package net.mffjam2.setup;

import net.mffjam2.MFFJam2;
import net.mffjam2.common.network.StartInfuserMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class JamNetwork
{
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(MFFJam2.MODID, "main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	
	private static int lastIndex = 0;
	
	public static void registerPackets()
	{
		CHANNEL.registerMessage(lastIndex, StartInfuserMessage.class, StartInfuserMessage::encode, StartInfuserMessage::new, StartInfuserMessage::onMessage);
		lastIndex++;
	}
}
