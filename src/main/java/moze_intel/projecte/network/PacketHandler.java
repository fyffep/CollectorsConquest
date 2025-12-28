package moze_intel.projecte.network;

import java.util.Optional;
import java.util.function.Function;
import moze_intel.projecte.PECore;
import moze_intel.projecte.network.packets.IPEPacket;
import moze_intel.projecte.network.packets.to_client.UpdateWindowIntPKT;
import moze_intel.projecte.network.packets.to_client.UpdateWindowLongPKT;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {

	private static final String PROTOCOL_VERSION = Integer.toString(4);
	private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
			.named(PECore.rl("main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	private static int index;

	public static void register() {
		registerServerToClient(UpdateWindowIntPKT.class, UpdateWindowIntPKT::decode);
		registerServerToClient(UpdateWindowLongPKT.class, UpdateWindowLongPKT::decode);
	}

//	private static <MSG extends IPEPacket> void registerClientToServer(Class<MSG> type, Function<FriendlyByteBuf, MSG> decoder) {
//		registerMessage(type, decoder, NetworkDirection.PLAY_TO_SERVER);
//	}

	private static <MSG extends IPEPacket> void registerServerToClient(Class<MSG> type, Function<FriendlyByteBuf, MSG> decoder) {
		registerMessage(type, decoder, NetworkDirection.PLAY_TO_CLIENT);
	}

	private static <MSG extends IPEPacket> void registerMessage(Class<MSG> type, Function<FriendlyByteBuf, MSG> decoder, NetworkDirection networkDirection) {
		HANDLER.registerMessage(index++, type, IPEPacket::encode, decoder, IPEPacket::handle, Optional.of(networkDirection));
	}

//	private static boolean isLocal(ServerPlayer player) {
//		return player.server.isSingleplayerOwner(player.getGameProfile());
//	}

//	public static <MSG extends IPEPacket> void sendNonLocal(MSG msg, ServerPlayer player) {
//		if (!isLocal(player)) {
//			sendTo(msg, player);
//		}
//	}

//	private static void sendFragmentedEmcPacket(ServerPlayer player, SyncEmcPKT pkt, SyncFuelMapperPKT fuelPkt) {
//		if (!isLocal(player)) {
//			sendTo(pkt, player);
//			sendTo(fuelPkt, player);
//		}
//	}

//	public static void sendFragmentedEmcPacket(ServerPlayer player) {
//		sendFragmentedEmcPacket(player, new SyncEmcPKT(serializeEmcData()), FuelMapper.getSyncPacket());
//	}
//
//	public static void sendFragmentedEmcPacketToAll() {
//		if (ServerLifecycleHooks.getCurrentServer() != null) {
//			SyncEmcPKT pkt = new SyncEmcPKT(serializeEmcData());
//			SyncFuelMapperPKT fuelPkt = FuelMapper.getSyncPacket();
//			for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
//				sendFragmentedEmcPacket(player, pkt, fuelPkt);
//			}
//		}
//	}
//
//	private static EmcPKTInfo[] serializeEmcData() {
//		EmcPKTInfo[] data = EMCMappingHandler.createPacketData();
//		//Simulate encoding the EMC packet to get an accurate size
//		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
//		int index = buf.writerIndex();
//		new SyncEmcPKT(data).encode(buf);
//		PECore.debugLog("EMC data size: {} bytes", buf.writerIndex() - index);
//		buf.release();
//		return data;
//	}

	/**
	 * Sends a packet to the server.<br> Must be called Client side.
	 */
	public static <MSG extends IPEPacket> void sendToServer(MSG msg) {
		HANDLER.sendToServer(msg);
	}

	/**
	 * Send a packet to a specific player.<br> Must be called Server side.
	 */
	public static <MSG extends IPEPacket> void sendTo(MSG msg, ServerPlayer player) {
		if (!(player instanceof FakePlayer)) {
			HANDLER.send(PacketDistributor.PLAYER.with(() -> player), msg);
		}
	}
}