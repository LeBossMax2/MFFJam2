package net.mffjam2.common.item;

import lombok.Getter;
import net.mffjam2.common.gem.GemProperty;
import net.minecraft.item.Item;

public class EssenceItem extends Item
{
	@Getter
	private final GemProperty gemProperty;
	
	public EssenceItem(GemProperty gemProperty, Properties properties)
	{
		super(properties);
		this.gemProperty = gemProperty; 
	}
}
