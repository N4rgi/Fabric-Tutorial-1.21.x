package net.nargi.friulcraft.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;

public class FermentationBarrelScreen extends HandledScreen<fermentation_barrel_screen_handler> {
    public static final Identifier GUI_TEXTURE =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/fermentation_barrel/fermentation_barrel_gui.png");
    public static final Identifier BUBBLES_TEXTURE =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/bubbles_progress.png");
    public static final Identifier ARROW_TEXTURE =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/arrow_progress.png");
    public static final Identifier GRAPES_PROGRESS_1 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_1.png");
    public static final Identifier GRAPES_PROGRESS_2 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_2.png");
    public static final Identifier GRAPES_PROGRESS_3 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_3.png");
    public static final Identifier GRAPES_PROGRESS_4 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_4.png");
    public static final Identifier GRAPES_PROGRESS_5 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_5.png");
    public static final Identifier GRAPES_PROGRESS_6 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_6.png");
    public static final Identifier GRAPES_PROGRESS_7 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_7.png");
    public static final Identifier GRAPES_PROGRESS_8 =
            Identifier.of(FriulCraftMod.MOD_ID, "textures/gui/grapes_progress_8.png");



    private static final int[] BUBBLE_PROGRESS = new int[]{0, 4, 8, 12, 16, 20, 24, 26};


    public FermentationBarrelScreen(fermentation_barrel_screen_handler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int grapesProgress = handler.getGrapesProgress();

        if (grapesProgress == 1) {

            context.drawTexture(GRAPES_PROGRESS_1, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 2) {

            context.drawTexture(GRAPES_PROGRESS_2, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 3) {

            context.drawTexture(GRAPES_PROGRESS_3, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 4) {

            context.drawTexture(GRAPES_PROGRESS_4, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 5) {

            context.drawTexture(GRAPES_PROGRESS_5, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 6) {

            context.drawTexture(GRAPES_PROGRESS_6, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 7) {

            context.drawTexture(GRAPES_PROGRESS_7, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        } else if (grapesProgress == 8) {

            context.drawTexture(GRAPES_PROGRESS_8, x + 114, y + 28, 0, 0,  36, 30, 36, 30);

        }


        renderProgressArrow(context, x ,y);

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        int m = ((fermentation_barrel_screen_handler)this.handler).getBubbleOn();

        int n = BUBBLE_PROGRESS[m / 2 % 7];
        if (n > 0) {
            context.drawTexture(
                    BUBBLES_TEXTURE,
                    i + 75, j + 22,0, 0, n, 16, 25, 16
            );
        }

    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(ARROW_TEXTURE, x + 76, y + 39, 0, 0,
                    handler.getScaledArrowProgress(), 8, 26, 8);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
