package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.clinilo;
import kn.jktech.exkinilo.config;
import net.minecraft.client.Minecraft;
import net.minecraft.src.client.player.PlayerController;
import net.minecraft.src.client.player.PlayerControllerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerControllerSP.class)
public class hitwait extends PlayerController {
    @Shadow
    private int blockHitWait;
    public hitwait(Minecraft minecraft) {
        super(minecraft);
    }
    @Inject(method = "sendBlockRemoving",at = @At("HEAD"))
    public void sendBlockRemoving(int x, int y, int z, int facing, CallbackInfo c) {
        if(config.INSTANCE.noblockdelay){
            blockHitWait=0;
        }
    }

}
