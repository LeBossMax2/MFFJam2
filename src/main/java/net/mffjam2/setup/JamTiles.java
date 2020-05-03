package net.mffjam2.setup;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.tile.GemCrusherTile;
import net.mffjam2.common.tile.GemInfuserTile;

@ObjectHolder(MFFJam2.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class JamTiles
{
    public static final TileEntityType<GemCrusherTile> GEM_CRUSHER = null;
    public static final TileEntityType<GemInfuserTile> GEM_INFUSER = null;

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
    }
}
