package net.nargi.friulcraft.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

public class ModFoodComponents {
    public static final FoodComponent GRAPES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).build();
    public static final FoodComponent GLASS_CUP = new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).build();
    public static final FoodComponent FRICO_SLICE = new FoodComponent.Builder().nutrition(6).saturationModifier(1.6F).build();
    public static final FoodComponent GRAPES_MUST = new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 1.0F).usingConvertsTo(Items.BOWL).build();

}
