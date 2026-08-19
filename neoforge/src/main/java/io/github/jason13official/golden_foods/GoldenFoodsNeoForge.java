package io.github.jason13official.golden_foods;

import io.github.jason13official.golden_foods.impl.common.registry.ModBlocks;
import io.github.jason13official.golden_foods.impl.common.registry.ModEntities;
import io.github.jason13official.golden_foods.impl.common.registry.ModItems;
import io.github.jason13official.golden_foods.impl.common.registry.ModMenus;
import io.github.jason13official.golden_foods.impl.common.registry.ModParticles;
import io.github.jason13official.golden_foods.impl.common.registry.ModTabs;
import io.github.jason13official.golden_foods.impl.common.registry.ModTiles;
import io.github.jason13official.golden_foods.impl.common.util.EnchantedGoldenFoodCreationMethod;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import oshi.util.tuples.Triplet;

@Mod(Constants.MOD_ID)
public class GoldenFoodsNeoForge {

  public static IEventBus EVENT_BUS;

  public GoldenFoodsNeoForge(final IEventBus modEventBus) {

    EVENT_BUS = modEventBus;

    bind(Registries.BLOCK, ModBlocks::register);
    bind(Registries.ENTITY_TYPE, ModEntities::register);
    bind(Registries.ITEM, ModItems::register);
    bind(Registries.PARTICLE_TYPE, ModParticles::register);
    bind(Registries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(Registries.MENU, ModMenus::register);
    bind(Registries.CREATIVE_MODE_TAB, ModTabs::register);

    EVENT_BUS.addListener((Consumer<FMLCommonSetupEvent>) event -> GoldenFoods.init());

    NeoForge.EVENT_BUS.addListener(this::onAnvilUpdate);

    NeoForge.EVENT_BUS.addListener((Consumer<AddServerReloadListenersEvent>) event -> {
      event.addListener(GoldenFoods.identifier(Constants.MOD_ID), new ResourceReloadListener());
    });

    if (FMLLoader.getCurrent().getDist() == Dist.CLIENT) {
      new GoldenFoodsClientNeoForge(EVENT_BUS);
    }
  }

  public void onAnvilUpdate(final AnvilUpdateEvent event) {

    final Triplet<Integer, Integer, ItemStack> triplet = EnchantedGoldenFoodCreationMethod.createGoldenFoods((AnvilMenu) null, event.getLeft(), event.getRight(), event.getOutput(), event.getName(), event.getXpCost(), event.getPlayer());

    if (triplet != null && triplet.getC() != ItemStack.EMPTY) {
      event.setXpCost(triplet.getA());
      event.setMaterialCost(triplet.getB());
      event.setOutput(triplet.getC());
    }
  }

  public <T> void bind(ResourceKey<Registry<T>> registryKey, Consumer<BiConsumer<T, Identifier>> source) {

    EVENT_BUS.addListener((Consumer<RegisterEvent>) event -> {
      if (registryKey.equals(event.getRegistryKey())) {
        source.accept((t, rl) -> event.register(registryKey, rl, () -> t));
      }
    });
  }

  public static class ResourceReloadListener extends SimplePreparableReloadListener<Void> {

    @Override
    public String getName() {
      return GoldenFoods.identifier(Constants.MOD_ID).toString();
    }

    @Override
    protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      // ModConfig.load(Services.PLATFORM.getConfigDirectory());
    }

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      return null;
    }
  }
}