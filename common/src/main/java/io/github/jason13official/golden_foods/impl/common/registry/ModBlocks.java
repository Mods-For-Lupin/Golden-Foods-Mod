package io.github.jason13official.golden_foods.impl.common.registry;

import io.github.jason13official.golden_foods.Constants;
import io.github.jason13official.golden_foods.impl.common.block.EnchantedGoldenCakeBlock;
import io.github.jason13official.golden_foods.impl.common.block.GoldenCakeBlock;
import java.util.function.BiConsumer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public class ModBlocks {

  public static final Block GOLDEN_CAKE = new GoldenCakeBlock();
  public static final Block ENCHANTED_GOLDEN_CAKE = new EnchantedGoldenCakeBlock();

  public static void register(BiConsumer<Block, Identifier> consumer) {
    consumer.accept(GOLDEN_CAKE,
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "golden_cake"));
    consumer.accept(ENCHANTED_GOLDEN_CAKE,
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "enchanted_golden_cake"));
  }
}
