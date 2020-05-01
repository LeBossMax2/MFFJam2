package net.mffjam2;

import net.mffjam2.client.ClientProxy;
import net.mffjam2.common.ServerProxy;
import net.mffjam2.common.capability.GemCapability;
import net.mffjam2.common.capability.GemSocketCapability;
import net.mffjam2.compat.CompatManager;
import net.mffjam2.setup.IProxy;
import net.mffjam2.setup.JamGemProperties;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.voxelindustry.steamlayer.common.container.CustomCreativeTab;
import net.voxelindustry.steamlayer.core.TickHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MFFJam2.MODID)
public class MFFJam2
{
    public static final String MODID   = "mffjam2";
    public static final String NAME    = "MFF Jam 2";
    public static final String VERSION = "0.1.0";

    public static final ItemGroup TAB_ALL = new CustomCreativeTab(MODID, () -> new ItemStack(Blocks.BARREL));

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static MFFJam2 instance;

    public static Logger logger = LogManager.getLogger();

    public MFFJam2()
    {
        instance = this;
        
        JamGemProperties.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent e)
    {
        proxy.setup(e);

        CompatManager.setup(e);

        MinecraftForge.EVENT_BUS.register(new TickHandler());

        GemCapability.register();
        GemSocketCapability.register();
    }
}
