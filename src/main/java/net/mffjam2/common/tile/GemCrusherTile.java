package net.mffjam2.common.tile;

import net.mffjam2.common.item.GemstoneItem;
import net.mffjam2.setup.JamContainers;
import net.mffjam2.setup.JamTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.voxelindustry.steamlayer.container.ContainerBuilder;
import net.voxelindustry.steamlayer.container.ContainerTileInventoryBuilder;
import net.voxelindustry.steamlayer.inventory.InventoryHandler;
import net.voxelindustry.steamlayer.tile.TileBase;

import javax.annotation.Nullable;

public class GemCrusherTile extends TileBase implements INamedContainerProvider
{
    private final InventoryHandler inventory;

    public GemCrusherTile()
    {
        super(JamTiles.GEM_CRUSHER);

        this.inventory = new InventoryHandler(6);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
    	compound = super.write(compound);
        compound.put("Inventory", this.inventory.serializeNBT());
        return compound;
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
    	ContainerTileInventoryBuilder b = new ContainerBuilder(JamContainers.GEM_CRUSHER, player)
            .player(player)
            .inventory(8, 98)
            .hotbar(8, 156)
            .tile(this, inventory)
            .filterSlot(0, 80, 12, stack -> stack.getItem() instanceof GemstoneItem);
    	
    	for (int i = 0; i < 5; i++)
    	{
    		b.outputSlot(i + 1, 44 + i * 18, 65);
    	}
    	
        return b.create(windowId);
    }

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("test");
	}
}
