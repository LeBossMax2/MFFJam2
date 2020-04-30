package net.mffjam2.client.gui;

import lombok.Getter;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.tile.GemCrusherTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.voxelindustry.brokkgui.data.RectAlignment;
import net.voxelindustry.brokkgui.element.GuiLabel;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.shape.Rectangle;
import net.voxelindustry.brokkgui.sprite.Texture;
import net.voxelindustry.brokkgui.wrapper.container.BrokkGuiContainer;
import net.voxelindustry.steamlayer.container.BuiltContainer;

public class GemCrusherGui extends BrokkGuiContainer<BuiltContainer>
{
    public static final float GUI_WIDTH   = 176;
    public static final float GUI_HEIGHT  = 180;
    public static final int   FONT_HEIGHT = Minecraft.getInstance().fontRenderer.FONT_HEIGHT;

    private static final Texture BACKGROUND = new Texture(MFFJam2.MODID + ":textures/gui/gem_crusher.png", 0, 0, GUI_WIDTH / 256, GUI_HEIGHT / 256);

    private final Rectangle survivalInventory;
    private final Rectangle tileInventory;

    private   GuiAbsolutePane mainPanel;

    @Getter
    private GemCrusherTile tile;

    public GemCrusherGui(BuiltContainer container)
    {
        super(container);

        setxRelativePos(0.5f);
        setyRelativePos(0.5f);

        setWidth(GUI_WIDTH);
        setHeight(GUI_HEIGHT);

        mainPanel = new GuiAbsolutePane();
        mainPanel.setBackgroundTexture(getBackgroundTexture());
        setMainPanel(mainPanel);

        tile = (GemCrusherTile) container.getMainTile();
        
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
        tileInventory.setSize(162, 84);
        tileInventory.setID("tile-inventory");
        mainPanel.addChild(tileInventory, 7, 0);
    }

    protected Texture getBackgroundTexture()
    {
        return BACKGROUND;
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
