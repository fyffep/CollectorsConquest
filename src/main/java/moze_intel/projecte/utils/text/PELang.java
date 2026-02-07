package moze_intel.projecte.utils.text;

import moze_intel.projecte.PECore;
import net.minecraft.Util;

public enum PELang implements ILangEntry {
	//Vanilla
	UNKNOWN_ITEM("argument.item.id.invalid"),
	UNKNOWN_TAG("arguments.item.tag.unknown"),
	//Misc
	PROJECTE("misc", "mod_name"),
	PACK_DESCRIPTION("misc", "pack_description"),
	SECONDS("misc", "seconds"),
	EVERY_TICK("misc", "seconds.every_tick"),
	UPDATE_AVAILABLE("misc", "update.available"),
	UPDATE_GET_IT("misc", "update.get_it"),
	BLACKLIST("misc", "blacklist"),
	WHITELIST("misc", "whitelist"),

	ADVANCEMENTS_PROJECTE_DESCRIPTION("advancements", "description"),
	ADVANCEMENTS_CITRINE_SANCTUM("advancements", "citrine_sanctum"),
	ADVANCEMENTS_CITRINE_SANCTUM_DESCRIPTION("advancements", "citrine_sanctum.description"),
	ADVANCEMENTS_INFINITE_WOOD("advancements", "infinite_wood"),
	ADVANCEMENTS_INFINITE_WOOD_DESCRIPTION("advancements", "infinite_wood.description"),
	ADVANCEMENTS_INFINITE_IRON("advancements", "infinite_iron"),
	ADVANCEMENTS_INFINITE_IRON_DESCRIPTION("advancements", "infinite_iron.description"),
	;

	private final String key;

	PELang(String type, String path) {
		this(Util.makeDescriptionId(type, PECore.rl(path)));
	}

	PELang(String key) {
		this.key = key;
	}

	@Override
	public String getTranslationKey() {
		return key;
	}
}