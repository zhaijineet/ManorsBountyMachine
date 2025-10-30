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
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardMultipleRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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

    public static final int CHANCE_SLOT_X_OFFSET = 220;
    public static final int CHANCE_SLOT_Y_OFFSET = 27;
    public static final int CHANCE_SLOT_WIDTH = 18;
    public static final int CHANCE_SLOT_HEIGHT = 18;

    public static final int NUMBER_SLOT_X_OFFSET = 167;
    public static final int NUMBER_SLOT_Y_OFFSET = 0;
    public static final int NUMBER_SLOT_WIDTH = 26;
    public static final int NUMBER_SLOT_HEIGHT = 18;

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
            int size = singleRecipe.outgrowths.size();
            int[][][] pos = {
                    {
                            {118, 33}
                    },
                    {
                            {105, 33},
                            {131, 33}
                    },
                    {
                            {105, 19},
                            {131, 19},
                            {105, 45}
                    },
                    {
                            {105, 19},
                            {131, 19},
                            {105, 45},
                            {131, 45}
                    }
            };
            builder.addOutputSlot(pos[size][0][0], pos[size][0][1])
                    .addItemStack(singleRecipe.output);
            int i = 1;
            for (Map.Entry<ItemStack, Float> entry : singleRecipe.outgrowths.entrySet()) {
                builder.addOutputSlot(pos[size][i][0], pos[size][i][1])
                        .addItemStack(entry.getKey())
                        .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                            if (entry.getValue() < 1) {
                                tooltip.add(
                                        Component.translatable("gui.jei.category.recipe.chance", (int) (entry.getValue() * 100))
                                                .withStyle(ChatFormatting.YELLOW)
                                );
                            }
                        });
                i++;
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
            int size = multipleRecipe.outgrowths.size();
            int[][][] pos = {
                    {
                            {118, 33}
                    },
                    {
                            {105, 33},
                            {131, 33},
                    },
                    {
                            {105, 19},
                            {131, 19},
                            {105, 45}
                    },
                    {
                            {105, 19},
                            {131, 19},
                            {105, 45},
                            {131, 45}
                    }
            };
            builder.addOutputSlot(pos[size][0][0], pos[size][0][1])
                    .addItemStack(multipleRecipe.output);
            int i = 1;
            for (Map.Entry<ItemStack, Float> entry : multipleRecipe.outgrowths.entrySet()) {
                builder.addOutputSlot(pos[size][i][0], pos[size][i][1])
                        .addItemStack(entry.getKey())
                        .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                            if (entry.getValue() < 1) {
                                tooltip.add(
                                        Component.translatable("gui.jei.category.recipe.chance", (int) (entry.getValue() * 100))
                                                .withStyle(ChatFormatting.YELLOW)
                                );
                            }
                        });
                i++;
            }
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
            int size = singleRecipe.outgrowths.size();
            int[][][] pos = {
                    {
                            {117, 32}
                    },
                    {
                            {104, 32},
                            {130, 32},
                    },
                    {
                            {104, 18},
                            {130, 18},
                            {104, 44}
                    },
                    {
                            {104, 18},
                            {130, 18},
                            {104, 44},
                            {130, 44}
                    }
            };
            int i = 0;
            this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, pos[size][i][0], pos[size][i][1]);
            i++;
            for (Map.Entry<ItemStack, Float> entry : singleRecipe.outgrowths.entrySet()) {
                if (entry.getValue() < 1) {
                    this.guiHelper.createDrawable(this.background, CHANCE_SLOT_X_OFFSET, CHANCE_SLOT_Y_OFFSET, CHANCE_SLOT_WIDTH, CHANCE_SLOT_HEIGHT).draw(guiGraphics, pos[size][i][0], pos[size][i][1]);
                } else {
                    this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, pos[size][i][0], pos[size][i][1]);
                }
                i++;
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
            int size = multipleRecipe.outgrowths.size();
            int[][][] pos = {
                    {
                            {117, 32}
                    },
                    {
                            {104, 32},
                            {130, 32},
                    },
                    {
                            {104, 18},
                            {130, 18},
                            {104, 44}
                    },
                    {
                            {104, 18},
                            {130, 18},
                            {104, 44},
                            {130, 44}
                    }
            };
            int i = 0;
            this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, pos[size][i][0], pos[size][i][1]);
            i++;
            for (Map.Entry<ItemStack, Float> entry : multipleRecipe.outgrowths.entrySet()) {
                if (entry.getValue() < 1) {
                    this.guiHelper.createDrawable(this.background, CHANCE_SLOT_X_OFFSET, CHANCE_SLOT_Y_OFFSET, CHANCE_SLOT_WIDTH, CHANCE_SLOT_HEIGHT).draw(guiGraphics, pos[size][i][0], pos[size][i][1]);
                } else {
                    this.guiHelper.createDrawable(this.background, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT).draw(guiGraphics, pos[size][i][0], pos[size][i][1]);
                }
                i++;
            }
        });
    }

    @Override
    public void draw(CuttingBoardRecipeWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.renderSingleRecipe(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.renderMultipleRecipe(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }
}
