package net.mffjam2.client.gui;

import lombok.Getter;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.network.StartInfuserMessage;
import net.mffjam2.common.tile.GemInfuserTile;
import net.mffjam2.setup.JamNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.voxelindustry.brokkgui.data.RectAlignment;
import net.voxelindustry.brokkgui.element.GuiLabel;
import net.voxelindustry.brokkgui.element.input.GuiButton;
import net.voxelindustry.brokkgui.event.ClickEvent;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.shape.Rectangle;
import net.voxelindustry.brokkgui.sprite.Texture;
import net.voxelindustry.brokkgui.wrapper.container.BrokkGuiContainer;
import net.voxelindustry.steamlayer.container.BuiltContainer;

@OnlyIn(Dist.CLIENT)
public class GemInfuserGui extends BrokkGuiContainer<BuiltContainer>
{
    public static final float GUI_WIDTH   = 176;
    public static final float GUI_HEIGHT  = 180;
    public static final int   FONT_HEIGHT = Minecraft.getInstance().fontRenderer.FONT_HEIGHT;

    private static final Texture BUTTON_BACKGROUND = new Texture("minecraft:textures/gui/widgets.png", 0, 66.0f / 256.0f, 200.0f / 256.0f, 86.0f / 256.0f);

    private final Rectangle survivalInventory;
    private final Rectangle tileInventory;

    private   GuiAbsolutePane mainPanel;

    @Getter
    private GemInfuserTile tile;

    public GemInfuserGui(BuiltContainer container)
    {
        super(container);

        setxRelativePos(0.5f);
        setyRelativePos(0.5f);

        setWidth(GUI_WIDTH);
        setHeight(GUI_HEIGHT);

        mainPanel = new GuiAbsolutePane();
        mainPanel.setID("main-panel");
        setMainPanel(mainPanel);

        tile = (GemInfuserTile) container.getMainTile();
        
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

        tileInventory = new Rectangle();
        tileInventory.setSize(54, 54);
        tileInventory.setID("infuser-inventory");
        mainPanel.addChild(tileInventory, 61, 28);
        
        GuiButton button = new GuiButton(I18n.format(MFFJam2.MODID + ".gui.gem_infuse.button"));
        //button.setSize(200, 20);
        button.setBackgroundTexture(BUTTON_BACKGROUND);
        button.setID("infuse");
        button.setOnClickEvent(this::startInfusion);
        button.getDisabledProperty().bind(tile.getCanInfuse().combine(tile.getActive(), (i, c) -> !i || c));

        mainPanel.addChild(button, 120, 50);
    }
    
    private void startInfusion(ClickEvent event)
    {
    	JamNetwork.CHANNEL.sendToServer(new StartInfuserMessage(this.getContainer().windowId));
    }

    protected int getSurvivalInventoryOffset()
    {
        // 12 from 216 (canvas height) - 204 (gui height)
        return 88 + 12;
    }

    private void refreshSize(float width, float height, float xOffset)
    {
    	mainPanel.setSize(width - xOffset, height);

    	mainPanel.setxTranslate(xOffset);
        setxOffset((int) -xOffset / 2);

        survivalInventory.setyTranslate(height - getSurvivalInventoryOffset());
    }

    public void refreshOffset(float xOffset)
    {
        refreshSize(GUI_WIDTH + xOffset, GUI_HEIGHT, xOffset);
    }

    @Override
    public GuiAbsolutePane getMainPanel()
    {
        return mainPanel;
    }
}
