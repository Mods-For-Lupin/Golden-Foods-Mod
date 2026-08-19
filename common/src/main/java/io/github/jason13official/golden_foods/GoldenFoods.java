package io.github.jason13official.golden_foods;

import io.github.jason13official.monolib.MonoLib;
import io.github.jason13official.monolib.impl.common.sailing.Sailing;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class GoldenFoods {

  public static final ResourceKey<Enchantment> GOLDEN_FOODS = ResourceKey.create(Registries.ENCHANTMENT, identifier(Constants.MOD_ID));

  public static void init() {

    Sailing.register(Constants.MOD_ID, MonoLib.createFilename(Constants.MOD_ID, "26.1.2", "1.0.0"));
  }

  public static Identifier identifier(final String path) {
    return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}