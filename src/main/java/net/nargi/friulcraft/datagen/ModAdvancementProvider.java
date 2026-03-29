package net.nargi.friulcraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.block.ModBlocks;
import net.nargi.friulcraft.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {

    public ModAdvancementProvider(FabricDataOutput output,
                                  CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {

        AdvancementEntry friulcraftAdvancements = Advancement.Builder.create()
                .display(
                        ModItems.GRAPES,
                        Text.translatable("friulcraft.advancements.root"),
                        Text.translatable("friulcraft.advancements.root_description"),
                        Identifier.of("friulcraft", "textures/gui/advancements/backgrounds/friulcraft.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("root",
                        InventoryChangedCriterion.Conditions.items(ModItems.GRAPES))
                .build(consumer, "friulcraft:root");

        AdvancementEntry friulcraftAdvancementsGrapesMust = Advancement.Builder.create().parent(friulcraftAdvancements)
                .display(
                        ModItems.GRAPES_MUST,
                        Text.translatable("friulcraft.advancements.must"),
                        Text.translatable("friulcraft.advancements.must_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("grapes_must", InventoryChangedCriterion.Conditions.items(ModItems.STUPID_ITEM_FOR_ADVANCEMENTS))
                .build(consumer, "friulcraft:grapes_must");

        AdvancementEntry friulcraftAdvancementsGetFermentationTable = Advancement.Builder.create().parent(friulcraftAdvancementsGrapesMust)
                .display(
                        ModBlocks.FERMENTATION_BARREL,
                        Text.translatable("friulcraft.advancements.barrel"),
                        Text.translatable("friulcraft.advancements.barrel_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("get_fermentation_table", InventoryChangedCriterion.Conditions.items(ModBlocks.FERMENTATION_BARREL))
                .build(consumer, "friulcraft:get_fermentation_table");

        AdvancementEntry friulcraftAdvancementsGetWineBottle = Advancement.Builder.create().parent(friulcraftAdvancementsGetFermentationTable)
                .display(
                        ModItems.WINE_BOTTLE,
                        Text.translatable("friulcraft.advancements.wine_bottle"),
                        Text.translatable("friulcraft.advancements.wine_bottle_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("get_wine_bottle", InventoryChangedCriterion.Conditions.items(ModItems.WINE_BOTTLE))
                .build(consumer, "friulcraft:get_wine_bottle");

        AdvancementEntry friulcraftAdvancementsGetDrunk = Advancement.Builder.create().parent(friulcraftAdvancementsGetWineBottle)
                .display(
                        ModItems.EMPTY_WINE_BOTTLE,
                        Text.translatable("friulcraft.advancements.drunk"),
                        Text.translatable("friulcraft.advancements.drunk_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("get_drunk", InventoryChangedCriterion.Conditions.items(ModItems.STUPID_ITEM_FOR_ADVANCEMENTS))
                .build(consumer, "friulcraft:get_drunk");

        AdvancementEntry friulcraftAdvancementsPourWine = Advancement.Builder.create().parent(friulcraftAdvancementsGetFermentationTable)
                .display(
                        ModItems.WINE_GLASS,
                        Text.translatable("friulcraft.advancements.pour"),
                        Text.translatable("friulcraft.advancements.pour_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("pour_wine", InventoryChangedCriterion.Conditions.items(ModItems.STUPID_ITEM_FOR_ADVANCEMENTS))
                .build(consumer, "friulcraft:pour_wine");

        AdvancementEntry friulcraftAdvancementsGetLeaves = Advancement.Builder.create().parent(friulcraftAdvancements)
                .display(
                        ModBlocks.VINE_PLANT,
                        Text.translatable("friulcraft.advancements.vineyards"),
                        Text.translatable("friulcraft.advancements.vineyards_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("get_leaves", InventoryChangedCriterion.Conditions.items(ModBlocks.VINE_PLANT_LEAVES, ModBlocks.VINE_PLANT))
                .build(consumer, "friulcraft:get_leaves");

        AdvancementEntry friulcraftAdvancementsFrico = Advancement.Builder.create().parent(friulcraftAdvancements)
                .display(
                        ModBlocks.FRICO,
                        Text.translatable("friulcraft.advancements.frico"),
                        Text.translatable("friulcraft.advancements.frico_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("frico", InventoryChangedCriterion.Conditions.items(ModBlocks.FRICO))
                .build(consumer, "friulcraft:frico");

        AdvancementEntry friulcraftAdvancementsFricoSlice = Advancement.Builder.create().parent(friulcraftAdvancementsFrico)
                .display(
                        ModItems.FRICO_SLICE,
                        Text.translatable("friulcraft.advancements.frico_slice"),
                        Text.translatable("friulcraft.advancements.frico_slice_description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(1000))
                .criterion("frico_slice", InventoryChangedCriterion.Conditions.items(Items.SPRUCE_LOG))
                .build(consumer, "friulcraft:frico_slice");
    }
}