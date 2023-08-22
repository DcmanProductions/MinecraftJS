package chase.minecraft.architectury.minecraftjs.mixins;


import chase.minecraft.architectury.minecraftjs.ScriptLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.ReloadCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin
{
	
	@Inject(at = @At("HEAD"), method = "reloadPacks")
	static void reloadJS(Collection<String> collection, CommandSourceStack commandSourceStack, CallbackInfo ci)
	{
		ScriptLoader.Instance.LoadScripts();
	}
}
