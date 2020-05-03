package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import net.voxelindustry.brokkgui.wrapper.impl.BrokkGuiManager;
import net.voxelindustry.steamlayer.container.BuiltContainer;
import net.voxelindustry.steamlayer.container.SteamLayerContainerFactory;
import net.mffjam2.client.gui.GemCrusherGui;
import net.mffjam2.client.gui.GemInfuserGui;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(bus = MOD)
@ObjectHolder(MFFJam2.MODID)
public class JamContainers
{
    public static final ContainerType<BuiltContainer> GEM_CRUSHER = null;
    public static final ContainerType<BuiltContainer> GEM_INFUSER = null;

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens()
    {
        ScreenManager.registerFactory(GEM_CRUSHER, BrokkGuiManager.getContainerFactory(MFFJam2.MODID, GemCrusherGui::new));
        ScreenManager.registerFactory(GEM_INFUSER, BrokkGuiManager.getContainerFactory(MFFJam2.MODID, GemInfuserGui::new));
    }

    @SubscribeEvent
    public static void onContainerRegister(Register<ContainerType<?>> event)
    {
        event.getRegistry().register(SteamLayerContainerFactory.create().setRegistryName(MFFJam2.MODID, "gem_crusher"));
        event.getRegistry().register(SteamLayerContainerFactory.create().setRegistryName(MFFJam2.MODID, "gem_infuser"));
    }
}
