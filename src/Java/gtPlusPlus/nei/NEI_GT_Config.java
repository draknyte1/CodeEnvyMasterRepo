package gtPlusPlus.nei;

import gregtech.api.util.CustomRecipeMap;
import gregtech.api.util.Recipe_GT.Gregtech_Recipe_Map;

import codechicken.nei.api.IConfigureNEI;

public class NEI_GT_Config
implements IConfigureNEI {
	public static boolean sIsAdded = true;

	@Override
	public synchronized void loadConfig() {
		sIsAdded = false;
		for (final CustomRecipeMap tMap : gregtech.api.util.CustomRecipeMap.sMappings) {
			if (tMap.mNEIAllowed) {
				new GT_NEI_DefaultHandler(tMap);
			}
		}
		for (final Gregtech_Recipe_Map tMap : gregtech.api.util.Recipe_GT.Gregtech_Recipe_Map.sMappings) {
			if (tMap.mNEIAllowed) {
				new GT_NEI_MultiBlockHandler(tMap);
			}
		}
		sIsAdded = true;
	}

	@Override
	public String getName() {
		return "GT++ NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "(1.01)";
	}
}
