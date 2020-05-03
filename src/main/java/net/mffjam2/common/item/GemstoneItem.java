package net.mffjam2.common.item;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.capability.GemCapability;
import net.mffjam2.common.gem.EffectType;
import net.mffjam2.common.gem.Gem;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.common.gem.GemProvider;
import net.mffjam2.setup.JamItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

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

    public static ItemStack createFromGem(Gem gem)
    {
        if (gem == null)
            return ItemStack.EMPTY;

        Item item = Items.AIR;
        switch (gem.getEffectType())
        {
            case FIRE:
                item = JamItems.FIRE_GEM;
                break;
            case ICE:
                item = JamItems.ICE_GEM;
                break;
            case EXPLOSION:
                item = JamItems.EXPLOSION_GEM;
                break;
            case ELECTRIC:
                item = JamItems.ELECTRIC_GEM;
                break;
            case VAMPIRISM:
                item = JamItems.VAMPIRISM_GEM;
                break;
            case LIGHT:
                item = JamItems.LIGHT_GEM;
                break;
        }

        ItemStack stack = new ItemStack(item);
        Gem stackGem = stack.getCapability(GemCapability.GEM_CAPABILITY).orElseThrow(() -> new RuntimeException("Gem capability cannot be null."));
        stackGem.setEffectType(gem.getEffectType());
        stackGem.setFlightType(gem.getFlightType());
        stackGem.setShootType(gem.getShootType());
        stackGem.setSummonType(gem.getSummonType());
        stackGem.setPower(gem.getPower());
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        super.addInformation(stack, world, tooltip, flag);

        if (GemCapability.GEM_CAPABILITY == null)
            return;

        Gem gem = stack.getCapability(GemCapability.GEM_CAPABILITY).orElse(Gem.builder().effectType(EffectType.FIRE).build());

        tooltip.add(new TranslationTextComponent(MFFJam2.MODID + ".gem.effect." + gem.getEffectType().name()));

        if (gem.getFlightType() != null)
            tooltip.add(new TranslationTextComponent(MFFJam2.MODID + ".gem.flight." + gem.getFlightType().name()));
        if (gem.getShootType() != null)
            tooltip.add(new TranslationTextComponent(MFFJam2.MODID + ".gem.shoot." + gem.getShootType().name()));
        if (gem.getSummonType() != null)
            tooltip.add(new TranslationTextComponent(MFFJam2.MODID + ".gem.summon." + gem.getSummonType().name()));
        if (gem.getPower() != 0)
            tooltip.add(new TranslationTextComponent(MFFJam2.MODID + ".gem.power", gem.getPower()));
    }

    @Override
    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new GemProvider(this.effectType);
    }
}
