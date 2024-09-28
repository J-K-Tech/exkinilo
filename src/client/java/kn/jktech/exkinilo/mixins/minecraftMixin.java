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

@Mixin(Minecraft.class)
public abstract class minecraftMixin {
    @Shadow
    public World theWorld;
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow

    public abstract void changeWorld(World world, String arg2, EntityPlayer player);
    @Inject(method = "usePortal",at=@At("HEAD"),cancellable = true)
    public void usePortal(CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        if (thePlayer.getClass().getField("incustomportal").getBoolean(thePlayer)){
            int dim=thePlayer.getClass().getField("goingtodim").getInt(thePlayer);
            int prev=thePlayer.dimension;
            thePlayer.dimension=dim;
            double playerX = this.thePlayer.posX;
            double playerZ = this.thePlayer.posZ;

            short hc = this.theWorld.worldInfo.getHighestChunkNether();
            short lc = this.theWorld.worldInfo.getLowestChunkNether();
            World world = null;
            world = new World(this.theWorld, WorldProvider.getProviderForDimension(dim));
            world.highestChunk = hc;
            world.highestY = hc << 4;
            world.lowestChunk = lc;
            world.lowestY = lc << 4;
            int pos=thePlayer.getClass().getField("goingtodim").getInt(thePlayer);

            this.changeWorld(world, StringTranslate.getInstance().translateKey("gui.world.enterNether"), this.thePlayer);



        }
    }
}
