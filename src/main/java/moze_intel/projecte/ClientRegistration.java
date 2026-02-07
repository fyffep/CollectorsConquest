package moze_intel.projecte;

import mezz.jei.api.runtime.IRecipesGui;
import moze_intel.projecte.gameObjs.gui.*;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.gameObjs.registries.PEBlocks;
import moze_intel.projecte.rendering.ChestRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = PECore.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

	public static final ResourceLocation ACTIVE_OVERRIDE = PECore.rl("active");
	public static final ResourceLocation MODE_OVERRIDE = PECore.rl("mode");

	@SubscribeEvent
	public static void registerContainers(RegisterEvent event) {
		event.register(Registries.MENU, helper -> {
		});
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent evt) {
		if (ModList.get().isLoaded("jei")) {
			//Note: This listener is only registered if JEI is loaded
			MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, (ScreenEvent.Opening event) -> {
				if (event.getCurrentScreen() instanceof PEContainerScreen<?> screen) {
					//If JEI is loaded and our current screen is a mekanism gui,
					// check if the new screen is a JEI recipe screen
					if (event.getNewScreen() instanceof IRecipesGui) {
						//If it is mark on our current screen that we are switching to JEI
						screen.switchingToJEI = true;
					}
				}
			});
		}
	}

//	@SubscribeEvent
//	public static void registerKeybindings(RegisterKeyMappingsEvent event) {
//		ClientKeyHelper.registerKeyBindings(event);
//	}
//
//	@SubscribeEvent
//	public static void registerOverlays(RegisterGuiOverlaysEvent event) {
//		event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "transmutation_result", new TransmutationRenderingOverlay());
//	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		//Block Entity
		event.registerBlockEntityRenderer(PEBlockEntityTypes.CITRINE_SANCTUM.get(), context -> new ChestRenderer(context, PECore.rl("textures/block/citrine_sanctum.png"), () -> PEBlocks.CITRINE_SANCTUM));

		//Entities

	}

//	@SubscribeEvent
//	public static void addLayers(EntityRenderersEvent.AddLayers event) {
//		for (String skinName : event.getSkins()) {
//			PlayerRenderer skin = event.getSkin(skinName);
//			if (skin != null) {
//				skin.addLayer(new LayerYue(skin));
//			}
//		}
//	}
//
//	private static void addPropertyOverrides(ResourceLocation override, ItemPropertyFunction propertyGetter, ItemLike... itemProviders) {
//		for (ItemLike itemProvider : itemProviders) {
//			ItemProperties.register(itemProvider.asItem(), override, propertyGetter);
//		}
//	}
//
//	private static <C extends AbstractContainerMenu, U extends Screen & MenuAccess < C >> void registerScreen(ContainerTypeRegistryObject < C > type, ScreenConstructor < C, U > factory) {
//		MenuScreens.register(type.get(), factory);
//	}
}