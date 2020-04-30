package net.mffjam2.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import lombok.Getter;
import net.mffjam2.common.gem.FlightType;
import net.mffjam2.common.gem.Gem;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.common.gem.ShootType;
import net.mffjam2.common.gem.SummonType;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.util.NonNullSupplier;

public class JamGemProperties
{
	@Getter
	private static final List<GemProperty> PROPERTIES = new ArrayList<>();
	
	private static void register(GemProperty property)
	{
		PROPERTIES.add(property);
	}
	
	private static <T> void register(String baseName, BiConsumer<Gem.GemBuilder, T> gemProperty, T value, NonNullSupplier<IItemProvider> essenceItem)
	{
		register(GemProperty.create(baseName + "_" + value.toString().toLowerCase(), gemProperty, value, essenceItem));
	}
	
	public static void init()
	{
		for (FlightType type : FlightType.values())
		{
			register("flight_type", Gem.GemBuilder::flightType, type, () -> null);
		}
		for (ShootType type : ShootType.values())
		{
			register("shoot_type", Gem.GemBuilder::shootType, type, () -> null);
		}
		for (SummonType type : SummonType.values())
		{
			register("summon_type", Gem.GemBuilder::summonType, type, () -> null);
		}
	}
}
