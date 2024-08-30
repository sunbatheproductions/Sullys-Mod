package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.common.particletypes.ParticleWithDirectionType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SullysMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SMParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SullysMod.MOD_ID);

    public static final RegistryObject<ParticleWithDirectionType> RICOCHET = PARTICLES.register("ricochet", () -> new ParticleWithDirectionType(false));

    public static final RegistryObject<SimpleParticleType> BLOT_EYES = PARTICLES.register("blot_eyes", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> AMBER_DRIP = PARTICLES.register("amber_drip", () -> new SimpleParticleType(false));
}
