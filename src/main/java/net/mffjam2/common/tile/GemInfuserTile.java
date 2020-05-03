package net.mffjam2.common.tile;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.capability.GemCapability;
import net.mffjam2.common.item.EssenceItem;
import net.mffjam2.setup.JamContainers;
import net.mffjam2.setup.JamTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
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

import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.ObservableValue;
import lombok.Getter;

public class GemInfuserTile extends TileBase implements INamedContainerProvider, ITickableTileEntity
{
	private static int INFUSE_TIME = 40;
	private static int ESSENCE_SLOTS = 8;
    private final InventoryHandler inventory;
    private final BaseProperty<Integer> progress = new BaseProperty<>(0, "Progress");
    @Getter
    private final BaseProperty<Boolean> active = new BaseProperty<>(false, "Active");
    @Getter
    private final ObservableValue<Float> progressRatio = progress.map(p -> p / (float)INFUSE_TIME);
    @Getter
    private final BaseProperty<Boolean> canInfuse = new BaseProperty<>(false, "CanInfuse");

    public GemInfuserTile()
    {
        super(JamTiles.GEM_INFUSER);
        inventory = new InventoryHandler(ESSENCE_SLOTS + 1);
        inventory.setOnSlotChange(this::onSlotChange);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        compound.putInt("Progress", progress.getValue());
        compound.putBoolean("Active", active.getValue());
		canInfuse.setValue(canInfuse());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
    	compound = super.write(compound);
        compound.put("Inventory", inventory.serializeNBT());
        progress.setValue(compound.getInt("Progress"));
        active.setValue(compound.getBoolean("Active"));
        return compound;
    }
    
    private boolean canInfuse()
    {
    	ItemStack input = inventory.getStackInSlot(0);
    	if (input.isEmpty())
    		return false;
    	
    	for (int i = 0; i < ESSENCE_SLOTS; i++)
    	{
    		if (!inventory.getStackInSlot(i + 1).isEmpty())
    			return true;
    	}
    	return false;
    }
    
    @Override
    public void tick()
    {
    	if (canInfuse() && active.getValue())
    	{
    		progress.setValue(progress.getValue() + 1);
    		if (progress.getValue() >= INFUSE_TIME)
    		{
    			progress.setValue(INFUSE_TIME);
    			finishInfusion();
    		}
    	}
    	else
    		progress.setValue(0);
    }
    
    public boolean startInfusion()
    {
    	if (canInfuse())
    	{
    		progress.setValue(0);
    		active.setValue(true);
    		canInfuse.setValue(false);
    		return true;
    	}
    	
    	return false;
    }
    
    private void finishInfusion()
    {
    	ItemStack input = inventory.getStackInSlot(0);
    	input.getCapability(GemCapability.GEM_CAPABILITY).ifPresent(gem ->
    	{
    		for (int i = 0; i < ESSENCE_SLOTS; i++)
        	{
    			Item essence = inventory.extractItem(i + 1, 1, false).getItem();
    			if (essence instanceof EssenceItem)
    			{
    				((EssenceItem)essence).getGemProperty().applyProperty(gem);
    			}
        	}
    	});
    }
    
    private void onSlotChange(int newStack)
    {
    	active.setValue(false);
    	progress.setValue(0);
		canInfuse.setValue(canInfuse());
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
    	else if (side.getAxis() == Direction.Axis.Y)
    	{
    		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> new RangedWrapper(inventory, 0, 1)));
    	}
    	else
    	{
    		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> new RangedWrapper(inventory, 1, ESSENCE_SLOTS + 1)));
    	}
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
    {
    	ContainerTileInventoryBuilder b = new ContainerBuilder(JamContainers.GEM_INFUSER, player)
            .player(player)
            .inventory(8, 98)
            .hotbar(8, 156)
            .tile(this, inventory)
            .filterSlot(0, 80, 47, stack -> stack.getCapability(GemCapability.GEM_CAPABILITY).isPresent());
    	
    	Predicate<ItemStack> essenceFilter = stack -> stack.getItem() instanceof EssenceItem;
    	
    	for (int i = 0; i < 3; i++)
    	{
    		b.filterSlot(i + 1, 62 + i * 18, 29, essenceFilter);
    	}
    	
    	b.filterSlot(4, 62, 47, essenceFilter);
    	b.filterSlot(5, 62 + 2 * 18, 47, essenceFilter);

    	for (int i = 0; i < 3; i++)
    	{
    		b.filterSlot(i + 6, 62 + i * 18, 65, essenceFilter);
    	}
    	
        return b
        	.sync()
    		.syncInteger(progress::getValue, progress::setValue)
    		.create(windowId);
    }

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent(MFFJam2.MODID + ".gui.gem_infuser.name");
	}
}
