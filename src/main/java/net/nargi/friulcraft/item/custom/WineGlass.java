package net.nargi.friulcraft.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.nargi.friulcraft.FriulCraftMod;
import net.nargi.friulcraft.effect.ModEffects;
import net.nargi.friulcraft.item.ModItems;
import net.nargi.friulcraft.util.ICustomIntDrunkLvl;

public class WineGlass extends Item {

    public WineGlass(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {

            if (!world.isClient) {
                player.addStatusEffect(new StatusEffectInstance(
                        ModEffects.UMAMI,
                        1800,
                        0
                ));
            }

            if (player instanceof ICustomIntDrunkLvl customPlayer) {
                int current = customPlayer.getCustomInt();
                current++;
                customPlayer.setCustomInt(current);

                if (current >= 5) {

                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.DRUNK,
                            1800,
                            0
                    ));
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            200,
                            0
                    ));
                    customPlayer.setCustomInt(current - 1);
                    FriulCraftMod.LOGGER.info("Drunk lvl: " + current);
                }

                if (current == 4) {
                    // Give the player a bad effect
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            200,
                            0
                    ));
                }
            }

            if (!player.isCreative()) {
                return new ItemStack(ModItems.EMPTY_WINE_GLASS);
            }
        }

        return stack;
    }
}