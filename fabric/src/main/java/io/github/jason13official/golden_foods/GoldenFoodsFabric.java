package io.github.jason13official.golden_foods;

import io.github.jason13official.golden_foods.impl.common.registry.ModBlocks;
import io.github.jason13official.golden_foods.impl.common.registry.ModEntities;
import io.github.jason13official.golden_foods.impl.common.registry.ModItems;
import io.github.jason13official.golden_foods.impl.common.registry.ModMenus;
import io.github.jason13official.golden_foods.impl.common.registry.ModParticles;
import io.github.jason13official.golden_foods.impl.common.registry.ModTabs;
import io.github.jason13official.golden_foods.impl.common.registry.ModTiles;
import io.github.jason13official.golden_foods.impl.common.util.EnchantedGoldenFoodCreationMethod;
import io.github.jason13official.monolib.impl.common.event.MonoLibFabricAnvilMenuEvents;
import io.github.jason13official.monolib.impl.common.event.MonoLibFabricAnvilMenuEvents.AnvilMenuResult;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.impl.resource.DataResourceLoaderImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class GoldenFoodsFabric implements ModInitializer {

  @Override
  public void onInitialize() {

    bind(BuiltInRegistries.BLOCK, ModBlocks::register);
    bind(BuiltInRegistries.ENTITY_TYPE, ModEntities::register);
    bind(BuiltInRegistries.ITEM, ModItems::register);
    bind(BuiltInRegistries.PARTICLE_TYPE, ModParticles::register);
    bind(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(BuiltInRegistries.MENU, ModMenus::register);
    bind(BuiltInRegistries.CREATIVE_MODE_TAB, ModTabs::register);

    GoldenFoods.init();

    // MonoLibFabricAnvilMenuEvents.CREATE_RESULT.register(EnchantedGoldenFoodCreationMethod::createGoldenFoods);
    MonoLibFabricAnvilMenuEvents.CREATE_RESULT.register((anvilMenu, itemStack, itemStack1, itemStack2, s, i, player) -> {

      var result = EnchantedGoldenFoodCreationMethod.createGoldenFoods(anvilMenu, itemStack, itemStack1, itemStack2, s, i, player);
      if (result == null) return null;

      return new AnvilMenuResult(result.getA(), result.getB(), result.getC());
    });
    ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(GoldenFoods.identifier(Constants.MOD_ID), new ResourceReloadListener());
  }

  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, Identifier>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
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
