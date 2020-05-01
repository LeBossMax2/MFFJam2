package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class JamTags
{
    public static final Tag<Item> GEMS = new ItemTags.Wrapper(new ResourceLocation(MFFJam2.MODID, "gems"));
}
