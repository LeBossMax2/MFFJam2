package net.mffjam2.client.gui;

import lombok.Getter;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.capability.GemSocketCapability;
import net.mffjam2.common.item.GemstoneItem;
import net.mffjam2.common.tile.GemSocketTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.voxelindustry.brokkgui.data.RectAlignment;
import net.voxelindustry.brokkgui.element.GuiLabel;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.shape.Rectangle;
import net.voxelindustry.brokkgui.wrapper.container.BrokkGuiContainer;
import net.voxelindustry.brokkgui.wrapper.elements.ItemStackView;
import net.voxelindustry.steamlayer.container.BuiltContainer;
import net.voxelindustry.steamlayer.container.slot.FilteredSlot;
import net.voxelindustry.steamlayer.network.action.ServerActionBuilder;

public class GemSocketGui extends BrokkGuiContainer<BuiltContainer>
{
    public static final float GUI_WIDTH  = 176;
    public static final float GUI_HEIGHT = 180;

    @Getter
    private final GemSocketTile   tile;
    private final GuiAbsolutePane mainPanel;

    private final Rectangle survivalInventory;

    public GemSocketGui(BuiltContainer container)
    {
        super(container);

        setxRelativePos(0.5f);
        setyRelativePos(0.5f);

        setWidth(GUI_WIDTH);
        setHeight(GUI_HEIGHT);

        mainPanel = new GuiAbsolutePane();
        mainPanel.setID("main-panel");
        setMainPanel(mainPanel);

        tile = (GemSocketTile) container.getMainTile();

        GuiLabel title = new GuiLabel(tile.getDisplayName().getFormattedText());
        title.setSize(162, 11);
        title.setTextAlignment(RectAlignment.MIDDLE_CENTER);
        mainPanel.addChild(title, 7, 1);

        GuiLabel inventoryLabel = new GuiLabel(I18n.format("container.inventory"));
        inventoryLabel.setSize(110, 11);
        inventoryLabel.setTextAlignment(RectAlignment.LEFT_CENTER);
        mainPanel.addChild(inventoryLabel, 7, 85);

        survivalInventory = new Rectangle();
        survivalInventory.setSize(162, 76);
        survivalInventory.setID("survival-inventory");
        mainPanel.addChild(survivalInventory, 7, 97);

        GuiAbsolutePane socketPanel = new GuiAbsolutePane();
        socketPanel.setID("socket-panel");
        socketPanel.setSize(58, 74);

        ItemStackView currentGem = new ItemStackView();
        currentGem.setItemTooltip(true);
        currentGem.setSize(10, 10);
        socketPanel.addChild(currentGem, 18, 31);

        currentGem.setOnClickEvent(e ->
        {
            if (Minecraft.getInstance().player.inventory.getItemStack().isEmpty())
                new ServerActionBuilder("GEM_SOCKET_REMOVE").toTile(getTile()).then(answer ->
                {
                    if (answer.getBoolean("accepted"))
                    {
                        Minecraft.getInstance().player.inventory.setItemStack(ItemStack.read(answer.getCompound("cursor")));
                        container.getSlot(36).putStack( ItemStack.read(answer.getCompound("inventory")));
                    }
                }).send();
            else
                new ServerActionBuilder("GEM_SOCKET_ADD").toTile(getTile()).then(answer ->
                {
                    if (answer.getBoolean("accepted"))
                    {
                        Minecraft.getInstance().player.inventory.setItemStack(ItemStack.EMPTY);
                        container.getSlot(36).putStack( ItemStack.read(answer.getCompound("inventory")));
                    }
                }).send();
        });

        mainPanel.addChild(socketPanel, 61, 87 - 74);

        ((FilteredSlot) container.getSlot(36)).setOnChange(stack ->
        {
            ItemStack gemStack = stack.getCapability(GemSocketCapability.GEM_SOCKET_CAPABILITY)
                    .map(socket ->
                    {
                        if (socket.getGems().isEmpty())
                            return ItemStack.EMPTY;
                        return GemstoneItem.createFromGem(socket.getGems().get(0));
                    }).orElse(ItemStack.EMPTY);

            currentGem.setItemStack(gemStack);
        });

        addStylesheet("/assets/" + MFFJam2.MODID + "/css/gem_socket.css");
    }

    @Override
    public GuiAbsolutePane getMainPanel()
    {
        return mainPanel;
    }
}
