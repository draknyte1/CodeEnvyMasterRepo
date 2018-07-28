package gtPlusPlus.core.util.minecraft;

import java.util.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import gtPlusPlus.core.util.Utils;

public class PlayerUtils {

	public static void messagePlayer(final EntityPlayer P, final String S){
		gregtech.api.util.GT_Utility.sendChatToPlayer(P, S);
	}

	public static EntityPlayer getPlayer(final String name){
		try{
			final List<EntityPlayer> i = new ArrayList<>();
			final Iterator<EntityPlayerMP> iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
			while (iterator.hasNext()) {
				i.add((iterator.next()));
			}
			for (final EntityPlayer temp : i) {
				if (temp.getDisplayName().toLowerCase().equals(name.toLowerCase())){
					return temp;
				}
			}
		}
		catch(final Throwable e){}
		return null;
	}

	public static EntityPlayer getPlayerOnServerFromUUID(final UUID parUUID){
		if (parUUID == null)
		{
			return null;
		}
		final List<EntityPlayerMP> allPlayers = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		for (final EntityPlayerMP player : allPlayers)
		{
			if (player.getUniqueID().equals(parUUID))
			{
				return player;
			}
		}
		return null;
	}

	//Not Clientside
	public static EntityPlayer getPlayerInWorld(final World world, final String Name){
		final List<EntityPlayer> i = world.playerEntities;
		final Minecraft mc = Minecraft.getMinecraft();
		try{
			for (final EntityPlayer temp : i) {
				if (temp.getDisplayName().toLowerCase().equals(Name.toLowerCase())){
					return temp;
				}
			}
		}
		catch(final NullPointerException e){}
		return null;
	}

	public static boolean isPlayerOP(final EntityPlayer player){
		if (player.canCommandSenderUseCommand(2, "")){
			return true;
		}
		return false;
	}

	//Not Clientside
	public static ItemStack getItemStackInPlayersHand(final World world, final String Name){
		final EntityPlayer thePlayer = getPlayer(Name);
		ItemStack heldItem = null;
		try{heldItem = thePlayer.getHeldItem();
		}catch(final NullPointerException e){return null;}
		if (heldItem != null){
			return heldItem;
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static ItemStack getItemStackInPlayersHand(){
		final Minecraft mc = Minecraft.getMinecraft();
		ItemStack heldItem = null;
		try{heldItem = mc.thePlayer.getHeldItem();
		}catch(final NullPointerException e){return null;}
		if (heldItem != null){
			return heldItem;
		}
		return null;
	}

	@SideOnly(Side.SERVER)
	public static ItemStack getItemStackInPlayersHand(final EntityPlayer player){
		ItemStack heldItem = null;
		try{heldItem = player.getHeldItem();
		}catch(final NullPointerException e){
			e.printStackTrace();
			return null;
		}
		if (heldItem != null){
			return heldItem;
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static Item getItemInPlayersHandClient(){
		final Minecraft mc = Minecraft.getMinecraft();
		Item heldItem = null;

		try{heldItem = mc.thePlayer.getHeldItem().getItem();
		}catch(final NullPointerException e){return null;}

		if (heldItem != null){
			return heldItem;
		}

		return null;
	}

	public static Item getItemInPlayersHand(final EntityPlayer player){
		Item heldItem = null;
		try{
			heldItem = player.getHeldItem().getItem();
		}catch(final NullPointerException e){return null;}

		if (heldItem != null){
			return heldItem;
		}
		return null;
	}

	public final static EntityPlayer getPlayerEntityByName(final String aPlayerName){
		final EntityPlayer player = PlayerUtils.getPlayer(aPlayerName);
		if (player != null){
			return player;
		}
		return null;
	}

	public final static UUID getPlayersUUIDByName(final String aPlayerName){
		final EntityPlayer player = PlayerUtils.getPlayer(aPlayerName);
		if (player != null){
			return player.getUniqueID();
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static final boolean isPlayerAlkalus(){
		if (Utils.isServer()){
			return false;
		}
		return isPlayerAlkalus(Minecraft.getMinecraft().thePlayer);
	}
	
	public static final boolean isPlayerAlkalus(EntityPlayer player){
		if (player != null){
			if (player.getDisplayName().toLowerCase().equals("draknyte1") || player.getDisplayName().toLowerCase().equals("alkalus")){
				return true;
			}
		}
		return false;
	}

}
