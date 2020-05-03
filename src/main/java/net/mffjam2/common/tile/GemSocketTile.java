package net.mffjam2.common.tile;

import lombok.Getter;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.capability.GemCapability;
import net.mffjam2.common.capability.GemSocketCapability;
import net.mffjam2.common.gem.Gem;
import net.mffjam2.common.gem.GemSocket;
import net.mffjam2.common.item.GemstoneItem;
import net.mffjam2.setup.JamContainers;
import net.mffjam2.setup.JamTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.voxelindustry.steamlayer.container.ContainerBuilder;
import net.voxelindustry.steamlayer.inventory.InventoryHandler;
import net.voxelindustry.steamlayer.network.action.ActionSender;
import net.voxelindustry.steamlayer.network.action.IActionReceiver;
import net.voxelindustry.steamlayer.tile.TileBase;

import javax.annotation.Nullable;

public class GemSocketTile extends TileBase implements INamedContainerProvider, IActionReceiver
{
    @Getter
    private InventoryHandler inventory;

    public GemSocketTile()
    {
        super(JamTiles.GEM_SOCKET);
        inventory = new InventoryHandler(1);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        compound.put("Inventory", inventory.serializeNBT());
        return compound;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent(MFFJam2.MODID + ".gui.socket");
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new ContainerBuilder(JamContainers.GEM_SOCKET, player)
                .player(player)
                .inventory(8, 98)
                .hotbar(8, 156)
                .tile(this, inventory)
                .filterSlot(0, 82, 70, stack -> stack.getCapability(GemSocketCapability.GEM_SOCKET_CAPABILITY).isPresent())
                .create(windowID);
    }

    @Override
    public void handle(ActionSender actionSender, String actionID, CompoundNBT compoundNBT)
    {
        ItemStack currentItem = inventory.getStackInSlot(0);
        GemSocket socket = currentItem.getCapability(GemSocketCapability.GEM_SOCKET_CAPABILITY).orElseThrow(() -> new RuntimeException("Socket capability cannot be null."));

        switch (actionID)
        {
            case "GEM_SOCKET_REMOVE":
                if (actionSender.getPlayer().inventory.getItemStack().isEmpty() && !socket.getGems().isEmpty())
                {
                    Gem gem = socket.getGems().get(0);
                    ItemStack gemStack = GemstoneItem.createFromGem(gem);

                    actionSender.getPlayer().inventory.setItemStack(gemStack);
                    socket.getGems().set(0, null);
                    inventory.notifySlotChange();

                    actionSender.answer()
                            .withBoolean("accepted", true)
                            .withItemStack("cursor", gemStack)
                            .withItemStack("inventory", inventory.getStackInSlot(0))
                            .send();
                }
                break;
            case "GEM_SOCKET_ADD":
                if (!actionSender.getPlayer().inventory.getItemStack().isEmpty())
                {
                    Gem gem = actionSender.getPlayer().inventory.getItemStack().getCapability(GemCapability.GEM_CAPABILITY).orElseThrow(() -> new RuntimeException("Gem capability cannot be null."));

                    if (socket.getGems().isEmpty())
                        socket.getGems().add(gem);
                    else
                        socket.getGems().set(0, gem);
                    inventory.setStackInSlot(0, inventory.getStackInSlot(0));
                    actionSender.getPlayer().inventory.setItemStack(ItemStack.EMPTY);

                    actionSender.answer()
                            .withBoolean("accepted", true)
                            .withItemStack("inventory", inventory.getStackInSlot(0))
                            .send();
                }
                break;
        }
    }
}
