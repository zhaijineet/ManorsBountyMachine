package net.zhaiji.manorsbountymachine.item;

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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.network.NetworkHooks;
import net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.ShakerMenu;
import net.zhaiji.manorsbountymachine.recipe.ShakerRecipe;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import net.zhaiji.manorsbountymachine.util.SoundUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.INPUT_SLOTS;
import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.OUTPUT;

public class ShakerItem extends Item {
    public ShakerItem() {
        super(new Properties().stacksTo(1));
    }

    public static Optional<ShakerRecipe> getRecipe(Level level, RecipeWrapper recipeWrapper) {
        return level.getRecipeManager().getRecipeFor(InitRecipe.SHAKER_RECIPE_TYPE.get(), recipeWrapper, level);
    }

    public static List<ShakerRecipe> getAllRecipe(Level level) {
        return level.getRecipeManager().getAllRecipesFor(InitRecipe.SHAKER_RECIPE_TYPE.get());
    }

    public static void setCanStartUsing(ItemStack itemStack, boolean canStartUsing) {
        itemStack.getOrCreateTag().putBoolean("canStartUsing", canStartUsing);
    }

    public static boolean canStartUsing(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("canStartUsing");
    }

    public static void addOrSpawnItem(LivingEntity livingEntity, ItemStack itemStack) {
        if (livingEntity instanceof Player player) {
            if (!player.getInventory().add(itemStack)) {
                player.spawnAtLocation(itemStack);
            }
        } else {
            livingEntity.spawnAtLocation(itemStack);
        }
    }

    public void craftItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!canStartUsing(itemStack)) return;
        if (!itemStack.is(this)) return;
        itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            RecipeWrapper recipeWrapper = new RecipeWrapper((ItemStackHandler) iItemHandler);
            Optional<ShakerRecipe> recipe = getRecipe(level, recipeWrapper);
            recipe.ifPresent(shakerRecipe -> {
                ItemStack output = shakerRecipe.assemble(recipeWrapper, level.registryAccess());
                int count = output.getCount();
                int multiple = Math.min(iItemHandler.getSlotLimit(0), output.getMaxStackSize()) / count;
                multiple = Math.min(multiple, recipeWrapper.getItem(OUTPUT).getCount() / count);
                for (int slot : INPUT_SLOTS) {
                    ItemStack input = recipeWrapper.getItem(slot);
                    if (input.isEmpty()) continue;
                    multiple = Math.min(multiple, input.getCount());
                }
                List<ItemStack> craftRemaining = new ArrayList<>();
                for (int i = 0; i < recipeWrapper.getContainerSize(); i++) {
                    ItemStack input = recipeWrapper.getItem(i);
                    ItemStack remaining = MachineUtil.getCraftRemaining(input, multiple);
                    if (ManorsBountyCompat.isDamageableMaterial(input)) {
                        ManorsBountyCompat.damageItem(input, level);
                        if (!input.isEmpty()) {
                            remaining = ItemStack.EMPTY;
                        }
                    } else {
                        input.shrink(multiple);
                    }
                    if (input.isEmpty() && !remaining.isEmpty()) {
                        recipeWrapper.setItem(i, remaining);
                    } else if (!remaining.isEmpty()) {
                        craftRemaining.add(remaining);
                    }
                }
                if (livingEntity.getOffhandItem().isEmpty()) {
                    livingEntity.setItemInHand(InteractionHand.OFF_HAND, output.copyWithCount(count * multiple));
                } else {
                    addOrSpawnItem(livingEntity, output.copyWithCount(count * multiple));
                }
                craftRemaining.forEach(remaining -> addOrSpawnItem(livingEntity, remaining));
                setCanStartUsing(itemStack, false);
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
        return canStartUsing(pStack) ? 40 : 0;
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
                    pLevel.playSound(null, pPlayer.getOnPos(), InitSoundEvent.SHAKER_OPEN.get(), SoundSource.PLAYERS);
                }
                return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
            } else if (canStartUsing(itemStack)) {
                pPlayer.startUsingItem(pUsedHand);
                if (pLevel.isClientSide()) {
                    SoundUtil.playShakerSoundInstance(pPlayer);
                }
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
