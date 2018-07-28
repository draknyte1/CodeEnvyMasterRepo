package gtPlusPlus.xmod.gregtech.api.objects;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.minecraft.BlockPos;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ThreadedBuffer;
import gtPlusPlus.xmod.gregtech.common.tileentities.automation.GT_MetaTileEntity_ThreadedChestBuffer;

public class GregtechBufferThread extends Thread {

	public static final ConcurrentMap<String, GregtechBufferThread> mBufferThreadAllocation = new ConcurrentHashMap<String, GregtechBufferThread>();
	private final BlockPos mBlockPos;
	private final int mMaxLife = 300;
	private int mLifeCycleTime = mMaxLife;
	private final  String mID;

	public static synchronized final GregtechBufferThread getBufferThread(BlockPos pos) {
		if (pos != null && mBufferThreadAllocation.containsKey(""+pos.getUniqueIdentifier())){
			Logger.INFO("[SB] Found an existing thread for this Buffer.");
			return mBufferThreadAllocation.get(""+pos.getUniqueIdentifier());
		}
		else {
			return new GregtechBufferThread(pos);
		}
	}

	public GregtechBufferThread(BlockPos pos) {
		super();
		String aID = pos != null ? pos.getUniqueIdentifier() : ""+Short.MIN_VALUE;
		this.mID = aID;
		if (pos != null && !mBufferThreadAllocation.containsKey(mID)){
			mBlockPos = pos;
			mBufferThreadAllocation.put(mID, this);
		}
		else {
			this.mLifeCycleTime = 1;
			mBlockPos = null;
		}
		this.setName("GTPP-SuperBuffer("+mID+")");
		this.setDaemon(true);
		if (mBlockPos != null && !this.isAlive()) {
			try {
				start();
				Logger.INFO("[SB] Created a SuperBuffer Thread for dimension "+mID+".");
			}
			catch (Throwable t_) {
				//Do nothing.
			}
		}
	}

	public synchronized int getTimeLeft() {
		return this.mLifeCycleTime;
	}

