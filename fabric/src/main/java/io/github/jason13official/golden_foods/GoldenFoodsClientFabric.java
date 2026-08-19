package io.github.jason13official.golden_foods;

import net.fabricmc.api.ClientModInitializer;

public class GoldenFoodsClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    GoldenFoodsClient.init();
  }
}
