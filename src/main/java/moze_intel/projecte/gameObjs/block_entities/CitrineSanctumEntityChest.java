package moze_intel.projecte.gameObjs.block_entities;

import moze_intel.projecte.api.capabilities.PECapabilities;
import moze_intel.projecte.capability.managing.BasicCapabilityResolver;
import moze_intel.projecte.gameObjs.container.CitrineSanctumChestContainer;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.utils.text.TextComponentUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class CitrineSanctumEntityChest extends EmcChestBlockEntity {

	private final StackHandler inventory = new StackHandler(39) {
		@Override
		public void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			if (level != null && !level.isClientSide) {
				inventoryChanged = true;
			}
		}
	};
	private boolean inventoryChanged;

	public CitrineSanctumEntityChest(BlockPos pos, BlockState state) {
		super(PEBlockEntityTypes.CITRINE_SANCTUM, pos, state, 1_000);
		itemHandlerResolver = BasicCapabilityResolver.getBasicItemHandlerResolver(inventory);
	}

	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		inventory.deserializeNBT(nbt);
//		enforceGlowstone(); //TODO
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.merge(inventory.serializeNBT());
	}

	public static void tickClient(Level level, BlockPos pos, BlockState state, CitrineSanctumEntityChest alchChest) {
		for (int i = 0; i < alchChest.inventory.getSlots(); i++) {
			ItemStack stack = alchChest.inventory.getStackInSlot(i);
			if (!stack.isEmpty()) {
				stack.getCapability(PECapabilities.ALCH_CHEST_ITEM_CAPABILITY).ifPresent(alchChestItem -> alchChestItem.updateInAlchChest(level, pos, stack));
			}
		}
		EmcChestBlockEntity.lidAnimateTick(level, pos, state, alchChest);
	}

	public static void tickServer(Level level, BlockPos pos, BlockState state, CitrineSanctumEntityChest citrineChest) {
		for (int i = 0; i < citrineChest.inventory.getSlots(); i++) {
			ItemStack stack = citrineChest.inventory.getStackInSlot(i);
			if (!stack.isEmpty()) {
				int slotId = i;
				stack.getCapability(PECapabilities.ALCH_CHEST_ITEM_CAPABILITY).ifPresent(alchChestItem -> {
					if (alchChestItem.updateInAlchChest(level, pos, stack)) {
						citrineChest.inventory.onContentsChanged(slotId);
					}
				});
			}
		}

		if (citrineChest.inventoryChanged) {
			citrineChest.inventoryChanged = false;
//			citrineChest.replenishInfiniteItemGiver(level, pos); //TODO write separate method to restock after items change based on cached achievements
			level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
		}

		citrineChest.updateComparators();
	}


//	private void enforceGlowstone() {
//		ItemStack stack = inventory.getStackInSlot(3);
//
//		if (stack.isEmpty() || stack.getItem() != Items.GLOWSTONE || stack.getCount() != 4) {
//			ItemStack glowstone = new ItemStack(Items.GLOWSTONE, 4);
//			inventory.setStackInSlot(3, glowstone);
//		}
//	}

	private void stockItem(ServerPlayer player, int slot, Item freeItem, Advancement requirement)
	{
		Item chosenItem;
		if (player.getAdvancements()
				.getOrStartProgress(requirement)
				.isDone()) {
			chosenItem = freeItem;
		}
		else {
			chosenItem = Items.AIR;
		}

		ItemStack stack = inventory.getStackInSlot(3);
		if (stack.isEmpty() || stack.getItem() != chosenItem || stack.getCount() != 64) {
			inventory.setStackInSlot(slot, new ItemStack(chosenItem, 64));
			inventoryChanged = true;
		}
	}

	public void replenishInfiniteItemGiver(Level level, ServerPlayer player) {
		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		ServerAdvancementManager advancements = serverLevel.getServer()
				.getAdvancements();
		int i = 0;

		stockItem(player, i++, Items.OAK_LOG, advancements.getAdvancement(new ResourceLocation("projecte", "citrine_sanctum/infinite_wood")));
		stockItem(player, i++, Items.IRON_INGOT, advancements.getAdvancement(new ResourceLocation("projecte", "citrine_sanctum/infinite_iron")));
	}


	@NotNull
	@Override
	public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player playerIn) {
		return new CitrineSanctumChestContainer(windowId, playerInventory, this);
	}

	@NotNull
	@Override
	public Component getDisplayName() {
		return TextComponentUtil.build(PEBlocks.CITRINE_SANCTUM);
	}
}