package net.zhaiji.manorsbountymachine.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.network.NetworkHooks;
import net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider;
import net.zhaiji.manorsbountymachine.menu.ShakerMenu;
import net.zhaiji.manorsbountymachine.recipe.ShakerRecipe;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ShakerItem extends Item {
    public ShakerItem() {
        super(new Properties().stacksTo(1));
    }

    public static List<ShakerRecipe> getAllRecipe(Level level) {
        return level.getRecipeManager().getAllRecipesFor(InitRecipe.SHAKER_RECIPE_TYPE.get());
    }

    public static Optional<ShakerRecipe> getRecipe(Level level, RecipeWrapper recipeWrapper) {
        return level.getRecipeManager().getRecipeFor(InitRecipe.SHAKER_RECIPE_TYPE.get(), recipeWrapper, level);
    }

    public boolean canStartUsing(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("canStartUsing");
    }

    public void craftItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!canStartUsing(itemStack)) return;
        if (!itemStack.is(this)) return;
        itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            RecipeWrapper recipeWrapper = new RecipeWrapper((ItemStackHandler) iItemHandler);
            Optional<ShakerRecipe> recipe = this.getRecipe(level, recipeWrapper);
            recipe.ifPresent(shakerRecipe -> {
                NonNullList<ItemStack> remaining = shakerRecipe.getRemainingItems(recipeWrapper);
                for (int i = 0; i < remaining.size(); i++) {
                    recipeWrapper.setItem(i, remaining.get(i));
                }
                ItemStack output = shakerRecipe.assemble(recipeWrapper, level.registryAccess());
                if (livingEntity.getOffhandItem().is(Items.AIR)) {
                    livingEntity.setItemInHand(InteractionHand.OFF_HAND, output);
                } else {
                    if (livingEntity instanceof Player player) {
                        if (!player.getInventory().add(output)) {
                            player.spawnAtLocation(output);
                        }
                    } else {
                        livingEntity.spawnAtLocation(output);
                    }
                }
                itemStack.getOrCreateTag().putBoolean("canStartUsing", false);
                level.playSound(null, livingEntity.blockPosition(), SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS);
            });
        });
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return this.canStartUsing(pStack) ? 32 : 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            if (pPlayer.isShiftKeyDown()) {
                if (!pLevel.isClientSide()) {
                    itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
                        NetworkHooks.openScreen(
                                (ServerPlayer) pPlayer,
                                new SimpleMenuProvider(
                                        (containerId, inventory, player) -> new ShakerMenu(containerId, inventory, iItemHandler),
                                        itemStack.getDisplayName())
                        );
                    });
                }
                return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
            } else {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.success(itemStack);
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.craftItem(pStack, pLevel, pLivingEntity);
        });
        return pStack;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ShakerCapabilityProvider();
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
