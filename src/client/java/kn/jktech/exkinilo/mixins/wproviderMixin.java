package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.config;
import kn.jktech.exkinilo.world.flathell;
import kn.jktech.exkinilo.world.noodle;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.WorldProvider;
import net.minecraft.src.game.level.WorldProviderHell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldProvider.class)
public class wproviderMixin{@Shadow
public World worldObj;
    public static WorldProvider[] customWorldProviders={null};


    public static WorldProvider getProviderForDimensioncustom(int arg0) throws IllegalAccessException, InstantiationException {
        return customWorldProviders[arg0].getClass().newInstance();
    }
    public static void addprovider(WorldProvider provider){
        WorldProvider[] tmp=new WorldProvider[customWorldProviders.length+1];
        tmp[customWorldProviders.length]=provider;
        customWorldProviders=tmp;
    }

    @Inject(method = "getChunkProvider",at=@At("HEAD"),cancellable = true)
    public void getChunkProvider(CallbackInfoReturnable ci) {
        System.out.println(this.worldObj.getWorldInfo().getGenType());
        if (this.worldObj.getWorldInfo().getGenType()==3) {
            System.out.println("noodle");
            ci.setReturnValue(new noodle(this.worldObj, this.worldObj.getRandomSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()));
            ci.cancel();
        }
    }

}
