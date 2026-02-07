package moze_intel.projecte.api.capabilities;

import moze_intel.projecte.api.capabilities.item.IAlchChestItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class PECapabilities {

	private PECapabilities() {
	}

	/**
	 * The capability object for IAlchChestItem
	 */
	public static final Capability<IAlchChestItem> ALCH_CHEST_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
}