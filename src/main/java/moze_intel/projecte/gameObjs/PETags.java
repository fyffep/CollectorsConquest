package moze_intel.projecte.gameObjs;

import moze_intel.projecte.PECore;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PETags {

	private PETags() {
	}

	/**
	 * Call to force make sure this is all initialized
	 */
	public static void init() {
		Items.init();
		Blocks.init();
		Entities.init();
		BlockEntities.init();
	}

	public static class Items {

		private static void init() {
		}

		private Items() {
		}

//		public static final TagKey<Item> ALCHEMICAL_BAGS = tag("alchemical_bags");
	}

	public static class Blocks {

		private static void init() {
		}

		private Blocks() {
		}
	}

	public static class Entities {

		private static void init() {
		}

		private Entities() {
		}

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, PECore.rl(name));
		}
	}

	public static class BlockEntities {

		private static void init() {
		}

		private BlockEntities() {
		}

		private static TagKey<BlockEntityType<?>> tag(String name) {
			return TagKey.create(Registries.BLOCK_ENTITY_TYPE, PECore.rl(name));
		}
	}
}