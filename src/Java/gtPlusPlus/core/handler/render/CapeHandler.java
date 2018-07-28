package gtPlusPlus.core.handler.render;

import java.util.Collection;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;

import gtPlusPlus.core.lib.CORE;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class CapeHandler extends RenderPlayer {
	private final ResourceLocation[] mCapes = {
			new ResourceLocation(CORE.MODID+":textures/TesterCape.png"),
			new ResourceLocation(CORE.MODID+":textures/Draknyte1.png"),
			new ResourceLocation("gregtech:textures/GregoriusCape.png") };
	private final Collection<String> mCapeList;

	public CapeHandler(final Collection<String> aCapeList) {
		this.mCapeList = aCapeList;
		this.setRenderManager(RenderManager.instance);
	}

	public void receiveRenderSpecialsEvent(final RenderPlayerEvent.Specials.Pre aEvent) {
		final AbstractClientPlayer aPlayer = (AbstractClientPlayer) aEvent.entityPlayer;
		if (GT_Utility.getFullInvisibility(aPlayer)) {
			aEvent.setCanceled(true);
			return;
		}
		final float aPartialTicks = aEvent.partialRenderTick;
		if (aPlayer.isInvisible()) {
			return;
		}
		if (GT_Utility.getPotion(aPlayer,
				Integer.valueOf(Potion.invisibility.id).intValue())) {
			return;
		}
		try {
			ResourceLocation tResource = null;
			if (aPlayer.getDisplayName().equalsIgnoreCase("XW3B")) {
				tResource = this.mCapes[0];
			}
			if (this.mCapeList.contains(aPlayer.getDisplayName().toLowerCase())) {
				tResource = this.mCapes[0];
			}
			if (aPlayer.getDisplayName().equalsIgnoreCase("Draknyte1")) {
				tResource = this.mCapes[1];
			}
			if (aPlayer.getDisplayName().equalsIgnoreCase("GregoriusT")) {
				tResource = this.mCapes[2];
			}
			if ((tResource != null) && (!(aPlayer.getHideCape()))) {
				this.bindTexture(tResource);
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 0.0F, 0.125F);
				final double d0 = (aPlayer.field_71091_bM
						+ ((aPlayer.field_71094_bP - aPlayer.field_71091_bM)
								* aPartialTicks))
						- (aPlayer.prevPosX + ((aPlayer.posX - aPlayer.prevPosX)
								* aPartialTicks));
				final double d1 = (aPlayer.field_71096_bN
						+ ((aPlayer.field_71095_bQ - aPlayer.field_71096_bN)
								* aPartialTicks))
						- (aPlayer.prevPosY + ((aPlayer.posY - aPlayer.prevPosY)
								* aPartialTicks));
				final double d2 = (aPlayer.field_71097_bO
						+ ((aPlayer.field_71085_bR - aPlayer.field_71097_bO)
								* aPartialTicks))
						- (aPlayer.prevPosZ + ((aPlayer.posZ - aPlayer.prevPosZ)
								* aPartialTicks));
				final float f6 = aPlayer.prevRenderYawOffset
						+ ((aPlayer.renderYawOffset - aPlayer.prevRenderYawOffset)
								* aPartialTicks);
				final double d3 = MathHelper.sin((f6 * CORE.PI) / 180.0F);
				final double d4 = -MathHelper.cos((f6 * CORE.PI) / 180.0F);
				float f7 = (float) d1 * 10.0F;
				float f8 = (float) ((d0 * d3) + (d2 * d4)) * 100.0F;
				final float f9 = (float) ((d0 * d4) - (d2 * d3)) * 100.0F;
				if (f7 < -6.0F) {
					f7 = -6.0F;
				}
				if (f7 > 32.0F) {
					f7 = 32.0F;
				}
				if (f8 < 0.0F) {
					f8 = 0.0F;
				}
				final float f10 = aPlayer.prevCameraYaw
						+ ((aPlayer.cameraYaw - aPlayer.prevCameraYaw)
								* aPartialTicks);
				f7 += MathHelper
						.sin((aPlayer.prevDistanceWalkedModified + ((aPlayer.distanceWalkedModified - aPlayer.prevDistanceWalkedModified)
								* aPartialTicks)) * 6.0F)
						* 32.0F * f10;
				if (aPlayer.isSneaking()) {
					f7 += 25.0F;
				}
				GL11.glRotatef(6.0F + (f8 / 2.0F) + f7, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(f9 / 2.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-f9 / 2.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				((ModelBiped) this.mainModel).renderCloak(0.0625F);
				GL11.glPopMatrix();
			}
		} catch (final Throwable e) {
			if (GT_Values.D1) {
				e.printStackTrace(GT_Log.err);
			}
		}
	}

}
