package net.mffjam2.common.gem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.mffjam2.common.capability.GemCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class GemProvider implements ICapabilitySerializable<CompoundNBT>
{
	private final Gem gem;
    private final LazyOptional<Gem> holder;
    
	public GemProvider(EffectType effect)
	{
		gem = new Gem.GemBuilder()
			.effectType(effect)
			.summonType(SummonType.POINT_SELF)
			.shootType(ShootType.SINGLE)
			.flightType(FlightType.NORMAL)
			.power(1)
			.build();
		this.holder = LazyOptional.of(() -> gem);
	}
	
	@Override
	public CompoundNBT serializeNBT()
	{
		return this.gem.serializeNBT();
	}
	
	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		this.gem.deserializeNBT(nbt);
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		return GemCapability.GEM_CAPABILITY.orEmpty(cap, holder);
	}
}
