package net.nargi.friulcraft.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;

public class UmamiEffect extends StatusEffect {

    private static final int TICK_INTERVAL = 20;

    public UmamiEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

//    @Override
//    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
//        if (entity instanceof PlayerEntity player) {
//
//            if (player.age % TICK_INTERVAL == 0) {
//                HungerManager hunger = player.getHungerManager();
//                float added = 0.5F * (amplifier + 1);
//
//                hunger.setSaturationLevel(
//                        Math.min(
//                                hunger.getSaturationLevel() + added,
//                                hunger.getFoodLevel()
//                        )
//                );
//            }
//        }
//
//        return true; // <-- THIS is the important part
//    }
}
