package io.github.jason13official.golden_foods.impl.common.registry;

import io.github.jason13official.golden_foods.Constants;
import io.github.jason13official.golden_foods.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class ModTabs {

  public static final CreativeModeTab GOLDEN_FOODS = Services.PLATFORM.tabBuilder()
      .icon(() -> new ItemStack(ModItems.ENCHANTED_GOLDEN_CAKE))
      .title(Component.translatable("itemGroup.goldenFoods"))
      .displayItems((parameters, output) -> {

        parameters.holders().lookup(Registries.ENCHANTMENT).ifPresent((enchantments) -> {
          generateEnchantmentBookTypesOnlyMaxLevel(output, enchantments, TabVisibility.PARENT_AND_SEARCH_TABS);
        });

        ModItems.REGISTERED.forEach((s, item) -> output.accept(item));
      }).build();

  public static void register(BiConsumer<CreativeModeTab, Identifier> consumer) {
    consumer.accept(GOLDEN_FOODS,
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, Constants.MOD_ID));
  }

  private static void generateEnchantmentBookTypesOnlyMaxLevel(CreativeModeTab.Output output, HolderLookup<Enchantment> enchantments, CreativeModeTab.TabVisibility tabVisibility) {
    enchantments.listElements().map((enchantment) -> EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, ((Enchantment)enchantment.value()).getMaxLevel()))).forEach((stack) -> output.accept(stack, tabVisibility));
  }
}
