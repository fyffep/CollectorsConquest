package moze_intel.projecte.gameObjs.registries;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.registration.impl.CreativeTabDeferredRegister;
import moze_intel.projecte.gameObjs.registration.impl.CreativeTabRegistryObject;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class PECreativeTabs {

	public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(PECore.MODID, PECreativeTabs::addToExistingTabs);

	public static final CreativeTabRegistryObject PROJECTE = CREATIVE_TABS.registerMain(PELang.PROJECTE, PEBlocks.CITRINE_SANCTUM, builder ->
			builder.displayItems((displayParameters, output) -> {
				output.accept(PEBlocks.CITRINE_SANCTUM);
				output.accept(PEItems.INFINITE_WOOD_TROPHY);
				output.accept(PEItems.INFINITE_IRON_TROPHY);
			})
	);

	private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
		ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
		if (tabKey == CreativeModeTabs.BUILDING_BLOCKS) {
//			addToExistingTab(event,
//
//			);
		} else if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			addToExistingTab(event,
					PEBlocks.CITRINE_SANCTUM
			);
		} else if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
			addToExistingTab(event,
					//Comparator supporting blocks
					PEBlocks.CITRINE_SANCTUM
			);
		} else if (tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES) {
//			addToExistingTab(event,
//
//			);
		} else if (tabKey == CreativeModeTabs.COMBAT) {
//			addToExistingTab(event,
//
//			);
		} else if (tabKey == CreativeModeTabs.INGREDIENTS) {
			addToExistingTab(event,
				PEItems.INFINITE_IRON_TROPHY,
				PEItems.INFINITE_WOOD_TROPHY
			);
		}
	}

	private static void addToExistingTab(BuildCreativeModeTabContentsEvent event, ItemLike... items) {
		for (ItemLike item : items) {
			event.accept(item);
		}
	}
}