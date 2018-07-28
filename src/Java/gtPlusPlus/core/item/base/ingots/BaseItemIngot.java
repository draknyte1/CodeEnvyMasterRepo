package gtPlusPlus.core.item.base.ingots;

import net.minecraft.item.ItemStack;

import gregtech.api.util.GT_ModHandler;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.item.base.BaseItemComponent;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.util.minecraft.ItemUtils;

public class BaseItemIngot extends BaseItemComponent{

	protected final String materialName;
	protected final String unlocalName;

	public BaseItemIngot(final Material material) {
		this(material, ComponentTypes.INGOT);
	}

	public BaseItemIngot(final Material material, final ComponentTypes type) {
		super(material, type);
		this.materialName = material.getLocalizedName();
		this.unlocalName = material.getUnlocalizedName();
		this.generateCompressorRecipe();
	}

	private void generateCompressorRecipe(){
		if (this.unlocalName.contains("itemIngot")){
			final ItemStack tempStack = ItemUtils.getSimpleStack(this, 9);
			ItemStack tempOutput = null;
			String temp = this.getUnlocalizedName().replace("item.itemIngot", "block");
			Logger.WARNING("Unlocalized name for OreDict nameGen: "+this.getUnlocalizedName());
			if (this.getUnlocalizedName().contains("item.")){
				temp = this.getUnlocalizedName().replace("item.", "");
				Logger.WARNING("Generating OreDict Name: "+temp);
			}
			temp = temp.replace("itemIngot", "block");
			Logger.WARNING("Generating OreDict Name: "+temp);
			if ((temp != null) && !temp.equals("")){
				tempOutput = ItemUtils.getItemStackOfAmountFromOreDict(temp, 1);
				if (tempOutput != null){
					GT_ModHandler.addCompressionRecipe(tempStack, tempOutput);
				}

			}
		}
		else if (this.unlocalName.contains("itemHotIngot")){
			return;
		}


	}

}
