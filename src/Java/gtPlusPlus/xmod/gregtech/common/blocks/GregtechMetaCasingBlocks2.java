package gtPlusPlus.xmod.gregtech.common.blocks;

import gregtech.api.util.GT_Utility;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.storage.GregtechMetaTileEntity_PowerSubStationController;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import gregtech.api.enums.TAE;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Material_Casings;

import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.CasingTextureHandler2;

import java.util.List;

public class GregtechMetaCasingBlocks2
extends GregtechMetaCasingBlocksAbstract {

	CasingTextureHandler2 TextureHandler = new CasingTextureHandler2();

	public static class GregtechMetaCasingItemBlocks2 extends GregtechMetaCasingItems {

		public GregtechMetaCasingItemBlocks2(Block par1) {
			super(par1);
		}

		@Override
		public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
			int meta = aStack.getItemDamage();
			int tier = GregtechMetaTileEntity_PowerSubStationController.getCellTier(field_150939_a, meta);
			if (tier > 0) {
				long capacity = GregtechMetaTileEntity_PowerSubStationController.getCapacityFromCellTier(tier);
				aList.add("Energy Storage: " + GT_Utility.formatNumbers(capacity));
			}
			super.addInformation(aStack, aPlayer, aList, aF3_H);
		}
	}

	public GregtechMetaCasingBlocks2() {
		super(GregtechMetaCasingItemBlocks2.class, "gtplusplus.blockcasings.2", GT_Material_Casings.INSTANCE);
		for (byte i = 0; i < 16; i = (byte) (i + 1)) {
			TAE.registerTextures(new GT_CopiedBlockTexture(this, 6, i));
		}
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".0.name", "Thermal Processing Casing");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".1.name", "Hastelloy-N Sealant Block");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".2.name", "Hastelloy-X Structural Block");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".3.name", "Incoloy-DS Fluid Containment Block");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".4.name", "Wash Plant Casing");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".5.name", "Industrial Sieve Casing");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".6.name", "Large Sieve Grate");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".7.name", "Vanadium Redox Power Cell (EV)");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".8.name", "Sub-Station External Casing"); 
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".9.name", "Cyclotron Coil");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".10.name", "Cyclotron Outer Casing");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".11.name", "Thermal Containment Casing");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".12.name", "Autocrafter Frame");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".13.name", "Cutting Factory Frame");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".14.name", "Tesla Containment Casing");
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".15.name", "Casing "); //Tree Farmer Textures
		GregtechItemList.Casing_ThermalCentrifuge.set(new ItemStack(this, 1, 0));
		GregtechItemList.Casing_Refinery_External.set(new ItemStack(this, 1, 1));
		GregtechItemList.Casing_Refinery_Structural.set(new ItemStack(this, 1, 2));
		GregtechItemList.Casing_Refinery_Internal.set(new ItemStack(this, 1, 3));
		GregtechItemList.Casing_WashPlant.set(new ItemStack(this, 1, 4));
		GregtechItemList.Casing_Sifter.set(new ItemStack(this, 1, 5));
		GregtechItemList.Casing_SifterGrate.set(new ItemStack(this, 1, 6));
		GregtechItemList.Casing_Vanadium_Redox.set(new ItemStack(this, 1, 7));
		GregtechItemList.Casing_Power_SubStation.set(new ItemStack(this, 1, 8));
		GregtechItemList.Casing_Cyclotron_Coil.set(new ItemStack(this, 1, 9));
		GregtechItemList.Casing_Cyclotron_External.set(new ItemStack(this, 1, 10));
		GregtechItemList.Casing_ThermalContainment.set(new ItemStack(this, 1, 11));
		GregtechItemList.Casing_Autocrafter.set(new ItemStack(this, 1, 12));
		GregtechItemList.Casing_CuttingFactoryFrame.set(new ItemStack(this, 1, 13));
		GregtechItemList.Casing_TeslaTower.set(new ItemStack(this, 1, 14));
		GregtechItemList.Casing_PLACEHOLDER_TreeFarmer.set(new ItemStack(this, 1, 15)); //Tree Farmer Textures
	}

	@Override
	public IIcon getIcon(final int aSide, final int aMeta) {
		return CasingTextureHandler2.getIcon(aSide, aMeta);
	}
}
