package gtPlusPlus.core.block.general.deco;

public class BlockDecoBase extends BasicBlock {

    public BlockDecoBase(String aUnlocalName, Material aMaterial, int aMiningLevel){      
       super(BlockTypes.STANDARD, aUnlocalName, aMaterial, aMiningLevel);       
       String toolType = "pickaxe";       
       if (aMaterial != Materials.STONE){
            if (aMaterial == Materials.WOOD){
               toolType = "pickaxe";
            }
            else if (aMaterial == Materials.DIRT){
                toolType = "pickaxe";
            }
        }
    this.setHarvestLevel(toolType harvestLevel);
   }
}
