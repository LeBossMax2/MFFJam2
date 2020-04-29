package net.mffjam2.common.tile;

import net.mffjam2.setup.JamContainers;
import net.mffjam2.setup.JamTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.voxelindustry.steamlayer.container.ContainerBuilder;
import net.voxelindustry.steamlayer.inventory.InventoryHandler;
import net.voxelindustry.steamlayer.tile.TileBase;

import javax.annotation.Nullable;

public class DummyTile extends TileBase implements IContainerProvider
{
    private final InventoryHandler inventory;

    public DummyTile()
    {
        super(JamTiles.DUMMY);

        inventory = new InventoryHandler(0);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        return super.write(compound);
    }

    @Override
    public void onLoad()
    {
        if (isClient())
            askServerSync();
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new ContainerBuilder(JamContainers.DUMMY, player)
                .player(player)
                .inventory(8, 147)
                .hotbar(8, 205)
                .tile(this, inventory)
                .create(windowId);
    }
}
