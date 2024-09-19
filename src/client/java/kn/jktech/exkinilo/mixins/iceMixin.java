package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.tools.sieve;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockIce;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockIce.class)
public class iceMixin extends Block {
    protected iceMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "doBlockDropEvent",at = @At("HEAD"),cancellable = true)
    public void doBlockDropEvent(World world, int x, int y, int z, EntityPlayer player, int metadata, CallbackInfo ci){
    if(player.getCurrentEquippedItem().getItem()instanceof sieve) {
        super.doBlockDropEvent(
                world,x,y,z,player,metadata
        );
        ci.cancel();
    }

}
}
