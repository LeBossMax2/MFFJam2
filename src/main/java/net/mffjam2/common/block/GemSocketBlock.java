package net.mffjam2.common.block;

import net.mffjam2.common.tile.GemInfuserTile;
import net.mffjam2.common.tile.GemSocketTile;
import net.mffjam2.setup.JamTiles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class GemSocketBlock extends Block
{
    public GemSocketBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return JamTiles.GEM_SOCKET.create();
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof GemInfuserTile)
            {
                te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler ->
                {
                    for (int i = 0; i < itemHandler.getSlots(); i++)
                    {
                        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemHandler.getStackInSlot(i));
                    }
                });
            }
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn.isRemote)
            return true;
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof GemSocketTile)
        {
            NetworkHooks.openGui((ServerPlayerEntity)player, (GemSocketTile) tileentity, pos);
        }

        return true;
    }
}
