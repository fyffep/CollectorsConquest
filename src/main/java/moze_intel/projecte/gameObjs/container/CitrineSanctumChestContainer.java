package moze_intel.projecte.gameObjs.container;

import moze_intel.projecte.gameObjs.block_entities.CitrineSanctumEntityChest;
import moze_intel.projecte.gameObjs.container.slots.InventoryContainerSlot;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.gameObjs.registries.PEContainerTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class CitrineSanctumChestContainer extends EmcChestBlockEntityContainer<CitrineSanctumEntityChest> {

	public CitrineSanctumChestContainer(int windowId, Inventory playerInv, CitrineSanctumEntityChest chest) {
		super(PEContainerTypes.CITRINE_SANCTUM_CONTAINER, windowId, playerInv, chest);
		IItemHandler inv = this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(NullPointerException::new);
		//Chest Inventory
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 13; j++) {
				this.addSlot(new InventoryContainerSlot(inv, j + i * 13, 12 + j * 18, 5 + i * 18));
			}
		}
		addPlayerInventory(48, 152);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return stillValid(player, blockEntity, PEBlocks.CITRINE_SANCTUM);
	}
}