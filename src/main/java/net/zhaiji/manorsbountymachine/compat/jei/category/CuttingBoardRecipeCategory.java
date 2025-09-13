package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardMultipleRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardRecipeCategory extends BaseRecipeCategory<CuttingBoardRecipeWrapper> {
    public static final ResourceLocation CUTTING_BOARD_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/cutting_board_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.cutting_board";

    public static final int X_START_POS = 22;
    public static final int Y_START_POS = 14;
    public static final int NUMBER_X_START_POS = 14;
    public static final int NUMBER_Y_START_POS = 14;
    public static final int SPACING = 8;
    public static final int X_MOVE_POS = 7;

    public static final int SLOT_X_OFFSET = 175;
    public static final int SLOT_Y_OFFSET = 0;
    public static final int SLOT_WIDTH = 18;
    public static final int SLOT_HEIGHT = 18;

    public static final int NUMBER_SLOT_X_OFFSET = 167;
    public static final int NUMBER_SLOT_Y_OFFSET = 0;
    public static final int NUMBER_SLOT_WIDTH = 26;
    public static final int NUMBER_SLOT_HEIGHT = 18;

    public static final int OUTPUT_X_OFFSET = 220;
    public static final int OUTPUT_Y_OFFSET = 0;
    public static final int OUTPUT_WIDTH = 26;
    public static final int OUTPUT_HEIGHT = 26;

    public CuttingBoardRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.CUTTING_BOARD, CUTTING_BOARD_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.CUTTING_BOARD.get());
    }

    public int getInputSize(CuttingBoardMultipleRecipe recipe) {
        return (int) recipe.input.stream().filter(ingredient -> !ingredient.isEmpty()).count();
    }

    public void setSingleRecipe(IRecipeLayoutBuilder builder, CuttingBoardRecipeWrapper recipe, IFocusGroup focuses) {
        recipe.getSingleRecipe().ifPresent(singleRecipe -> {
            builder.addInputSlot(23, 33)
                    .addIngredients(singleRecipe.input);
            builder.addInputSlot(71, 33)
                    .addIngredients(singleRecipe.tool);
            builder.addOutputSlot(109, 32)
                    .addItemStack(singleRecipe.output);
            if (singleRecipe.hasOutgrowth()) {
                builder.addInputSlot(133, 32)
                        .addItemStack(singleRecipe.outgrowth)
                        .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                            tooltip.add(
                                    Component.translatable("gui.jei.category.recipe.chance", (int) (singleRecipe.outgrowthChance * 100))
                                            .withStyle(ChatFormatting.YELLOW)
                            );
                        });
            }
        });
    }

    public void setMultipleRecipe(IRecipeLayoutBuilder builder, CuttingBoardRecipeWrapper recipe, IFocusGroup focuses) {
        recipe.getMultipleRecipe().ifPresent(multipleRecipe -> {
            for (int i = 0; i < this.getInputSize(multipleRecipe); i++) {
                builder.addInputSlot(
                        X_START_POS + 1 - (i / 2 * X_MOVE_POS) + (i % 2 * (SPACING + SLOT_WIDTH)),
                        Y_START_POS + 1 + (i / 2 * SLOT_HEIGHT)
                ).addIngredients(multipleRecipe.input.get(i));
            }
            if (multipleRecipe.hasTool()) {
                builder.addInputSlot(71, 33)
                        .addIngredients(multipleRecipe.tool);
            }
            builder.addOutputSlot(109, 32)
                    .addItemStack(multipleRecipe.output);
        });
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CuttingBoardRecipeWrapper recipe, IFocusGroup focuses) {
        this.setSingleRecipe(builder, recipe, focuses);
        this.setMultipleRecipe(builder, recipe, focuses);
    }

    public void renderSingleRecipe(CuttingBoardRecipeWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        recipe.getSingleRecipe().ifPresent(singleRecipe -> {
            this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, 22, 32);
            this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, 70, 32);
            this.guiHelper.createDrawable(this.background, OUTPUT_X_OFFSET, OUTPUT_Y_OFFSET, OUTPUT_WIDTH, OUTPUT_HEIGHT).draw(guiGraphics, 104, 27);
            if (singleRecipe.hasOutgrowth()) {
                this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, 132, 31);
            }
        });
    }

    public void renderMultipleRecipe(CuttingBoardRecipeWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        recipe.getMultipleRecipe().ifPresent(multipleRecipe -> {
            for (int i = 0; i < this.getInputSize(multipleRecipe); i++) {
                if (multipleRecipe.isShaped) {
                    this.guiHelper.createDrawable(
                                    this.background,
                                    NUMBER_SLOT_X_OFFSET - (i / 2 * X_MOVE_POS) + (i % 2 * NUMBER_SLOT_WIDTH),
                                    NUMBER_SLOT_Y_OFFSET + (i / 2 * NUMBER_SLOT_HEIGHT),
                                    NUMBER_SLOT_WIDTH,
                                    NUMBER_SLOT_HEIGHT
                            )
                            .draw(
                                    guiGraphics,
                                    NUMBER_X_START_POS - (i / 2 * X_MOVE_POS) + (i % 2 * NUMBER_SLOT_WIDTH),
                                    NUMBER_Y_START_POS + (i / 2 * NUMBER_SLOT_HEIGHT)
                            );
                } else {
                    this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT)
                            .draw(
                                    guiGraphics,
                                    X_START_POS - (i / 2 * X_MOVE_POS) + (i % 2 * (SPACING + SLOT_WIDTH)),
                                    Y_START_POS + (i / 2 * SLOT_HEIGHT)
                            );
                }
            }
            if (multipleRecipe.hasTool()) {
                this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, 70, 32);
            }
            this.guiHelper.createDrawable(this.background, OUTPUT_X_OFFSET, OUTPUT_Y_OFFSET, OUTPUT_WIDTH, OUTPUT_HEIGHT).draw(guiGraphics, 104, 27);
        });
    }

    @Override
    public void draw(CuttingBoardRecipeWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.renderSingleRecipe(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.renderMultipleRecipe(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }
}
