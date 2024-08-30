package com.uraneptus.sullysmod.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.client.model.PiranhaModel;
import com.uraneptus.sullysmod.common.entities.Piranha;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PiranhaRenderer<E extends Piranha> extends MobRenderer<E, PiranhaModel<E>> {
    private static final ResourceLocation TEXTURE = SullysMod.modPrefix("textures/entity/piranha/piranha.png");

    public PiranhaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PiranhaModel<>(pContext.bakeLayer(PiranhaModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    protected void setupRotations(E pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        float f = 4.3F * Mth.sin(0.6F * pAgeInTicks);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!pEntityLiving.isInWater()) {
            pMatrixStack.translate(0.1F, 0.1F, -0.1F);
            pMatrixStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }

    }

    @Override
    public ResourceLocation getTextureLocation(E pEntity) {
        return TEXTURE;
    }
}
