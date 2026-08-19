package io.github.jason13official.golden_foods.impl.common.registry;

import io.github.jason13official.golden_foods.Constants;
import io.github.jason13official.golden_foods.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static final CreativeModeTab GOLDEN_FOODS = Services.PLATFORM.tabBuilder()
      .icon(() -> new ItemStack(ModItems.ENCHANTED_GOLDEN_CAKE))
      .title(Component.translatable("itemGroup.goldenFoods"))
      .displayItems((itemDisplayParameters, output) -> {
        ModItems.REGISTERED.forEach((s, item) -> output.accept(item));
      }).build();

  public static void register(BiConsumer<CreativeModeTab, Identifier> consumer) {
    consumer.accept(GOLDEN_FOODS,
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, Constants.MOD_ID));
  }
}
