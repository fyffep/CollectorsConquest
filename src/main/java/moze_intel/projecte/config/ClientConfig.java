package moze_intel.projecte.config;

import moze_intel.projecte.config.value.CachedBooleanValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

/**
 * For config options that only the client cares about
 */
public class ClientConfig extends BasePEConfig {

	private final ForgeConfigSpec configSpec;

	ClientConfig() {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		//We push as client in case we ever want to add an overarching comment to the client config
		builder.push("client");

		builder.pop();
		configSpec = builder.build();
	}

	@Override
	public String getFileName() {
		return "client";
	}

	@Override
	public ForgeConfigSpec getConfigSpec() {
		return configSpec;
	}

	@Override
	public ModConfig.Type getConfigType() {
		return ModConfig.Type.CLIENT;
	}
}