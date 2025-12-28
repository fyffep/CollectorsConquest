package moze_intel.projecte.gameObjs.registries;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.blocks.CitrineSanctum;
import moze_intel.projecte.gameObjs.registration.impl.BlockDeferredRegister;
import moze_intel.projecte.gameObjs.registration.impl.BlockRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class PEBlocks {

	public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(PECore.MODID);

	public static final BlockRegistryObject<CitrineSanctum, BlockItem> CITRINE_SANCTUM = BLOCKS.register("citrine_sanctum", () -> new CitrineSanctum(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5, 6000)));

}