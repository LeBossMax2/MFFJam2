package net.mffjam2.common.item;

import java.util.Collections;
import java.util.List;

import net.mffjam2.common.gem.GemProperty;
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
    		return Collections.emptyList(); //TODO get properties from nbt or capability
    	}
    	return Collections.emptyList();
    }
}
