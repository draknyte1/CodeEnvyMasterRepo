package gtPlusPlus.core.util.minecraft;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;

import gtPlusPlus.api.objects.Logger;

public class ShapelessUtils {


	public static boolean addShapelessRecipe(final ItemStack output, final Object ... params)
	{
		final ArrayList<ItemStack> arraylist = new ArrayList<>();
		final Object[] aobject = params;
		final int i = params.length;

		for (int j = 0; j < i; ++j)
		{
			final Object object1 = aobject[j];

			if (object1 instanceof ItemStack)
			{
				arraylist.add(((ItemStack)object1).copy());
			}
			else if (object1 instanceof Item)
			{
				arraylist.add(new ItemStack((Item)object1));
			}
			else
			{
				if ((object1 == null))
				{
					Logger.INFO(("Invalid shapeless input, ignoring!"));
				}
				else if (!(object1 instanceof Block) && (object1 != null))
				{
					Logger.INFO(("Invalid shapeless recipe!"));
					return false;
				}
				else {
					arraylist.add(new ItemStack((Block)object1));
				}
			}
		}
		CraftingManager.getInstance().getRecipeList().add(new ShapelessRecipes(output, arraylist));
		//CraftingManager.getInstance().addShapelessRecipe(output, arraylist);
		return true;
	}


}
