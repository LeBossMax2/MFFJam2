package net.mffjam2.common.system;

import lombok.experimental.UtilityClass;
import net.mffjam2.common.gem.Gem;
import net.mffjam2.common.gem.GemSlot;
import net.minecraft.entity.player.PlayerEntity;

import static net.mffjam2.common.system.FireSystem.createFireBullet;

@UtilityClass
public class MagicSystem
{
    public void handleEffect(GemSlot slot, Gem gem, PlayerEntity player)
    {
        switch (slot.getProjectileType())
        {
            case MELEE:
                break;
            case ARROW:
                break;
            case BULLET:
                handleBulletEffect(gem, player);
                break;
            case RAY:
                break;
        }
    }

    public void handleBulletEffect(Gem gem, PlayerEntity player)
    {
        switch (gem.getEffectType())
        {
            case FIRE:
                createFireBullet(gem, player);
                break;
            case ICE:
                break;
            case EXPLOSION:
                break;
            case ELECTRIC:
                break;
            case VAMPIRISM:
                break;
            case LIGHT:
                break;
        }
    }
}
