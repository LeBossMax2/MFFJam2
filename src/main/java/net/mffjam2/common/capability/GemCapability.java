package net.mffjam2.common.capability;

import javax.annotation.Nullable;

import net.mffjam2.common.gem.Gem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class GemCapability
{
    @CapabilityInject(Gem.class)
    public static Capability<Gem> GEM_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(Gem.class, new IStorage<Gem>()
        {
            @Nullable
            @Override
            public INBT writeNBT(Capability<Gem> capability, Gem instance, Direction side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<Gem> capability, Gem instance, Direction side, INBT nbt)
            {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, Gem::new);
    }
}
