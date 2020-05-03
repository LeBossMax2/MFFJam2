package net.mffjam2.common.system;

import lombok.experimental.UtilityClass;
import net.mffjam2.common.gem.Gem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class FireSystem
{
    public static void createFireBullet(Gem gem, PlayerEntity player)
    {
        switch (gem.getSummonType())
        {
            case POINT_SELF:
                break;
            case POINT_TARGET:
                Vec3d motion = player.getLookVec();
                Vec3d startOffset = player.getEyePosition(0).add(player.getLookVec().mul(3, 3, 3));
                FireballEntity fireball = new FireballEntity(player.world, startOffset.x, startOffset.y, startOffset.z, motion.x, motion.y, motion.z);

                player.world.addEntity(fireball);
                break;
            case SKY_SELF:
                break;
            case SKY_TARGET:
                break;
            case AREA_SELF:
                break;
            case AREA_TARGET:
                break;
        }
    }

    private List<LivingEntity> sphereCast(PlayerEntity player, Vec3d origin, Vec3d direction, double radius, double maxLength)
    {
        AxisAlignedBB bounds = new AxisAlignedBB(origin, new Vec3d(origin.x + direction.x * maxLength, origin.y + direction.y * maxLength, origin.z + direction.z * maxLength));

        List<Entity> candidates = player.world.getEntitiesInAABBexcluding(player, bounds, entity -> entity instanceof LivingEntity);

        return candidates.stream().filter(candidate ->
        {
            double dist = direction.subtract(candidate.getPositionVec().subtract(origin).normalize()).length();
            return dist < radius;
        }).map(LivingEntity.class::cast).sorted(Comparator.comparingDouble(candidate -> candidate.getPositionVec().subtract(origin).length())).collect(toList());
    }
}
