package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import net.voxelindustry.brokkgui.wrapper.impl.BrokkGuiManager;
import net.voxelindustry.steamlayer.container.BuiltContainer;
import net.voxelindustry.steamlayer.container.SteamLayerContainerFactory;
import net.mffjam2.client.gui.DummyGui;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(bus = MOD)
@ObjectHolder(MFFJam2.MODID)
public class JamContainers
{
    @ObjectHolder("dummy")
    public static ContainerType<BuiltContainer> DUMMY;

    public static void registerScreens()
    {
        ScreenManager.registerFactory(DUMMY, BrokkGuiManager.getContainerFactory(MFFJam2.MODID, DummyGui::new));
    }

    @SubscribeEvent
    public static void onContainerRegister(Register<ContainerType<?>> event)
    {
        event.getRegistry().register(SteamLayerContainerFactory.create().setRegistryName(MFFJam2.MODID, "dummy"));
    }
}
