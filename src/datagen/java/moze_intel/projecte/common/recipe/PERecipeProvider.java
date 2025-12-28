package moze_intel.projecte.common.recipe;

import java.util.function.Consumer;

import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.gameObjs.registries.PEItems;
import moze_intel.projecte.utils.RegistryUtils;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class PERecipeProvider extends RecipeProvider {

	public PERecipeProvider(PackOutput output) {
		super(output);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
		citrineSanctumRecipes(consumer);
		infiniteTalismanRecipes(consumer);
	}

	private static void addCustomRecipeSerializer(Consumer<FinishedRecipe> consumer, SimpleCraftingRecipeSerializer<?> serializer) {
		SpecialRecipeBuilder.special(serializer).save(consumer, RegistryUtils.getName(serializer).toString());
	}

	private static void citrineSanctumRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PEBlocks.CITRINE_SANCTUM)
				.pattern("BBB")
				.pattern("O O")
				.pattern("OLO")
				.define('L', Items.LAVA_BUCKET)
				.define('O', Items.COAL_BLOCK)
				.define('B', Items.COBBLESTONE)
				.save(consumer);
	}

	private static void infiniteTalismanRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, PEItems.INFINITE_IRON_TROPHY)
				.requires(Tags.Items.GLASS)
				.requires(Tags.Items.INGOTS_GOLD)
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.GEMS_LAPIS)
				.requires(Tags.Items.ENDER_PEARLS)
				.unlockedBy("has_citrine_sanctum", has(PEBlocks.CITRINE_SANCTUM))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, PEItems.INFINITE_IRON_TROPHY)
				.requires(Tags.Items.SEEDS)
				.requires(Items.COOKED_COD)
				.requires(Tags.Items.EGGS)
				.requires(Items.APPLE)
				.requires(Items.MILK_BUCKET)
				.unlockedBy("has_citrine_sanctum", has(PEBlocks.CITRINE_SANCTUM))
				.save(consumer);
	}




	private static String getName(ItemLike item) {
		return RegistryUtils.getPath(item.asItem());
	}

	protected static InventoryChangeTrigger.TriggerInstance hasItems(ItemLike... items) {
		return InventoryChangeTrigger.TriggerInstance.hasItems(items);
	}

	@SafeVarargs
	protected static InventoryChangeTrigger.TriggerInstance hasItems(ItemLike item, TagKey<Item>... tags) {
		return hasItems(new ItemLike[]{item}, tags);
	}

	@SafeVarargs
	protected static InventoryChangeTrigger.TriggerInstance hasItems(ItemLike[] items, TagKey<Item>... tags) {
		ItemPredicate[] predicates = new ItemPredicate[items.length + tags.length];
		for (int i = 0; i < items.length; ++i) {
			predicates[i] = ItemPredicate.Builder.item().of(items[i]).build();
		}
		for (int i = 0; i < tags.length; ++i) {
			predicates[items.length + i] = ItemPredicate.Builder.item().of(tags[i]).build();
		}
		return inventoryTrigger(predicates);
	}
}