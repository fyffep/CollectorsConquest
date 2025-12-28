package moze_intel.projecte.client;

import java.util.function.Function;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.registration.impl.BlockRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.utils.RegistryUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PEBlockStateProvider extends BlockStateProvider {

	public PEBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, PECore.MODID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		registerChests();
	}

	private void registerChests() {
		models().withExistingParent("base_chest", "block/block")
				//Body
				.element()
				.from(1, 0, 1)
				.to(15, 10, 15)
				.face(Direction.NORTH).uvs(10.5F, 10.65F, 14, 8.25F).texture("#chest").end()
				.face(Direction.EAST).uvs(7, 10.65F, 10.5F, 8.25F).texture("#chest").end()
				.face(Direction.SOUTH).uvs(3.5F, 10.65F, 7, 8.25F).texture("#chest").end()
				.face(Direction.WEST).uvs(0, 10.7F, 3.5F, 8.3F).texture("#chest").end()
				.face(Direction.UP).uvs(7, 8.2F, 10.5F, 4.8F).texture("#chest").end()
				.face(Direction.DOWN).uvs(3.5F, 8.3F, 7, 4.7F).texture("#chest").end()
				.end()
				//Lid
				.element()
				.from(1, 10, 1)
				.to(15, 15, 15)
				.face(Direction.NORTH).uvs(10.5F, 4.65F, 14, 3.5F).texture("#chest").end()
				.face(Direction.EAST).uvs(7, 4.7F, 10.5F, 3.5F).texture("#chest").end()
				.face(Direction.SOUTH).uvs(3.5F, 4.7F, 7, 3.5F).texture("#chest").end()
				.face(Direction.WEST).uvs(0, 4.7F, 3.5F, 3.5F).texture("#chest").end()
				.face(Direction.UP).uvs(7, 3.5F, 10.5F, 0).texture("#chest").end()
				.face(Direction.DOWN).uvs(3.5F, 3.5F, 7, 0).texture("#chest").end()
				.end()
				//Top
				.element()
				.from(7, 8, 0)
				.to(9, 12, 1)
				.face(Direction.NORTH).uvs(0.75F, 1.25F, 0.25F, 0.25F).texture("#chest").end()
				.face(Direction.EAST).uvs(1.25F, 1.25F, 1.5F, 0.25F).texture("#chest").end()
				.face(Direction.SOUTH).uvs(0.75F, 1.25F, 1.25F, 0.25F).texture("#chest").end()
				.face(Direction.WEST).uvs(0, 1.25F, 0.25F, 0.25F).texture("#chest").end()
				.face(Direction.UP).uvs(0.25F, 0.25F, 0.75F, 0).texture("#chest").end()
				.face(Direction.DOWN).uvs(0.75F, 0.25F, 1.25F, 0).texture("#chest").end()
				.end();
		particleOnly(PEBlocks.CITRINE_SANCTUM);
	}

	private void particleOnly(BlockRegistryObject<?, ?> block) {
		String name = getName(block);
		simpleBlock(block.getBlock(), models().getBuilder(name).texture("particle", modLoc("block/" + name)));
	}

	private void registerTieredOrientable(String type, BlockRegistryObject<?, ?> base, BlockRegistryObject<?, ?> mk2, BlockRegistryObject<?, ?> mk3) {
		ResourceLocation side = modLoc("block/" + type + "/other");
		BlockModelBuilder model = models().orientableWithBottom(getName(base), side, modLoc("block/" + type + "/front"), side,
				modLoc("block/" + type + "/top_1"));
		horizontalBlock(base.getBlock(), model);
		horizontalBlock(mk2.getBlock(), models().getBuilder(getName(mk2))
				.parent(model)
				.texture("top", modLoc("block/" + type + "/top_2")));
		horizontalBlock(mk3.getBlock(), models().getBuilder(getName(mk3))
				.parent(model)
				.texture("top", modLoc("block/" + type + "/top_3")));
	}

	private void simpleBlocks(BlockRegistryObject<?, ?>... blocks) {
		for (BlockRegistryObject<?, ?> block : blocks) {
			simpleBlock(block.getBlock());
		}
	}

	private void directionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset, Property<?>... toSkip) {
		getVariantBuilder(block).forAllStatesExcept(state -> {
			Direction dir = state.getValue(BlockStateProperties.FACING);
			return ConfiguredModel.builder()
					.modelFile(modelFunc.apply(state))
					.rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
					.rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + angleOffset) % 360)
					.build();
		}, toSkip);
	}

	private static String getName(ItemLike itemProvider) {
		return RegistryUtils.getPath(itemProvider.asItem());
	}
}