package kn.jktech.exkinilo.mixins;

import com.fox2code.foxloader.loader.ModContainer;
import com.fox2code.foxloader.updater.AbstractUpdater;
import com.fox2code.foxloader.updater.UpdateManager;
import kn.jktech.exkinilo.config;
import kn.jktech.exkinilo.world.flathell;
import kn.jktech.exkinilo.world.noodle;
import net.minecraft.src.game.level.WorldProvider;
import net.minecraft.src.game.level.WorldProviderHell;
import net.minecraft.src.game.level.chunk.ChunkProviderFlatworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldProviderHell.class)
public class wproviderMixinhek extends WorldProvider{

    @Inject(method = "getChunkProvider",at=@At("TAIL"),cancellable = true)
    public void getChunkProvider(CallbackInfoReturnable ci) {
        System.out.println(this.worldObj.getWorldInfo().getGenType());
        if (this.worldObj.getWorldInfo().getGenType()==1&& config.INSTANCE.flatnether) {
            ci.setReturnValue(
                new flathell(this.worldObj, this.worldObj.getRandomSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()
                )
            );
            ci.cancel();
        }
        if (this.worldObj.getWorldInfo().getGenType()==3) {
            System.out.println("noodle");
            ci.setReturnValue(new noodle(this.worldObj, this.worldObj.getRandomSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()));
            ci.cancel();
        }
    }

}
