package net.mffjam2.common.packet;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import net.mffjam2.common.capability.GemSocketCapability;
import net.mffjam2.common.gem.GemSlot;
import net.mffjam2.common.gem.UserActionType;
import net.mffjam2.common.system.MagicSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.voxelindustry.steamlayer.network.packet.Message;

import java.util.List;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor
public class SpellTriggerPacket extends Message
{
    private UserActionType type;
    private int   entityID;
    public SpellTriggerPacket(UserActionType type)
    {
        this.type = type;
    }

    @Override
    public void read(ByteBuf buffer)
    {
        type = UserActionType.values()[buffer.readByte()];
    }

    @Override
    public void write(ByteBuf buffer)
    {
        buffer.writeByte(type.ordinal());
    }

    @Override
    public void handle(PlayerEntity playerEntity)
    {
        playerEntity.getHeldItemMainhand().getCapability(GemSocketCapability.GEM_SOCKET_CAPABILITY).ifPresent(socket ->
        {
            List<GemSlot> gemSlots = socket.getGemSlots();

            gemSlots.stream().filter(slot -> slot.getUserActionType() == type)
                    .forEach(slot -> MagicSystem.handleEffect(slot, socket.getGems().get(gemSlots.indexOf(slot)), playerEntity));
        });
    }
}
