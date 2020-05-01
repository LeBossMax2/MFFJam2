package net.mffjam2.common.item;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.mffjam2.common.capability.GemCapability;
import net.mffjam2.common.gem.EffectType;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.common.gem.GemProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class GemstoneItem extends Item
{
	private final EffectType effectType;
	public GemstoneItem(EffectType effectType, Properties properties)
	{
		super(properties);
		this.effectType = effectType;
	}
	
	public static List<GemProperty> getGemProperties(ItemStack stack)
	{
		return stack.getCapability(GemCapability.GEM_CAPABILITY).map(gem -> gem.getGemProperties()).orElse(Collections.emptyList());
	}
	
	@Override
	@Nullable
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
	{
		return new GemProvider(this.effectType);
	}
}
