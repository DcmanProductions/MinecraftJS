package chase.minecraft.architectury.minecraftjs.fabric;

import chase.minecraft.architectury.minecraftjs.MinecraftJS;
import net.fabricmc.api.ModInitializer;

public class MinecraftJSFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		MinecraftJS.init();
	}
}