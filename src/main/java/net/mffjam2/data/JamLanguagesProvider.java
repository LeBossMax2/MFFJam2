package net.mffjam2.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.mffjam2.MFFJam2;
import net.mffjam2.setup.JamBlocks;
import net.mffjam2.setup.JamItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;

public class JamLanguagesProvider implements IDataProvider 
{
	private final List<LanguagePartProvider> languages = new ArrayList<>();
	
	protected JamLanguagesProvider(DataGenerator gen, String... locales)
	{
		for (String locale : locales)
		{
			this.languages.add(new LanguagePartProvider(gen, MFFJam2.MODID, locale));
		}
	}
	
	public JamLanguagesProvider(DataGenerator gen)
	{
		this(gen, "en_us", "fr_fr");
	}
	
	protected void addTranslations()
    {
		// BLocks
		add(JamBlocks.GEM_CRUSHER, "Gem Crusher", "Broyeur de gemmes");
		
		// Items
		add(JamItems.MODULAR_SWORD, "Modular Sword", "Epée modulaire");
		add(JamItems.TEST_GEM, "Test Gam", "Gemme de test");

		add(JamItems.ESSENCE_FLIGHT_TYPE_NORMAL, "Normal Flight Essence", "Essence de vol normal");
		add(JamItems.ESSENCE_FLIGHT_TYPE_HOMING, "Homing Flight Essence", "Essence de vol dirigé");
		add(JamItems.ESSENCE_FLIGHT_TYPE_NO_CLIP, "No Clip Flight Essence", "Essence de vol traversant");
		add(JamItems.ESSENCE_FLIGHT_TYPE_BOUNCE, "Bouncy Flight Essence", "Essence de vol rebondissant");
    	
		add(JamItems.ESSENCE_SHOOT_TYPE_SINGLE, "Single Shoot Essence", "Essence de tir simple");
		add(JamItems.ESSENCE_SHOOT_TYPE_MULTISHOT, "Multishot Shoot Essence", "Essence de tir multiple");
		add(JamItems.ESSENCE_SHOOT_TYPE_BURST, "Burst Shoot Essence", "Essence de tir en rafale");
		add(JamItems.ESSENCE_SHOOT_TYPE_SCATTERSHOT, "Scattershot Shoot Essence", "Essence de tir à dispersion");
    	
		add(JamItems.ESSENCE_SUMMON_TYPE_POINT_SELF, "Point Self Summon Essence", "Essence d'apparition sur soi-même");
		add(JamItems.ESSENCE_SUMMON_TYPE_POINT_TARGET, "Point Target Summon Essence", "Essence d'apparition sur cible");
		add(JamItems.ESSENCE_SUMMON_TYPE_SKY_SELF, "Sky Self Summon Essence", "Essence d'apparition dans le ciel sur soi-même");
		add(JamItems.ESSENCE_SUMMON_TYPE_SKY_TARGET, "Sky Self Summon Essence", "Essence d'apparition dans le ciel sur cible");
		add(JamItems.ESSENCE_SUMMON_TYPE_AREA_SELF, "Area Self Summon Essence", "Essence d'apparition en zone sur soi-même");
		add(JamItems.ESSENCE_SUMMON_TYPE_AREA_TARGET, "Area Target Summon Essence", "Essence d'apparition en zone sur cible");
		
		// Gui
		add(MFFJam2.MODID + ".gui.gem_crusher.name", "Gem Crusher", "Broyeur de gemmes");
    }
	
	@Override
	public void act(DirectoryCache cache) throws IOException
	{
		this.addTranslations();
		for (LanguageProvider language : this.languages)
		{
			language.act(cache);
		}
	}
	
	protected void add(Block key, String... names)
	{
		add(key.getTranslationKey(), names);
	}
	
	protected void add(Item key, String... names)
	{
		add(key.getTranslationKey(), names);
	}
	
	protected void add(ItemGroup key, String... names)
	{
		add(key.getTranslationKey(), names);
	}
	
	protected void add(Enchantment key, String... names)
	{
		add(key.getName(), names);
	}
	
	protected void add(Biome key, String... names)
	{
		add(key.getTranslationKey(), names);
	}
	
	protected void add(Effect key, String... names)
	{
		add(key.getName(), names);
	}
	
	protected void add(EntityType<?> key, String... names)
	{
		add(key.getTranslationKey(), names);
	}
	
	protected void add(String key, String... values)
	{
		for (int i = 0; i < this.languages.size(); i++)
		{
			this.languages.get(i).add(key, values[i]);
		}
	}
	
	@Override
	public String getName()
	{
		return "JamLanguagesProvider";
	}

	private static class LanguagePartProvider extends LanguageProvider
	{
		public LanguagePartProvider(DataGenerator gen, String modid, String locale)
		{
			super(gen, modid, locale);
		}

		@Override
		protected void addTranslations()
		{ }
		
		@Override
		public void add(String key, String value)
		{
			super.add(key, value);
		}
	}
}
