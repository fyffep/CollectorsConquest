package moze_intel.projecte.common;

import java.util.function.Consumer;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.PETags;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.gameObjs.registries.PEItems;
import moze_intel.projecte.utils.text.ILangEntry;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;
import org.jetbrains.annotations.NotNull;

public class PEAdvancementsGenerator implements AdvancementGenerator {

	@Override
	public void generate(@NotNull HolderLookup.Provider registries, @NotNull Consumer<Advancement> advancementConsumer, @NotNull ExistingFileHelper fileHelper) {
//		Advancement root = Advancement.Builder.advancement()
//				.display(PEItems.INFINITE_IRON_TROPHY,
//						PELang.PROJECTE.translate(),
//						PELang.ADVANCEMENTS_PROJECTE_DESCRIPTION.translate(),
//						new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
//						FrameType.TASK,
//						false,
//						false,
//						false)
		Advancement root = Advancement.Builder.advancement()
				.display(PEItems.INFINITE_IRON_TROPHY,
						PELang.PROJECTE.translate(),
						PELang.ADVANCEMENTS_PROJECTE_DESCRIPTION.translate(),
						new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
						FrameType.TASK,
						false,
						false,
						false)
				.addCriterion("philstone_recipe", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLOWSTONE_DUST, Items.DIAMOND, Items.REDSTONE))
				.save(advancementConsumer, PECore.rl("root"), fileHelper);

		addCitrineSanctum(advancementConsumer, fileHelper, root);
	}

	private static Advancement.Builder childDisplay(Advancement parent, ItemLike icon, ILangEntry title, ILangEntry description) {
		return Advancement.Builder.advancement()
				.parent(parent)
				.display(icon, title.translate(), description.translate(), null, FrameType.TASK, true, true, false);
	}

	private void addCitrineSanctum(Consumer<Advancement> advancementConsumer, ExistingFileHelper fileHelper, Advancement root) {
		Advancement citrine_sanctum = childDisplay(root, PEBlocks.CITRINE_SANCTUM, PELang.ADVANCEMENTS_CITRINE_SANCTUM,
				PELang.ADVANCEMENTS_CITRINE_SANCTUM_DESCRIPTION)
				.addCriterion("citrine_sanctum", InventoryChangeTrigger.TriggerInstance.hasItems(PEBlocks.CITRINE_SANCTUM))
				.save(advancementConsumer, PECore.rl("citrine_sanctum"), fileHelper);
		//Branch 1
		childDisplay(citrine_sanctum, Blocks.OAK_LOG, PELang.ADVANCEMENTS_INFINITE_WOOD,
				PELang.ADVANCEMENTS_INFINITE_WOOD_DESCRIPTION)
				.addCriterion("infinite_wood", InventoryChangeTrigger.TriggerInstance.hasItems(PEItems.INFINITE_WOOD_TROPHY))
				.save(advancementConsumer, PECore.rl("infinite_wood"), fileHelper);
		childDisplay(citrine_sanctum, Blocks.IRON_BLOCK, PELang.ADVANCEMENTS_INFINITE_IRON,
				PELang.ADVANCEMENTS_INFINITE_IRON_DESCRIPTION)
				.addCriterion("infinite_iron", InventoryChangeTrigger.TriggerInstance.hasItems(PEItems.INFINITE_IRON_TROPHY))
				.save(advancementConsumer, PECore.rl("infinite_iron"), fileHelper);
	}
}