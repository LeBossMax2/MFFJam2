package net.mffjam2.common.capability;

import net.mffjam2.common.gem.GemSocket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class GemSocketCapability
{
    @CapabilityInject(GemSocket.class)
    public static Capability<GemSocket> GEM_SOCKET_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(GemSocket.class, new IStorage<GemSocket>()
        {
            @Nullable
            @Override
            public INBT writeNBT(Capability<GemSocket> capability, GemSocket instance, Direction side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<GemSocket> capability, GemSocket instance, Direction side, INBT nbt)
            {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, GemSocket::new);
    }
}
