package moze_intel.projecte.common.tag;

import java.util.concurrent.CompletableFuture;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PEBlockTagsProvider extends BlockTagsProvider {

	public PEBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, PECore.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider) {
		//Vanilla/Forge Tags
		tag(Tags.Blocks.CHESTS).add(
				PEBlocks.CITRINE_SANCTUM.getBlock()
		);
		tag(BlockTags.GUARDED_BY_PIGLINS).add(
				PEBlocks.CITRINE_SANCTUM.getBlock()
		);

		addImmuneBlocks(BlockTags.DRAGON_IMMUNE);
		addImmuneBlocks(BlockTags.WITHER_IMMUNE);

		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				PEBlocks.CITRINE_SANCTUM.getBlock()
		);
		tag(BlockTags.MINEABLE_WITH_AXE).add(
				PEBlocks.CITRINE_SANCTUM.getBlock()
		);
	}

	private void addImmuneBlocks(TagKey<Block> tag) {
		tag(tag).add(
				PEBlocks.CITRINE_SANCTUM.getBlock()
		);
	}
}