package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.tile.GemCrusherTile;
import net.mffjam2.common.tile.GemInfuserTile;
import net.mffjam2.common.tile.GemSocketTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MFFJam2.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class JamTiles
{
    public static final TileEntityType<GemCrusherTile> GEM_CRUSHER = null;
    public static final TileEntityType<GemInfuserTile> GEM_INFUSER = null;
    public static final TileEntityType<GemInfuserTile> GEM_SOCKET = null;

    @SubscribeEvent
    public static void onTileRegister(RegistryEvent.Register<TileEntityType<?>> event)
    {
        event.getRegistry().register(Builder
                .create(GemCrusherTile::new, JamBlocks.GEM_CRUSHER)
                .build(null)
                .setRegistryName(MFFJam2.MODID, "gem_crusher"));
        event.getRegistry().register(Builder
	            .create(GemInfuserTile::new, JamBlocks.GEM_INFUSER)
	            .build(null)
	            .setRegistryName(MFFJam2.MODID, "gem_infuser"));
        event.getRegistry().register(Builder
        .create(GemSocketTile::new, JamBlocks.GEM_SOCKET)
        .build(null)
        .setRegistryName(MFFJam2.MODID, "gem_socket"));
    }
}
