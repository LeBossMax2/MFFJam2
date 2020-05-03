package net.mffjam2.common.gem;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullSupplier;

public class GemProperty
{
	private final String name;
	private final Consumer<Gem> gemConfig;
	private final NonNullLazy<ItemStack> essenceItem;
	
	private GemProperty(String name, Consumer<Gem> gemConfig, NonNullSupplier<ItemStack> essenceItem)
	{
		this.name = name;
		this.gemConfig = gemConfig;
		this.essenceItem = NonNullLazy.of(essenceItem);
	}

	public void applyProperty(Gem gem)
	{
		gemConfig.accept(gem);
	}
	
	public ItemStack getEssenceItem()
	{
		return essenceItem.get();
	}
	
	public static GemProperty createWithStack(String name, Consumer<Gem> gemConfig, NonNullSupplier<ItemStack> essenceItem)
	{
		return new GemProperty(name, gemConfig, essenceItem);
	}
	
	public static <T> GemProperty createWithStack(String name, BiConsumer<Gem, T> gemProperty, T value, NonNullSupplier<ItemStack> essenceItem)
	{
		return new GemProperty(name, builder -> gemProperty.accept(builder, value), essenceItem);
	}
	
	public static GemProperty create(String name, Consumer<Gem> gemConfig, NonNullSupplier<IItemProvider> essenceItem)
	{
		return createWithStack(name, gemConfig, () -> new ItemStack(essenceItem.get()));
	}
	
	public static <T> GemProperty create(String name, BiConsumer<Gem, T> gemProperty, T value, NonNullSupplier<IItemProvider> essenceItem)
	{
		return createWithStack(name, gemProperty, value, () -> new ItemStack(essenceItem.get()));
	}
	
	public String getName()
	{
		return this.name;
	}
}
