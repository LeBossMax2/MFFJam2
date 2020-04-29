package net.mffjam2.compat.top;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.mffjam2.MFFJam2;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.voxelindustry.steamlayer.tile.ITileInfoProvider;

import java.util.Objects;

public class ProbeProvider implements IProbeInfoProvider
{
    @Override
    public String getID()
    {
        return MFFJam2.MODID;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world,
                             BlockState blockState, IProbeHitData data)
    {
        TileEntity tile = world.getTileEntity(data.getPos());
        if (tile instanceof ITileInfoProvider && Objects.equals(MFFJam2.MODID, tile.getType().getRegistryName().getNamespace()))
        {
            TileInfoListImpl list = new TileInfoListImpl(probeInfo);
            ((ITileInfoProvider) tile).addInfo(list);
        }
    }
}
