package net.mffjam2.common.gem;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gem implements INBTSerializable<CompoundNBT>
{
    private EffectType effectType;
    private SummonType summonType;
    private ShootType  shootType;
    private FlightType flightType;
    private int        power;

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT tag = new CompoundNBT();
        tag.putByte("effectType", (byte) effectType.ordinal());
        tag.putByte("summonType", (byte) summonType.ordinal());
        tag.putByte("shootType", (byte) shootType.ordinal());
        tag.putByte("flightType", (byte) flightType.ordinal());
        tag.putInt("power", power);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag)
    {
        effectType = EffectType.values()[tag.getByte("effectType")];
        summonType = SummonType.values()[tag.getByte("summonType")];
        shootType = ShootType.values()[tag.getByte("shootType")];
        flightType = FlightType.values()[tag.getByte("flightType")];

        power = tag.getInt("power");
    }
    
    public List<GemProperty> getGemProperties()
    {
    	return ImmutableList.of(summonType.asProperty(), shootType.asProperty(), flightType.asProperty());
    }
}
