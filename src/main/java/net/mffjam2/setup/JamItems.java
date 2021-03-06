package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.item.ModularSwordItem;
import net.mffjam2.common.gem.EffectType;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.common.item.EssenceItem;
import net.mffjam2.common.item.GemstoneItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
@ObjectHolder(MFFJam2.MODID)
public class JamItems
{
    private static final Item.Properties ITEM_PROPS = new Item.Properties().group(MFFJam2.TAB_ALL);
    private static final List<Item>      ITEMS      = new ArrayList<>();

    public static final ModularSwordItem MODULAR_SWORD = null;
    
    public static final EssenceItem
    	ESSENCE_FLIGHT_TYPE_NORMAL = null,
    	ESSENCE_FLIGHT_TYPE_HOMING = null,
    	ESSENCE_FLIGHT_TYPE_NO_CLIP = null,
    	ESSENCE_FLIGHT_TYPE_BOUNCE = null,
    	
    	ESSENCE_SHOOT_TYPE_SINGLE = null,
    	ESSENCE_SHOOT_TYPE_MULTISHOT = null,
    	ESSENCE_SHOOT_TYPE_BURST = null,
    	ESSENCE_SHOOT_TYPE_SCATTERSHOT = null,
    	
    	ESSENCE_SUMMON_TYPE_POINT_SELF = null,
    	ESSENCE_SUMMON_TYPE_POINT_TARGET = null,
    	ESSENCE_SUMMON_TYPE_SKY_SELF = null,
    	ESSENCE_SUMMON_TYPE_SKY_TARGET = null,
    	ESSENCE_SUMMON_TYPE_AREA_SELF = null,
    	ESSENCE_SUMMON_TYPE_AREA_TARGET = null;
    
    public static final  GemstoneItem
    	FIRE_GEM = null,
    	ICE_GEM = null,
    	EXPLOSION_GEM = null,
    	ELECTRIC_GEM = null,
    	VAMPIRISM_GEM = null,
    	LIGHT_GEM = null;
    
    @SubscribeEvent
    public static void onItemRegister(Register<Item> event)
    {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));

        registerItem(event, new ModularSwordItem(new Item.Properties().group(MFFJam2.TAB_ALL)), "modular_sword");
        
        for (EffectType effect : EffectType.values())
        {
            registerItem(event, new GemstoneItem(effect, new Item.Properties().group(MFFJam2.TAB_ALL).maxStackSize(1)), effect.toString().toLowerCase() +  "_gem");
        }
        
        for (GemProperty prop : JamGemProperties.getPROPERTIES().values())
        {
            registerItem(event, new EssenceItem(prop, ITEM_PROPS), "essence_" + prop.getName());
        }
    }

    static void registerItem(Register<Item> event, Item item, String name)
    {
        item.setRegistryName(MFFJam2.MODID, name);
        event.getRegistry().register(item);
    }

    public static void registerItemBlock(Block block)
    {
        ITEMS.add(new BlockItem(block, ITEM_PROPS).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
    }
}
