package net.mffjam2.common.network;

import java.util.function.Supplier;

import net.mffjam2.common.tile.GemInfuserTile;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.voxelindustry.steamlayer.container.BuiltContainer;

public class StartInfuserMessage
{
	private int windowId;

	public StartInfuserMessage(PacketBuffer buf)
	{
		this(buf.readInt());
	}
	
	public StartInfuserMessage(int windowId)
	{
		this.windowId = windowId;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeInt(this.windowId);
	}
	
	public static void onMessage(StartInfuserMessage message, Supplier<NetworkEvent.Context> ctxSup)
	{
		NetworkEvent.Context ctx = ctxSup.get();
		if (ctx.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			ctx.enqueueWork(() ->
			{
				ServerPlayerEntity sender = ctx.getSender();
				if (sender != null && sender.openContainer.windowId == message.windowId)
				{
					if (sender.openContainer instanceof BuiltContainer)
					{
						BuiltContainer container = ((BuiltContainer)sender.openContainer);
						if (container.getMainTile() instanceof GemInfuserTile)
						{
							GemInfuserTile infuser = (GemInfuserTile)container.getMainTile();
							infuser.startInfusion();
						}
					}
				}
			});
			ctx.setPacketHandled(true);
		}
	}
}
