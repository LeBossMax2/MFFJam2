package net.mffjam2.common.gem;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.mffjam2.common.capability.GemSocketCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@ToString
@EqualsAndHashCode
public class GemSocket implements INBTSerializable<CompoundNBT>, ICapabilityProvider
{
    @Getter
    private final List<Gem> gems = new ArrayList<>();

    @Getter
    private final List<GemSlot>           gemSlots;
    private final LazyOptional<GemSocket> holder = LazyOptional.of(() -> this);

    public GemSocket()
    {
        this.gemSlots = new ArrayList<>();
    }

    public GemSocket(GemSlot... gemSlots)
    {
        this.gemSlots = Lists.newArrayList(gemSlots);

        this.gems.addAll(IntStream.range(0, gemSlots.length).mapToObj(i -> (Gem) null).collect(toList()));
    }

    public void putGem(int slotIndex, Gem gem)
    {
        this.gems.set(slotIndex, gem);
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("count", gemSlots.size());
        for (int index = 0; index < gemSlots.size(); index++)
        {
            tag.put("slot" + index, gemSlots.get(index).serializeNBT());

            if (gems.get(index) != null)
                tag.put("gem" + index, gems.get(index).serializeNBT());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag)
    {
        this.gemSlots.clear();
        this.gems.clear();

        int count = tag.getInt("count");

        for (int index = 0; index < count; index++)
        {
            if (tag.contains("gem" + index))
            {
                Gem gem = new Gem();
                gem.deserializeNBT(tag.getCompound("gem" + index));
                gems.add(gem);
            }
            else
                gems.add(null);

            GemSlot slot = new GemSlot();
            slot.deserializeNBT(tag.getCompound("slot" + index));
            gemSlots.add(slot);
        }
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
    {
        return GemSocketCapability.GEM_SOCKET_CAPABILITY.orEmpty(capability, holder);
    }
}
