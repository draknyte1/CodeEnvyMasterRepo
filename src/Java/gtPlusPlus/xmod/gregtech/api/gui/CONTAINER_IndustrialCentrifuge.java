package gtPlusPlus.xmod.gregtech.api.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The Container I use for all my Basic Machines
 */
public class CONTAINER_IndustrialCentrifuge extends GT_ContainerMetaTile_Machine {

	public CONTAINER_IndustrialCentrifuge(final InventoryPlayer aInventoryPlayer, final IGregTechTileEntity aTileEntity) {

		super(aInventoryPlayer, aTileEntity);
	}

	public CONTAINER_IndustrialCentrifuge(final InventoryPlayer aInventoryPlayer, final IGregTechTileEntity aTileEntity, final boolean bindInventory) {
		super(aInventoryPlayer, aTileEntity, bindInventory);
	}

	@Override
	public void addSlots(final InventoryPlayer aInventoryPlayer) {
		this.addSlotToContainer(new Slot(this.mTileEntity, 1, 154, 42));
	}

	@Override
	public int getSlotCount() {
		return 1;
	}

	@Override
	public int getShiftClickSlotCount() {
		return 1;
	}
}
