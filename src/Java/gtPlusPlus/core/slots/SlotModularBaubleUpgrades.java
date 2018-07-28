package gtPlusPlus.core.slots;

import static gtPlusPlus.core.tileentities.machines.TileEntityModularityTable.*;

import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.Pair;
import gtPlusPlus.core.util.minecraft.ModularArmourUtils.BT;
import gtPlusPlus.core.util.minecraft.ModularArmourUtils.Modifiers;

public class SlotModularBaubleUpgrades extends Slot {

	public SlotModularBaubleUpgrades(final IInventory inventory, final int slot, final int x, final int y) {
		super(inventory, slot, x, y);

	}

	@Override
	public boolean isItemValid(final ItemStack itemstack) {
		boolean isValid = false;
		if (itemstack != null) {
			Logger.INFO("trying to insert " + itemstack.getDisplayName());
			Logger.INFO("Valid Upgrade count: " + mValidUpgradeList.size());

			Iterator<Entry<ItemStack, BT>> it = mValidUpgradeListFormChange.entrySet().iterator();
			while (it.hasNext()) {
				Entry<ItemStack, BT> pair = it.next();
				if (pair.getKey().getItem() == itemstack.getItem()
						&& pair.getKey().getItemDamage() == itemstack.getItemDamage()) {
					isValid = true;
				}
			}

			Iterator<Entry<ItemStack, Pair<Modifiers, Integer>>> it2 = mValidUpgradeList.entrySet().iterator();
			while (it2.hasNext()) {
				Entry<ItemStack, Pair<Modifiers, Integer>> pair = it2.next();
				if (pair.getKey().getItem() == itemstack.getItem()
						&& pair.getKey().getItemDamage() == itemstack.getItemDamage()) {
					isValid = true;
				}
			}
		}
		return isValid;
	}

	@Override
	public int getSlotStackLimit() {
		return 64;
	}
}
