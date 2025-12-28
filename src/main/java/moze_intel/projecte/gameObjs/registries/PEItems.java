package moze_intel.projecte.gameObjs.registries;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.registration.impl.ItemDeferredRegister;
import moze_intel.projecte.gameObjs.registration.impl.ItemRegistryObject;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.world.item.Item;

public class PEItems {

	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(PECore.MODID);

	public static final ItemRegistryObject<Item> INFINITE_WOOD_TROPHY = ITEMS.register("infinite_wood_trophy");
	public static final ItemRegistryObject<Item> INFINITE_IRON_TROPHY = ITEMS.register("infinite_iron_trophy");

}