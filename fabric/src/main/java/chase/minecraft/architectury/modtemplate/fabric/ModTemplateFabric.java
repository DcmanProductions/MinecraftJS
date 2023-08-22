package chase.minecraft.architectury.modtemplate.fabric;

import chase.minecraft.architectury.modtemplate.ModTemplate;
import net.fabricmc.api.ModInitializer;

public class ModTemplateFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		ModTemplate.init();
	}
}