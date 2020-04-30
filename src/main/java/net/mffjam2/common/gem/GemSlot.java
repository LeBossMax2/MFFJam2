package net.mffjam2.common.gem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GemSlot implements INBTSerializable<CompoundNBT>
{
    private ProjectileType projectileType;
    private UserActionType userActionType;

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT tag = new CompoundNBT();
        tag.putByte("projectileType", (byte) projectileType.ordinal());
        tag.putByte("userActionType", (byte) userActionType.ordinal());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag)
    {
        projectileType = ProjectileType.values()[tag.getByte("projectileType")];
        userActionType = UserActionType.values()[tag.getByte("userActionType")];
    }
}
