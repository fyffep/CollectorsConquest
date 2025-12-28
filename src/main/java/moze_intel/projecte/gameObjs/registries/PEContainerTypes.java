package moze_intel.projecte.gameObjs.registries;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.block_entities.*;
import moze_intel.projecte.gameObjs.container.*;
import moze_intel.projecte.gameObjs.registration.impl.ContainerTypeDeferredRegister;
import moze_intel.projecte.gameObjs.registration.impl.ContainerTypeRegistryObject;

public class PEContainerTypes {

	public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(PECore.MODID);

	public static final ContainerTypeRegistryObject<CitrineSanctumChestContainer> CITRINE_SANCTUM_CONTAINER = CONTAINER_TYPES.register(PEBlocks.CITRINE_SANCTUM, CitrineSanctumEntityChest.class, CitrineSanctumChestContainer::new);
}