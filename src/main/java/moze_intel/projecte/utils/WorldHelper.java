package moze_intel.projecte.utils;

import java.util.List;

import moze_intel.projecte.PECore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Helper class for anything that touches a World.
 */
public final class WorldHelper {

	public static void createLootDrop(List<ItemStack> drops, Level level, BlockPos pos) {
		createLootDrop(drops, level, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void createLootDrop(List<ItemStack> drops, Level level, double x, double y, double z) {
		if (!drops.isEmpty()) {
			ItemHelper.compactItemListNoStacksize(drops);
			for (ItemStack drop : drops) {
				level.addFreshEntity(new ItemEntity(level, x, y, z, drop));
			}
		}
	}


	public static void dropInventory(IItemHandler inv, Level level, BlockPos pos) {
		if (inv == null) {
			return;
		}
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
			}
		}
	}

	/**
	 * Checks if a position is in bounds of the world, and is loaded
	 *
	 * @param world world
	 * @param pos   position
	 *
	 * @return True if the position is loaded or the given world is of a superclass of IWorldReader that does not have a concept of being loaded.
	 *
	 * @implNote From Mekanism
	 */
	public static boolean isBlockLoaded(@Nullable BlockGetter world, @NotNull BlockPos pos) {
		if (world == null) {
			return false;
		} else if (world instanceof LevelReader reader) {
			if (reader instanceof Level level && !level.isInWorldBounds(pos)) {
				return false;
			}
			return reader.hasChunkAt(pos);
		}
		return true;
	}

	/**
	 * Gets a block entity if the location is loaded
	 *
	 * @param level world
	 * @param pos   position
	 *
	 * @return block entity if found, null if either not found or not loaded
	 *
	 * @implNote From Mekanism
	 */
	@Nullable
	public static BlockEntity getBlockEntity(@Nullable BlockGetter level, @NotNull BlockPos pos) {
		if (!isBlockLoaded(level, pos)) {
			//If the world is null or its a world reader and the block is not loaded, return null
			return null;
		}
		return level.getBlockEntity(pos);
	}

	/**
	 * Gets a block entity if the location is loaded
	 *
	 * @param clazz Class type of the block entity we expect to be in the position
	 * @param level world
	 * @param pos   position
	 *
	 * @return block entity if found, null if either not found, not loaded, or of the wrong type
	 *
	 * @implNote From Mekanism
	 */
	@Nullable
	public static <BE extends BlockEntity> BE getBlockEntity(@NotNull Class<BE> clazz, @Nullable BlockGetter level, @NotNull BlockPos pos) {
		return getBlockEntity(clazz, level, pos, false);
	}

	/**
	 * Gets a block entity if the location is loaded
	 *
	 * @param clazz        Class type of the block entity we expect to be in the position
	 * @param level        world
	 * @param pos          position
	 * @param logWrongType Whether or not an error should be logged if a block entity of a different type is found at the position
	 *
	 * @return block entity if found, null if either not found or not loaded, or of the wrong type
	 *
	 * @implNote From Mekanism
	 */
	@Nullable
	public static <BE extends BlockEntity> BE getBlockEntity(@NotNull Class<BE> clazz, @Nullable BlockGetter level, @NotNull BlockPos pos, boolean logWrongType) {
		BlockEntity blockEntity = getBlockEntity(level, pos);
		if (blockEntity == null) {
			return null;
		}
		if (clazz.isInstance(blockEntity)) {
			return clazz.cast(blockEntity);
		} else if (logWrongType) {
			PECore.LOGGER.warn("Unexpected block entity class at {}, expected {}, but found: {}", pos, clazz, blockEntity.getClass());
		}
		return null;
	}
}