	public synchronized void fillStacksIntoFirstSlots(GT_MetaTileEntity_ThreadedChestBuffer mBuffer) {
		for (int i = 0; i < mBuffer.mInventorySynchro.length - 1; ++i) {
			for (int j = i + 1; j < mBuffer.mInventorySynchro.length - 1; ++j) {
				if (mBuffer.mInventorySynchro[j] != null && (mBuffer.mInventorySynchro[i] == null
						|| areStacksEqual(mBuffer.mInventorySynchro[i], mBuffer.mInventorySynchro[j]))) {
					moveStackFromSlotAToSlotB((IInventory) mBuffer.getBaseMetaTileEntity(),
							(IInventory) mBuffer.getBaseMetaTileEntity(), j, i, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
				}
			}
		}
	}

	public synchronized boolean moveItems(final IGregTechTileEntity aBaseMetaTileEntity, final long aTimer, GT_MetaTileEntity_ThreadedBuffer mBuffer) {
		final byte mTargetStackSize = (byte) mBuffer.mTargetStackSize;
		final int tCost = GT_Utility.moveOneItemStack((Object) aBaseMetaTileEntity,
				(Object) aBaseMetaTileEntity.getTileEntityAtSide(aBaseMetaTileEntity.getBackFacing()),
				aBaseMetaTileEntity.getBackFacing(), aBaseMetaTileEntity.getFrontFacing(), (List<ItemStack>) null, false,
				(byte) ((mTargetStackSize == 0) ? 64 : ((byte) mTargetStackSize)),
				(byte) ((mTargetStackSize == 0) ? 1 : ((byte) mTargetStackSize)), (byte) 64, (byte) 1);
		if (tCost > 0 || aBaseMetaTileEntity.hasInventoryBeenModified()) {
			mBuffer.mSuccess = 50;
			aBaseMetaTileEntity.decreaseStoredEnergyUnits((long) Math.abs(tCost), true);
			return true;
		}
		return false;
	}

	public synchronized void onPostTick(final IGregTechTileEntity aBaseMetaTileEntity, final long aTimer, GT_MetaTileEntity_ThreadedBuffer mBuffer) {
		if (aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.isServerSide()
				&& aBaseMetaTileEntity.isUniversalEnergyStored(mBuffer.getMinimumStoredEU())
				&& (aBaseMetaTileEntity.hasWorkJustBeenEnabled() || aBaseMetaTileEntity.hasInventoryBeenModified()
						|| aTimer % 200L == 0L || mBuffer.mSuccess > 0)) {
			--mBuffer.mSuccess;
			if (mLifeCycleTime < (mMaxLife-1)){
				mLifeCycleTime += 1;				
			}
			//Logger.INFO("Ticking SB @ "+mBuffer.getLogicThread().mBlockPos.getUniqueIdentifier() + " | Time Left: "+mLifeCycleTime);
			moveItems(aBaseMetaTileEntity, aTimer, mBuffer);
			for (byte b = 0; b < 6; ++b) {
				aBaseMetaTileEntity.setInternalOutputRedstoneSignal(b, (byte) (mBuffer.bInvert ? 15 : 0));
			}
			if (mBuffer.bRedstoneIfFull) {
				for (byte b = 0; b < 6; ++b) {
					aBaseMetaTileEntity.setInternalOutputRedstoneSignal(b, (byte) (mBuffer.bInvert ? 0 : 15));
				}
				for (int i = 0; i < mBuffer.mInventorySynchro.length; ++i) {
					if (mBuffer.isValidSlot(i) && mBuffer.mInventorySynchro[i] == null) {
						for (byte b2 = 0; b2 < 6; ++b2) {
							aBaseMetaTileEntity.setInternalOutputRedstoneSignal(b2, (byte) (mBuffer.bInvert ? 15 : 0));
						}
						aBaseMetaTileEntity.decreaseStoredEnergyUnits(1L, true);
						break;
					}
				}
			}
		}
	}

	/**
	 * Some GT logic we'd like to move off thread
	 */

	public synchronized boolean areStacksEqual(final ItemStack aStack1, final ItemStack aStack2) {
		return areStacksEqual(aStack1, aStack2, false);
	}

	public synchronized boolean areStacksEqual(final ItemStack aStack1, final ItemStack aStack2, final boolean aIgnoreNBT) {
		return aStack1 != null && aStack2 != null && aStack1.getItem() == aStack2.getItem()
				&& (aIgnoreNBT || (aStack1.getTagCompound() == null == (aStack2.getTagCompound() == null)
				&& (aStack1.getTagCompound() == null
				|| aStack1.getTagCompound().equals((Object) aStack2.getTagCompound()))))
				&& (Items.feather.getDamage(aStack1) == Items.feather.getDamage(aStack2)
				|| Items.feather.getDamage(aStack1) == 32767 || Items.feather.getDamage(aStack2) == 32767);
	}

	public synchronized byte moveStackFromSlotAToSlotB(final IInventory aTileEntity1, final IInventory aTileEntity2,
			final int aGrabFrom, final int aPutTo, byte aMaxTargetStackSize, final byte aMinTargetStackSize,
			final byte aMaxMoveAtOnce, final byte aMinMoveAtOnce) {
		if (aTileEntity1 == null || aTileEntity2 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0
				|| aMinTargetStackSize > aMaxTargetStackSize || aMaxMoveAtOnce <= 0
				|| aMinMoveAtOnce > aMaxMoveAtOnce) {
			return 0;
		}
		final ItemStack tStack1 = aTileEntity1.getStackInSlot(aGrabFrom);
		final ItemStack tStack2 = aTileEntity2.getStackInSlot(aPutTo);
		ItemStack tStack3 = null;
		if (tStack1 != null) {
			if (tStack2 != null && !areStacksEqual(tStack1, tStack2)) {
				return 0;
			}
			tStack3 = GT_Utility.copy(tStack1);
			aMaxTargetStackSize = (byte) Math.min(aMaxTargetStackSize,
					Math.min(tStack3.getMaxStackSize(),
							Math.min((tStack2 == null) ? Integer.MAX_VALUE : tStack2.getMaxStackSize(),
									aTileEntity2.getInventoryStackLimit())));
			tStack3.stackSize = Math.min(tStack3.stackSize,
					aMaxTargetStackSize - ((tStack2 == null) ? 0 : tStack2.stackSize));
			if (tStack3.stackSize > aMaxMoveAtOnce) {
				tStack3.stackSize = aMaxMoveAtOnce;
			}
			if (tStack3.stackSize + ((tStack2 == null) ? 0 : tStack2.stackSize) >= Math.min(tStack3.getMaxStackSize(),
					aMinTargetStackSize) && tStack3.stackSize >= aMinMoveAtOnce) {
				tStack3 = aTileEntity1.decrStackSize(aGrabFrom, tStack3.stackSize);
				aTileEntity1.markDirty();
				if (tStack3 != null) {
					if (tStack2 == null) {
						aTileEntity2.setInventorySlotContents(aPutTo, GT_Utility.copy(tStack3));
						aTileEntity2.markDirty();
					} else {
						final ItemStack itemStack = tStack2;
						itemStack.stackSize += tStack3.stackSize;
						aTileEntity2.markDirty();
					}
					return (byte) tStack3.stackSize;
				}
			}
		}
		return 0;
	}

	//Logic Vars
	private boolean mRunning = true;

	@Override
	public void run() {	
		//While thread is alive.
		run: while (mRunning) {
			//While thread is active, lets tick it's life down.
			life: while (mLifeCycleTime > 0) {
				if (!mRunning) {
					break life;
				}
				
				//Remove invalid threads
				if (this.mBlockPos.world == null || this.mBlockPos.getBlockAtPos() == null) {
					destroy();
					break run;
				}				
				//Prevent Overflows
				if (mLifeCycleTime > mMaxLife) {
					mLifeCycleTime = mMaxLife;
				}			
				try {
					sleep(1000);
					mLifeCycleTime--;
					Logger.WARNING("[SB] Ticking Thread "+mID+" | Remaining: "+mLifeCycleTime+"s");
				}
				catch (InterruptedException e) {
					mLifeCycleTime = 0;
				}
			}
			if (mLifeCycleTime <= 0) {
				destroy();
				break run;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void destroy() {		
		mRunning = false;
		GregtechBufferThread.mBufferThreadAllocation.remove(mID, this);
		Logger.INFO("[SB] Removing Thread "+mID);
		try {
			stop();
			this.finalize();
		}
		catch (Throwable t) {
			//Do nothing.
		}
	}




}
