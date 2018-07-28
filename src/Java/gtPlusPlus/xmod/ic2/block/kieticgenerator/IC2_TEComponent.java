package gtPlusPlus.xmod.ic2.block.kieticgenerator;

import java.io.DataInput;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import ic2.core.block.TileEntityBlock;

public abstract class IC2_TEComponent
{
	protected final TileEntityBlock parent;

	public IC2_TEComponent(final TileEntityBlock parent)
	{
		this.parent = parent;
	}

	public abstract String getDefaultName();

	public void readFromNbt(final NBTTagCompound nbt) {}

	public NBTTagCompound writeToNbt()
	{
		return null;
	}

	public void onLoaded() {}

	public void onUnloaded() {}

	public void onNeighborUpdate(final Block srcBlock) {}

	public void onContainerUpdate(final String name, final EntityPlayerMP player) {}

	public void onNetworkUpdate(final DataInput is)
			throws IOException
	{}

	public boolean enableWorldTick()
	{
		return false;
	}

	public void onWorldTick() {}
}
