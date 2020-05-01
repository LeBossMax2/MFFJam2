package net.mffjam2.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import lombok.Getter;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.gem.FlightType;
import net.mffjam2.common.gem.Gem;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.common.gem.ShootType;
import net.mffjam2.common.gem.SummonType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class JamGemProperties
{
	@Getter
	private static final Map<String, GemProperty> PROPERTIES = new HashMap<>();
	
	private static void register(GemProperty property)
	{
		PROPERTIES.put(property.getName(), property);
	}
	
	private static <T> void register(String baseName, BiConsumer<Gem.GemBuilder, T> gemProperty, T value)
	{
		register(GemProperty.create(baseName + "_" + value.toString().toLowerCase(), gemProperty, value, () -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(MFFJam2.MODID, "essence_" + baseName + "_" + value.toString().toLowerCase()))));
	}
	
	public static void init()
	{
		for (FlightType type : FlightType.values())
		{
			register("flight_type", Gem.GemBuilder::flightType, type);
		}
		for (ShootType type : ShootType.values())
		{
			register("shoot_type", Gem.GemBuilder::shootType, type);
		}
		for (SummonType type : SummonType.values())
		{
			register("summon_type", Gem.GemBuilder::summonType, type);
		}
	}
}
