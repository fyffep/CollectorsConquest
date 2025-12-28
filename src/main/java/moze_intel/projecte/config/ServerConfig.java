package moze_intel.projecte.config;

import moze_intel.projecte.config.value.CachedBooleanValue;
import moze_intel.projecte.config.value.CachedDoubleValue;
import moze_intel.projecte.config.value.CachedFloatValue;
import moze_intel.projecte.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

/**
 * For config options that the server has absolute say over
 */
public final class ServerConfig extends BasePEConfig {

	private final ForgeConfigSpec configSpec;

	public final Difficulty difficulty;
	public final Items items;
	public final Effects effects;
	public final Misc misc;
	public final Cooldown cooldown;

	ServerConfig() {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.comment("All of the config options in this file are server side and will be synced from server to client. ProjectE uses one \"server\" config file for " +
						"all worlds, for convenience in going from one world to another, but makes it be a \"server\" config file so that forge will automatically sync it when " +
						"we connect to a multiplayer server.")
				.push("server");
		difficulty = new Difficulty(this, builder);
		items = new Items(this, builder);
		effects = new Effects(this, builder);
		misc = new Misc(this, builder);
		cooldown = new Cooldown(this, builder);
		builder.pop();
		configSpec = builder.build();
	}

	@Override
	public String getFileName() {
		return "server";
	}

	@Override
	public ForgeConfigSpec getConfigSpec() {
		return configSpec;
	}

	@Override
	public ModConfig.Type getConfigType() {
		return ModConfig.Type.SERVER;
	}

	public static class Difficulty {

		private Difficulty(IPEConfig config, ForgeConfigSpec.Builder builder) {
			builder.push("difficulty");

			builder.pop();
		}
	}

	public static class Items {

		private Items(IPEConfig config, ForgeConfigSpec.Builder builder) {
			builder.push("items");

			builder.pop();
		}
	}

	public static class Effects {

		private Effects(IPEConfig config, ForgeConfigSpec.Builder builder) {
			builder.push("effects");

			builder.pop();
		}
	}

	public static class Misc {

		private Misc(IPEConfig config, ForgeConfigSpec.Builder builder) {
			builder.push("misc");

			builder.pop();
		}
	}

	public static class Cooldown {

		public final Player player;

		private Cooldown(IPEConfig config, ForgeConfigSpec.Builder builder) {
			builder.push("cooldown");
			builder.comment("Cooldown (in ticks) for various features in ProjectE. A cooldown of -1 will disable the functionality.",
					"A cooldown of 0 will allow the actions to happen every tick. Use caution as a very low value on features that run automatically could cause TPS issues.")
					.push("cooldown");
			player = new Player(config, builder);
			builder.pop();
		}

		public static class Player {

			public final CachedIntValue projectile;
			public final CachedIntValue heal;
			public final CachedIntValue feed;

			private Player(IPEConfig config, ForgeConfigSpec.Builder builder) {
				builder.comment("Cooldown for various items in regards to a player.")
						.push("player");
				projectile = CachedIntValue.wrap(config, builder
						.comment("A cooldown for firing projectiles")
						.defineInRange("projectile", 0, -1, Integer.MAX_VALUE));
				heal = CachedIntValue.wrap(config, builder
						.comment("Delay between heal attempts while in a player's inventory.")
						.defineInRange("heal", 20, -1, Integer.MAX_VALUE));
				feed = CachedIntValue.wrap(config, builder
						.comment("Delay between feed attempts while in a player's inventory.")
						.defineInRange("feed", 20, -1, Integer.MAX_VALUE));
				builder.pop();
			}
		}


	}
}