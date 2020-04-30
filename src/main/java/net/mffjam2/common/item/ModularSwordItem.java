package net.mffjam2.common.item;

import net.mffjam2.common.capability.GemSocketCapability;
import net.mffjam2.common.gem.EffectType;
import net.mffjam2.common.gem.FlightType;
import net.mffjam2.common.gem.Gem;
import net.mffjam2.common.gem.GemSlot;
import net.mffjam2.common.gem.GemSocket;
import net.mffjam2.common.gem.ProjectileType;
import net.mffjam2.common.gem.ShootType;
import net.mffjam2.common.gem.SummonType;
import net.mffjam2.common.gem.UserActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ModularSwordItem extends SwordItem
{
    public ModularSwordItem(Properties builder)
    {
        super(ItemTier.IRON, 3, -2.4F, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        context.getItem().getCapability(GemSocketCapability.GEM_SOCKET_CAPABILITY).ifPresent(socket ->
        {
            System.out.println(socket);
            if (context.isPlacerSneaking())
            {
                socket.putGem(0, Gem.builder()
                        .effectType(EffectType.ELECTRIC)
                        .flightType(FlightType.NORMAL)
                        .shootType(ShootType.SINGLE)
                        .summonType(SummonType.POINT_TARGET)
                        .power(2)
                        .build());
            }
        });

        return super.onItemUse(context);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT tag)
    {
        if (this.getClass() == ModularSwordItem.class)
            return new GemSocket(GemSlot.builder()
                    .projectileType(ProjectileType.ARROW)
                    .userActionType(UserActionType.ATTACK)
                    .build());
        else
            return super.initCapabilities(stack, tag);
    }
}
