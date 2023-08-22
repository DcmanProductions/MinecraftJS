package chase.minecraft.architectury.modtemplate.forge;

import chase.minecraft.architectury.modtemplate.ModTemplate;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModTemplate.MOD_ID)
public class ModTemplateForge
{
	public ModTemplateForge()
	{
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(ModTemplate.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		ModTemplate.init();
	}
}