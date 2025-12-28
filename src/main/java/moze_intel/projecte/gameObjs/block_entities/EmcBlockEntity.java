package moze_intel.projecte.gameObjs.block_entities;

import java.util.stream.IntStream;

import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.utils.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public abstract class EmcBlockEntity extends BlockEntity {

	private boolean updateComparators;

	public EmcBlockEntity(BlockEntityTypeRegistryObject<? extends EmcBlockEntity> type, BlockPos pos, BlockState state) {
		this(type, pos, state, Constants.BLOCK_ENTITY_MAX_EMC);
	}

	public EmcBlockEntity(BlockEntityTypeRegistryObject<? extends EmcBlockEntity> type, BlockPos pos, BlockState state,
			@Range(from = 1, to = Long.MAX_VALUE) long maxAmount) {
		super(type.get(), pos, state);
	}

	protected void updateComparators() {
		//Only update the comparator state if we need to update comparators
		//Note: We call this at the end of child implementations to try and update any changes immediately instead
		// of them having to be delayed a tick
		if (updateComparators) {
			BlockState state = getBlockState();
			if (!state.isAir()) {
				level.updateNeighbourForOutputSignal(worldPosition, state.getBlock());
			}
			updateComparators = false;
		}
	}

	protected boolean emcAffectsComparators() {
		return false;
	}

	@Override
	public void setChanged() {
		markDirty(true);
	}

	public void markDirty(boolean recheckComparators) {
		//Copy of the base impl of markDirty in BlockEntity, except only updates comparator state when something changed
		// and if our block supports having a comparator signal, instead of always doing it
		if (level != null) {
			if (level.hasChunkAt(worldPosition)) {
				level.getChunkAt(worldPosition).setUnsaved(true);
			}
			if (recheckComparators && !level.isClientSide) {
				updateComparators = true;
			}
		}
	}

	@NotNull
	@Override
	public final CompoundTag getUpdateTag() {
		//TODO: Eventually it would be nice to try and minimize how much data we send in the update tags
		return saveWithoutMetadata();
	}

	@Override
	public final ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	protected class StackHandler extends ItemStackHandler {

		protected StackHandler(int size) {
			super(size);
		}

		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			setChanged();
		}
	}

	protected class CompactableStackHandler extends StackHandler {

		//Start as needing to check for compacting when loaded
		private boolean needsCompacting = true;
		private boolean empty;

		protected CompactableStackHandler(int size) {
			super(size);
		}

		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			needsCompacting = true;
		}

//		public void compact() {
//			if (needsCompacting) {
//				if (level != null && !level.isClientSide) {
//					empty = ItemHelper.compactInventory(this);
//				}
//				needsCompacting = false;
//			}
//		}

		@Override
		protected void onLoad() {
			super.onLoad();
			empty = IntStream.range(0, getSlots()).allMatch(slot -> getStackInSlot(slot).isEmpty());
		}

		/**
		 * @apiNote Only use this on the server
		 */
		public boolean isEmpty() {
			return empty;
		}
	}
}