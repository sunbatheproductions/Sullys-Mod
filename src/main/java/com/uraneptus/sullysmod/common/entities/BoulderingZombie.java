package com.uraneptus.sullysmod.common.entities;

import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class BoulderingZombie extends Zombie {
    private static final EntityDataAccessor<Boolean> CLIMBING_DATA = SynchedEntityData.defineId(BoulderingZombie.class, EntityDataSerializers.BOOLEAN);
    //private static final RawAnimation CLIMBING_ANIM = RawAnimation.begin().thenLoop("animation.bouldering_zombie.climb");
    protected final WallClimberNavigation climberNavigation;
    protected final GroundPathNavigation groundNavigation;
    public final AnimationState climbAnimationState = new AnimationState();

    public BoulderingZombie(EntityType<? extends BoulderingZombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.climberNavigation = new WallClimberNavigation(this, pLevel);
        this.groundNavigation = new GroundPathNavigation(this, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 23.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }


    public static boolean checkBoulderingZombieSpawnRules(EntityType<? extends BoulderingZombie> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return isInDeepslateLayer(pos, random) && Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean isInDeepslateLayer(BlockPos pos, RandomSource random) {
        int chance = random.nextInt(100);
        double y = pos.getY();

        return (y <= -4 && y >= -15 && chance < 50) || (y <= -16 && y >= -30 && chance < 68) || (y <= -31 && y >= -39 && chance < 80) || (y <= -40 && y >= -63 && chance < 90);
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 0.5F : 1.0625F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CLIMBING_DATA, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (CLIMBING_DATA.equals(pKey)) {
            if (onClimbable()) {
                this.climbAnimationState.start(this.tickCount);
            } else {
                this.climbAnimationState.stop();
            }
        }
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.getTarget() != null) {
                this.navigation = climberNavigation;
                this.setClimbing(this.horizontalCollision);
            } else {
                this.navigation = groundNavigation;
                this.setClimbing(false);
            }
        }
        super.tick();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_FIRE)) {
            pAmount *= 1.5F;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean onClimbable() {
        return this.entityData.get(CLIMBING_DATA);
    }

    public void setClimbing(boolean pClimbing) {
        this.entityData.set(CLIMBING_DATA, pClimbing);
    }

    @Override
    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SMSounds.BOULDERING_ZOMBIE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SMSounds.BOULDERING_ZOMBIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SMSounds.BOULDERING_ZOMBIE_DEATH.get();
    }
}
