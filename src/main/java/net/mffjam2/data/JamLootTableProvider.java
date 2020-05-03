package net.mffjam2.data;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.mffjam2.MFFJam2;
import net.mffjam2.setup.JamBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.LootTable.Builder;
import net.minecraft.world.storage.loot.ValidationResults;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class JamLootTableProvider extends LootTableProvider
{
	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> tables = ImmutableList.of(
		Pair.of(() -> this::chestLootTables, LootParameterSets.CHEST),
		Pair.of(ModEntityLootTables::new, LootParameterSets.ENTITY),
		Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK));


	public JamLootTableProvider(DataGenerator dataGeneratorIn)
	{
		super(dataGeneratorIn);
	}
	
	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables()
	{
		return tables;
	}
	
	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationResults validationresults)
	{
		map.forEach((name, table) ->
		{
			LootTableManager.func_215302_a(validationresults, name, table, map::get);
		});
	}
	
	private void chestLootTables(BiConsumer<ResourceLocation, Builder> tableConsumer)
	{
		
	}
	
	private static class ModEntityLootTables extends EntityLootTables
	{
		@Override
		protected void addTables()
		{
			
		}
		
		@Override
		protected Iterable<EntityType<?>> getKnownEntities()
		{
			return filterRegistry(ForgeRegistries.ENTITIES);
		}
	}
	
	private static class ModBlockLootTables extends BlockLootTables
	{
		@Override
		protected void addTables()
		{
			func_218492_c(JamBlocks.GEM_CRUSHER); // registerDropSelfLootTable
			func_218492_c(JamBlocks.GEM_INFUSER);
		}
		
		@Override
		protected Iterable<Block> getKnownBlocks()
		{
			return filterRegistry(ForgeRegistries.BLOCKS);
		}
	}
	
	private static <T extends IForgeRegistryEntry<T>> List<T> filterRegistry(IForgeRegistry<T> registry)
	{
		return registry.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(MFFJam2.MODID)).collect(Collectors.toList());
	}
	
	@Override
	public String getName()
	{
		return "JamLootTableProvider";
	}
	
}
