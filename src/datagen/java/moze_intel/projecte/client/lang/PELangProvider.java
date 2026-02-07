package moze_intel.projecte.client.lang;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.gameObjs.registries.PEItems;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class PELangProvider extends BaseLanguageProvider {

	public PELangProvider(PackOutput output) {
		super(output, PECore.MODID);
	}

	@Override
	protected void addTranslations() {
		addAdvancements();
		addBlocks();
		addItems();
		//Misc stuff
		add(PELang.PROJECTE, PECore.MODNAME);
		add(PELang.PACK_DESCRIPTION, "Resources used for " + PECore.MODNAME);
		add(PELang.SECONDS, "%s seconds");
		add(PELang.EVERY_TICK, "%s seconds (every tick)");
		add(PELang.UPDATE_AVAILABLE, "New " + PECore.MODNAME + " update available! Version: %s");
		add(PELang.UPDATE_GET_IT, "Get it here!");
		add(PELang.BLACKLIST, "Blacklist");
		add(PELang.WHITELIST, "Whitelist");
		//JEI
//		add(PELang.JEI_COLLECTOR, "Collector Fuel Upgrades");
//		add(PELang.WORLD_TRANSMUTE_DESCRIPTION, "Click in world, shift click for second output");
	}

	private void addJadeConfigTooltip(ResourceLocation location, String value) {
		add("config.jade.plugin_" + location.getNamespace() + "." + location.getPath(), value);
	}

	private void addAdvancements() {
		add(PELang.ADVANCEMENTS_PROJECTE_DESCRIPTION, "Collector's Conquest");
		add(PELang.ADVANCEMENTS_CITRINE_SANCTUM, "The scavenger hunt begins!");
		add(PELang.ADVANCEMENTS_CITRINE_SANCTUM_DESCRIPTION, "The greatest time-saver of all time.");
		add(PELang.ADVANCEMENTS_INFINITE_WOOD, "I speak for the trees.");
		add(PELang.ADVANCEMENTS_INFINITE_WOOD_DESCRIPTION, "Do trees hug back?");
		add(PELang.ADVANCEMENTS_INFINITE_IRON, "Rich in iron!");
		add(PELang.ADVANCEMENTS_INFINITE_IRON_DESCRIPTION, "Is making golems cheating? Yes.");
	}

	private void addBlocks() {
		add(PEBlocks.CITRINE_SANCTUM, "Citrine Sanctum");
	}

	private void addItems() {
		add(PEItems.INFINITE_WOOD_TROPHY, "Infinite Wood Talisman");
		add(PEItems.INFINITE_IRON_TROPHY, "Infinite Iron Talisman");
	}
}