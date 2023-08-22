package chase.minecraft.architectury.minecraftjs.forge;

import chase.minecraft.architectury.minecraftjs.MinecraftJS;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MinecraftJS.MOD_ID)
public class MinecraftJSForge
{
	public MinecraftJSForge()
	{
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(MinecraftJS.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		MinecraftJS.init();
	}
}