package net.mffjam2.common.tile;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.common.item.GemstoneItem;
import net.mffjam2.setup.JamContainers;
import net.mffjam2.setup.JamTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.voxelindustry.steamlayer.container.ContainerBuilder;
import net.voxelindustry.steamlayer.container.ContainerTileInventoryBuilder;
import net.voxelindustry.steamlayer.inventory.InventoryHandler;
import net.voxelindustry.steamlayer.tile.TileBase;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.ObservableValue;
import lombok.Getter;

public class GemCrusherTile extends TileBase implements INamedContainerProvider, ITickableTileEntity
{
	private static int CRUSH_TIME = 40;
	private static int OUTPUT_SLOTS = 5;
    private final InventoryHandler inventory;
    private final BaseProperty<Integer> progress = new BaseProperty<>(0, "Progress");
    @Getter
    private final ObservableValue<Float> progressRatio = progress.map(p -> p / (float)CRUSH_TIME);

    public GemCrusherTile()
    {
        super(JamTiles.GEM_CRUSHER);

        inventory = new InventoryHandler(OUTPUT_SLOTS + 1);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        compound.putInt("Progress", progress.getValue());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
    	compound = super.write(compound);
        compound.put("Inventory", inventory.serializeNBT());
        progress.setValue(compound.getInt("Progress"));
        return compound;
    }
    
    @Override
    public void tick()
    {
    	ItemStack input = inventory.getStackInSlot(0);
    	if (!input.isEmpty())
    	{
    		progress.setValue(progress.getValue() + 1);
    		if (progress.getValue() >= CRUSH_TIME)
    		{
    			progress.setValue(CRUSH_TIME);
    			crushGem();
    		}
    	}
    	else
    		progress.setValue(0);
    }
    
    protected boolean crushGem()
    {
    	ItemStack input = inventory.extractItem(0, 1, true);
    	if (input.isEmpty())
        	return false;
    	
		List<GemProperty> properties = GemstoneItem.getGemProperties(input);
		NonNullList<ItemStack> outputStack = NonNullList.withSize(properties.size(), ItemStack.EMPTY);
		for (int i = 0; i < properties.size(); i++)
		{
			outputStack.set(i, properties.get(i).getEssenceItem().copy());
		}
		boolean[] usedSlot = new boolean[OUTPUT_SLOTS];
		
		// Check if all stacks can be inserted
		for (ItemStack stack : outputStack)
		{
			boolean foundFit = false;
    		for (int i = 0; i < OUTPUT_SLOTS; i++)
    		{
    			if (!usedSlot[i] && inventory.insertItem(i + 1, stack, true).isEmpty())
    			{
    				usedSlot[i] = true;
    				foundFit = true;
    				break;
    			}
    		}
    		if (!foundFit)
    			return false;
		}
		
		inventory.extractItem(0, 1, false);
		
		for (int i = 0; i < OUTPUT_SLOTS; i++)
			usedSlot[i] = false;
		
		// Actually insert the stacks
		for (ItemStack stack : outputStack)
		{
    		for (int i = 0; i < OUTPUT_SLOTS; i++)
    		{
    			if (!usedSlot[i] && inventory.insertItem(i + 1, stack, false).isEmpty())
    			{
    				usedSlot[i] = true;
    				break;
    			}
    		}
		}
    	
    	progress.setValue(0);
    	return true;
    }

    @Override
    public void onLoad()
    {
        if (isClient())
            askServerSync();
    }
    
    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
    	if (cap != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    		return super.getCapability(cap, side);
    	
    	if (side == null)
    	{
    		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> inventory));
    	}
    	else if (side != Direction.DOWN)
    	{
    		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> new RangedWrapper(inventory, 0, 1)));
    	}
    	else
    	{
    		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> new RangedWrapper(inventory, 1, OUTPUT_SLOTS + 1)));
    	}
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
    	
    	for (int i = 0; i < OUTPUT_SLOTS; i++)
    	{
    		b.outputSlot(i + 1, 44 + i * 18, 65);
    	}
    	
        return b.sync()
        		.syncInteger(progress::getValue, progress::setValue)
        		.create(windowId);
    }

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent(MFFJam2.MODID + ".gui.gem_crusher.name");
	}
}
