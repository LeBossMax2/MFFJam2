package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.mffjam2.data.JamBlockStateProvider;
import net.mffjam2.data.JamItemModelProvider;
import net.mffjam2.data.JamLanguagesProvider;
import net.mffjam2.data.JamLootTableProvider;
import net.mffjam2.data.JamRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = MFFJam2.MODID, bus = Bus.MOD)
public class JamDataProviders
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();

        if (event.includeServer())
        {
            gen.addProvider(new JamRecipeProvider(gen));
            gen.addProvider(new JamLootTableProvider(gen));
        }
        
        if (event.includeClient())
        {
        	JamBlockStateProvider blockStates = new JamBlockStateProvider(gen, event.getExistingFileHelper());
            gen.addProvider(blockStates);
            gen.addProvider(new JamItemModelProvider(gen, blockStates.getExistingFileHelper()));
            gen.addProvider(new JamLanguagesProvider(gen));
        }
    }
}
