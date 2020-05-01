package net.mffjam2.data;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.gem.EffectType;
import net.mffjam2.setup.JamTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag.Builder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class JamItemTagProvider extends ItemTagsProvider
{
	public JamItemTagProvider(DataGenerator gen)
    {
        super(gen);
    }

    @Override
    public void registerTags()
    {
    	Builder<Item> gems = getBuilder(JamTags.GEMS);
    	for (EffectType effect : EffectType.values())
		{
    		gems.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MFFJam2.MODID, effect.toString().toLowerCase() +  "_gem")));
		}
    }

    @Override
    public String getName()
    {
        return "Minerraria Block Tags";
    }
}
