package net.mffjam2.setup;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.tile.DummyTile;

@ObjectHolder(MFFJam2.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class JamTiles
{
    @ObjectHolder("dummy")
    public static TileEntityType<DummyTile> DUMMY;

    @SubscribeEvent
    public static void onTileRegister(RegistryEvent.Register<TileEntityType<?>> event)
    {
        event.getRegistry().register(Builder
                .create(DummyTile::new, JamBlocks.DUMMY)
                .build(null)
                .setRegistryName(MFFJam2.MODID, "dummy"));
    }
}
