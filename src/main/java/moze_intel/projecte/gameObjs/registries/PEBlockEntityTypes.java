package moze_intel.projecte.gameObjs.registries;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.block_entities.*;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeDeferredRegister;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;

public class PEBlockEntityTypes {

	public static final BlockEntityTypeDeferredRegister BLOCK_ENTITY_TYPES = new BlockEntityTypeDeferredRegister(PECore.MODID);

	public static final BlockEntityTypeRegistryObject<CitrineSanctumEntityChest> CITRINE_SANCTUM = BLOCK_ENTITY_TYPES.builder(PEBlocks.CITRINE_SANCTUM, CitrineSanctumEntityChest::new).clientTicker(CitrineSanctumEntityChest::tickClient).serverTicker(CitrineSanctumEntityChest::tickServer).build();
}