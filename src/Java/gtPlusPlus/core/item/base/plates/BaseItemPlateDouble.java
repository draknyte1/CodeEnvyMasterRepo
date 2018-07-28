package gtPlusPlus.core.item.base.plates;

import net.minecraft.item.ItemStack;

import gtPlusPlus.core.item.base.BaseItemComponent;
import gtPlusPlus.core.material.Material;

public class BaseItemPlateDouble extends BaseItemComponent{

	public BaseItemPlateDouble(final Material material) {
		super(material, BaseItemComponent.ComponentTypes.PLATEDOUBLE);
		this.setMaxStackSize(32);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack p_77653_1_) {
		return ("Double "+this.materialName+ " Plate");
	}
}
