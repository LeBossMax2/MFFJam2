package net.mffjam2.client;

import net.mffjam2.MFFJam2;
import net.mffjam2.setup.IProxy;
import net.mffjam2.setup.JamContainers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.voxelindustry.brokkgui.style.StylesheetManager;

public class ClientProxy implements IProxy
{
    @Override
    public void setup(FMLCommonSetupEvent e)
    {
        StylesheetManager.getInstance().addUserAgent(MFFJam2.MODID, "/assets/" + MFFJam2.MODID + "/css/theme.css");
        JamContainers.registerScreens();
    }
}
