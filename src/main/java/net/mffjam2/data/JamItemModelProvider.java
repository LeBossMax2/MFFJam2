package net.mffjam2.data;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.gem.GemProperty;
import net.mffjam2.setup.JamGemProperties;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class JamItemModelProvider extends ItemModelProvider
{

	public JamItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, MFFJam2.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		// Essences
		for (GemProperty prop : JamGemProperties.getPROPERTIES())
        {
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MFFJam2.MODID, "essence_" + prop.getName()));
			simpleItem(item, "essence");
        }
	}
	
	protected void simpleTool(IForgeRegistryEntry<?> entry)
	{
		singleTexture(name(entry), mcLoc("item/handheld"), "layer0", itemTexture(entry, name(entry)));
	}
	
	protected void simpleItem(IForgeRegistryEntry<?> entry, String texture)
	{
		singleTexture(name(entry), mcLoc("item/generated"), "layer0", itemTexture(entry, texture));
	}

    protected ResourceLocation itemTexture(IForgeRegistryEntry<?> entry, String name)
    {
        return new ResourceLocation(MFFJam2.MODID, (entry instanceof Block ? BLOCK_FOLDER : ITEM_FOLDER) + "/" + name);
    }

    protected String name(IForgeRegistryEntry<?> entry)
    {
        return entry.getRegistryName().getPath();
    }

	@Override
	public String getName()
	{
		return "JamItemModelProvider";
	}
	
}
