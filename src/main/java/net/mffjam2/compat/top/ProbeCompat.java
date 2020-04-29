package net.mffjam2.compat.top;

import mcjty.theoneprobe.TheOneProbe;
import net.mffjam2.MFFJam2;

public class ProbeCompat
{
    static int ELEMENT_FLUID;

    public static void load()
    {
        MFFJam2.logger.info("Compat module for The One Probe is loaded.");

        ELEMENT_FLUID = TheOneProbe.theOneProbeImp.registerElementFactory(FluidElement::new);

        TheOneProbe.theOneProbeImp.registerProvider(new ProbeProvider());
    }
}