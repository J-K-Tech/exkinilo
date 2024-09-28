package kn.jktech.exkinilo.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.gui.StringTranslate;
import net.minecraft.src.client.player.EntityPlayerSP;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;

@Mixin(Minecraft.class)
public abstract class minecraftMixin {
    @Shadow
    public World theWorld;
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow

    public abstract void changeWorld(World world, String arg2, EntityPlayer player);
    @Inject(method = "usePortal",at=@At("HEAD"),cancellable = true)
    public void usePortal(CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (thePlayer.getClass().getField("incustomportal").getBoolean(thePlayer)&&thePlayer.dimension==0){
            int dim=thePlayer.getClass().getField("goingtodim").getInt(thePlayer);
            thePlayer.dimension=dim;
            WorldProvider wp=(WorldProvider)WorldProvider.class.getMethod("getProviderForDimensioncustom").invoke(null,dim);

            if (this.thePlayer.isEntityAlive()) {
                this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
            }
            short hc = this.theWorld.worldInfo.getHighestChunkOW();
            short lc = this.theWorld.worldInfo.getLowestChunkOW();
            World world = new World(this.theWorld, wp);
            world.highestChunk = hc;
            world.highestY = hc << 4;
            world.lowestChunk = lc;
            world.lowestY = lc << 4;

            this.changeWorld(world, StringTranslate.getInstance().translateKey("gui.world.enterNether"), this.thePlayer);
            ci.cancel();


        }
    }
}
