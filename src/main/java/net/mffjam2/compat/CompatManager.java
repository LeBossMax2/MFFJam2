package net.mffjam2.compat;

import net.mffjam2.compat.top.ProbeCompat;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CompatManager
{
    public static void setup(FMLCommonSetupEvent e)
    {
        // FIXME: Add check of top loading
        ProbeCompat.load();
    }
}
