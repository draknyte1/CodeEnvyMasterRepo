package gtPlusPlus.core.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import gtPlusPlus.core.item.base.BaseItemBackpack;

public class SlotItemBackpackInv extends Slot {
	public SlotItemBackpackInv(final IInventory inv, final int index, final int xPos, final int yPos) {
		super(inv, index, xPos, yPos);
	}

	// This is the only method we need to override so that
	// we can't place our inventory-storing Item within
	// its own inventory (thus making it permanently inaccessible)
	// as well as preventing abuse of storing backpacks within backpacks
	/**
	 * Check if the stack is a valid item for this slot.
	 */
	@Override
	public boolean isItemValid(final ItemStack itemstack) {
		// Everything returns true except an instance of our Item
		return !(itemstack.getItem() instanceof BaseItemBackpack);
	}
}