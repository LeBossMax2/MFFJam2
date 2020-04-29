package net.mffjam2.client.gui;

import lombok.Getter;
import net.mffjam2.MFFJam2;
import net.mffjam2.common.tile.DummyTile;
import net.minecraft.client.Minecraft;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.shape.Rectangle;
import net.voxelindustry.brokkgui.sprite.Texture;
import net.voxelindustry.brokkgui.wrapper.container.BrokkGuiContainer;
import net.voxelindustry.steamlayer.container.BuiltContainer;

public class DummyGui extends BrokkGuiContainer<BuiltContainer>
{
    public static final float GUI_WIDTH   = 176;
    public static final float GUI_HEIGHT  = 216;
    public static final int   FONT_HEIGHT = Minecraft.getInstance().fontRenderer.FONT_HEIGHT;

    private static final Texture BACKGROUND = new Texture(MFFJam2.MODID + ":textures/gui/dummy/dummy_background.png");

    private final Rectangle survivalInventory;

    protected GuiAbsolutePane body;
    private   GuiAbsolutePane mainPanel;

    @Getter
    private DummyTile tile;

    public DummyGui(BuiltContainer container)
    {
        super(container);

        mainPanel = new GuiAbsolutePane();
        setMainPanel(mainPanel);

        tile = (DummyTile) container.getMainTile();

        body = new GuiAbsolutePane();
        body.setBackgroundTexture(getBackgroundTexture());

        survivalInventory = new Rectangle();
        survivalInventory.setSize(162, 76);
        survivalInventory.setID("survival-inventory");
        body.addChild(survivalInventory, 7, 0);
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
        body.setSize(width - xOffset, height);

        body.setxTranslate(xOffset);
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
