package net.mffjam2.common.item;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.setup.JamGemProperties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GemstoneItem extends Item
{
	public GemstoneItem(Properties properties)
	{
		super(properties);
	}

    
    public static List<GemProperty> getGemProperties(ItemStack stack)
    {
    	if (stack.getItem() instanceof GemstoneItem)
    	{
    		return ImmutableList.of(JamGemProperties.getPROPERTIES().get(0), JamGemProperties.getPROPERTIES().get(1)); //TODO get properties from nbt or capability
    	}
    	return Collections.emptyList();
    }
}
