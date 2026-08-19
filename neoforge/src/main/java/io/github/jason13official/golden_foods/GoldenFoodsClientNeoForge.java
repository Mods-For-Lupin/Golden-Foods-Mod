package io.github.jason13official.golden_foods;

import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class GoldenFoodsClientNeoForge {

  public GoldenFoodsClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> GoldenFoodsClient.init());
  }
}
