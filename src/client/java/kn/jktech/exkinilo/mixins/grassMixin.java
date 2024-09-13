package kn.jktech.exkinilo.mixins;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockGrass;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGrass.class)
public class grassMixin extends Block {
    protected grassMixin(int id, Material material) {
        super(id, material);
    }
    @Inject(method="doBlockDropEvent",at= @At(value = "INVOKE", target =
"Lnet/minecraft/src/game/block/Block;doBlockDropEvent(Lnet/minecraft/src/game/level/World;IIILnet/minecraft/src/game/entity/player/EntityPlayer;I)V"))
    public void doBlockDropEventMix(World world, int x, int y, int z, EntityPlayer player, int metadata, CallbackInfo ci) {

        float c=world.rand.nextFloat();
        if (c <= 0.05 ) {
            c=world.rand.nextFloat();
            if (c>0.75){
                this.dropBlockAsItem_do(world,x,y,z,new ItemStack(sapling.blockID,1));
            }
            else if (c<.76&&c>.5){
                this.dropBlockAsItem_do(world,x,y,z,new ItemStack(saplingCherry.blockID,1));
            }
            else if (c<.51&&c>.25){
                this.dropBlockAsItem_do(world,x,y,z,new ItemStack(saplingFir.blockID,1));}
            else{
                this.dropBlockAsItem_do(world,x,y,z,new ItemStack(saplingSpruce.blockID,1));}
        }
    }
}
