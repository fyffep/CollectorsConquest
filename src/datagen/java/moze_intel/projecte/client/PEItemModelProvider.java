package moze_intel.projecte.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import moze_intel.projecte.ClientRegistration;
import moze_intel.projecte.FieldReflectionHelper;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.registration.impl.BlockRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.utils.RegistryUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PEItemModelProvider extends ItemModelProvider {

	@SuppressWarnings("rawtypes")
	private final FieldReflectionHelper<ModelBuilder, Map<String, String>> MODEL_TEXTURES = new FieldReflectionHelper<>(ModelBuilder.class, "textures", HashMap::new);
	private static final TrimModelDataHelper<?> TRIM_HELPER = new TrimModelDataHelper<>();

	public PEItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, PECore.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		generateChests();
		//Note: We don't actually have a manual, but I moved this model over to data gen anyways
		generated("manual", modLoc("item/book"));
	}

	private void generateChests() {
		generateChest(PEBlocks.CITRINE_SANCTUM);
	}

	private void generateChest(BlockRegistryObject<?, ?> block) {
		String name = getName(block);
		withExistingParent(name, modLoc("block/base_chest")).texture("chest", modLoc("block/" + name));
	}

	private void blockParentModel(BlockRegistryObject<?, ?>... blocks) {
		for (BlockRegistryObject<?, ?> block : blocks) {
			String name = getName(block);
			withExistingParent(name, modLoc("block/" + name));
		}
	}

	protected ResourceLocation itemTexture(ItemLike itemProvider) {
		return modLoc("item/" + getName(itemProvider));
	}

	protected void registerGenerated(ItemLike... itemProviders) {
		for (ItemLike itemProvider : itemProviders) {
			generated(itemProvider);
		}
	}

	protected ItemModelBuilder generated(ItemLike itemProvider) {
		return generated(itemProvider, itemTexture(itemProvider));
	}

	protected ItemModelBuilder generated(ItemLike itemProvider, ResourceLocation texture) {
		return generated(getName(itemProvider), texture);
	}

	protected ItemModelBuilder generated(String name, ResourceLocation texture) {
		return withExistingParent(name, "item/generated").texture("layer0", texture);
	}

	protected ItemModelBuilder handheld(ItemLike itemProvider, ResourceLocation texture) {
		return handheld(getName(itemProvider), texture);
	}

	protected ItemModelBuilder handheld(String name, ResourceLocation texture) {
		return withExistingParent(name, "item/handheld").texture("layer0", texture);
	}

	private static String getName(ItemLike itemProvider) {
		return RegistryUtils.getPath(itemProvider.asItem());
	}

	protected ItemModelBuilder armorWithTrim(ItemLike itemProvider, ResourceLocation texture) {
		ItemModelBuilder builder = generated(itemProvider, texture);
		ArmorItem.Type type = ((ArmorItem) itemProvider.asItem()).getType();
		TRIM_HELPER.forEachTrim((trimId, itemModelIndex) -> {
					ItemModelBuilder override = withExistingParent(builder.getLocation().withSuffix("_" + trimId + "_trim").getPath(), "item/generated")
							.texture("layer0", texture);
					//Directly add the layer1 to the texture map as the file doesn't actually exist
					MODEL_TEXTURES.getValue(override).put("layer1", new ResourceLocation(type.getName() + "_trim_" + trimId).withPrefix("trims/items/").toString());
					builder.override()
							.predicate(ItemModelGenerators.TRIM_TYPE_PREDICATE_ID, itemModelIndex)
							.model(override);
				}
		);
		return builder;
	}

	private static class TrimModelDataHelper<TMD_CLASS> {

		private final FieldReflectionHelper<ItemModelGenerators, List<TMD_CLASS>> generatedTrimModels = new FieldReflectionHelper<>(ItemModelGenerators.class, "f_265952_", Collections::emptyList);
		private final FieldReflectionHelper<TMD_CLASS, String> name;
		private final FieldReflectionHelper<TMD_CLASS, Float> itemModelIndex;

		public TrimModelDataHelper() {
			Class<TMD_CLASS> tmdClass;
			try {
				tmdClass = (Class<TMD_CLASS>) Class.forName("net.minecraft.data.models.ItemModelGenerators$TrimModelData");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			name = new FieldReflectionHelper<>(tmdClass, "f_265890_", () -> null);
			itemModelIndex = new FieldReflectionHelper<>(tmdClass, "f_265849_", () -> null);
		}

		public void forEachTrim(BiConsumer<String, Float> consumer) {
			List<TMD_CLASS> trims = generatedTrimModels.getValue(null);
			for (TMD_CLASS trim : trims) {
				String trimName = name.getValue(trim);
				Float modelIndex = itemModelIndex.getValue(trim);
				consumer.accept(trimName, modelIndex);
			}
		}
	}
}
