package net.mffjam2.data;

import java.util.function.Consumer;

import net.mffjam2.setup.JamBlocks;
import net.mffjam2.setup.JamItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

public class JamRecipeProvider extends RecipeProvider
{

	public JamRecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		ShapedRecipeBuilder
			.shapedRecipe(JamBlocks.GEM_CRUSHER)
			.patternLine("CCC")
			.patternLine("CGC")
			.patternLine("CCC")
			.key('C', Blocks.COBBLESTONE)
			.key('G', JamItems.TEST_GEM)
			.addCriterion("has_gem", this.hasItem(JamItems.TEST_GEM))
			.build(consumer);
	}
	
	@Override
	public String getName()
	{
		return "JamRecipeProvider";
	}
	
}
