package net.mffjam2.data;

import javax.annotation.Nonnull;

import net.mffjam2.MFFJam2;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class JamBlockStateProvider extends BlockStateProvider
{

	public JamBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, MFFJam2.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
	}
	
	public ExistingFileHelper getExistingFileHelper()
	{
		return this.existingFileHelper;
	}
	
	@Override
	@Nonnull
	public String getName()
	{
		return "JamBlockStateProvider";
	}
	
}
