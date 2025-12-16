package net.nargi.friulcraft.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.nargi.friulcraft.component.ModDataComponentTypes;
import net.nargi.friulcraft.effect.ModEffects;
import net.nargi.friulcraft.item.ModItems;

import java.util.List;

public class WineBottle extends Item {

    public WineBottle(Settings settings) {
        super(settings.maxDamage(4));
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

            if (!player.getAbilities().creativeMode) {
                if (stack.getDamage() + 1 >= stack.getMaxDamage()) {
                    if (!world.isClient) {
                        world.playSound(
                                null,
                                entity.getX(),
                                entity.getY(),
                                entity.getZ(),
                                SoundEvents.ENTITY_PLAYER_BURP,
                                entity.getSoundCategory(),
                                1.0f,
                                1.0f
                        );
                    }
                    return new ItemStack(ModItems.EMPTY_WINE_BOTTLE);
                }

                stack.damage(1, entity, EquipmentSlot.MAINHAND);
            }
        }

        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        String wineName = stack.get(ModDataComponentTypes.WINE_NAME);
        String wineDescription = stack.get(ModDataComponentTypes.WINE_DESCRIPTION);

        if (wineName == null) wineName = "";
        if (wineDescription == null) wineDescription = "";

        // "Wine" in purple
        Text wineLabel = Text.literal("Wine: ")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x843C7D)));

        // Value in default white
        Text wineValue = Text.literal(wineName);

        // Combine them
        tooltip.add(Text.empty().append(wineLabel).append(wineValue));

        // "Description" in dark green
        Text descLabel = Text.literal("Description: ")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x2E8B57)));

        // Value in default white
        Text descValue = Text.literal(wineDescription);

        tooltip.add(Text.empty().append(descLabel).append(descValue));
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.set(ModDataComponentTypes.WINE_NAME, "");
        stack.set(ModDataComponentTypes.WINE_DESCRIPTION, "");
        return stack;
    }

}