package com.seabreyh.mana.client.gui;

import com.seabreyh.mana.ManaMod;
import com.seabreyh.mana.client.ClientManaStatData;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.IIngameOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation FULL_MANA = new ResourceLocation(ManaMod.MOD_ID,
            "textures/gui/mana_star_full.png");
    private static final ResourceLocation EMPTY_MANA = new ResourceLocation(ManaMod.MOD_ID,
            "textures/gui/mana_star_empty.png");

    public static final IIngameOverlay MANA_STAT_HUD = ((gui, poseStack, partialTick, width, height) -> {

        Minecraft instance = Minecraft.getInstance();
        boolean isMounted = instance.player.getVehicle() instanceof LivingEntity;
        if (isMounted || instance.options.hideGui || !gui.shouldDrawSurvivalElements()) {
            return;
        }
        int l5 = instance.player.getMaxAirSupply();
        int i6 = Math.min(instance.player.getAirSupply(), l5);
        boolean mustShiftUp = instance.player.isEyeInFluid(FluidTags.WATER) || i6 < l5;
        int yOffset = mustShiftUp ? 10 : 0;
        int x = width / 2 + 96;
        int y = height - 3 - yOffset;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_MANA);
        int max = ClientManaStatData.getPlayerManaCapacity() / 2 + ClientManaStatData.getPlayerManaCapacity() % 2;
        for (int i = 0; i < max; i++) {
            GuiComponent.blit(poseStack, x - 94 + (i * 8), y - 54, 0, 0, 22, 22,
                    22, 22);
        }

        for (int i = 0; i < max; i++) {
            int j = i * 2;

            if (ClientManaStatData.getPlayerManaStat() % 2 != 0 && (j == ClientManaStatData.getPlayerManaStat() - 1)) {
                RenderSystem.setShaderTexture(0, new ResourceLocation(ManaMod.MOD_ID,
                        "textures/gui/mana_star_half.png"));
                GuiComponent.blit(poseStack, x - 94 + (i * 8), y - 54, 0, 0, 21, 21,
                        21, 21);
            } else if (ClientManaStatData.getPlayerManaStat() > j) {
                // if ((i == ClientManaStatData.getPlayerManaStat() - 1) && (i % 2 == 0)) {
                // RenderSystem.setShaderTexture(0, new ResourceLocation(ManaMod.MOD_ID,
                // "textures/gui/mana_star_half.png"));
                // GuiComponent.blit(poseStack, x - 94 + (i * 8), y - 54, 0, 0, 21, 21,
                // 21, 21);
                // } else {
                RenderSystem.setShaderTexture(0, FULL_MANA);
                GuiComponent.blit(poseStack, x - 94 + (i * 8), y - 54, 0, 0, 21, 21,
                        21, 21);
                // }
            } else {
                break;
            }
        }

    });

}
