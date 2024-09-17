package kn.jktech.exkinilo.mixins;

import net.minecraft.src.client.player.MovementInput;
import net.minecraft.src.client.player.MovementInputFromOptions;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockSapling;
import net.minecraft.src.game.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public class movementMixin extends MovementInput {
    @Shadow
    private boolean[] movementKeyStates = new boolean[10];
    @Inject(method = "updatePlayerMoveState",at=@At(value = "HEAD"))
    public void updatePlayerMoveStateON(EntityPlayer entityPlayer, CallbackInfo ci) {

        if(this.sneak != this.movementKeyStates[5]&&this.movementKeyStates[5]){
            int x= (int) Math.floor(entityPlayer.posX);int y= (int)Math.floor(entityPlayer.posY - 1);int z= (int) Math.floor(entityPlayer.posZ);
            int []blocks={
                    entityPlayer.worldObj.getBlockId(x-1,y,z),
                    entityPlayer.worldObj.getBlockId(x+1,y,z),
                    entityPlayer.worldObj.getBlockId(x,y,1-z),
                    entityPlayer.worldObj.getBlockId(x,y,1+z),
                    };
            int [][] pos={
                    {-1,0},{1,0},{0,-1},{0,1}
            };
            for (int i = 0; i < blocks.length; i++) {
                if (Block.blocksList[entityPlayer.worldObj.getBlockId(x+pos[i][0], y, z+pos[i][1])]instanceof BlockSapling){
                    if(entityPlayer.worldObj.rand.nextFloat()>=.05){
                    ((BlockSapling)Block.blocksList[entityPlayer.worldObj.getBlockId(x+pos[i][0], y, z+pos[i][1])]).growTree(entityPlayer.worldObj, x+pos[i][0], y, z+pos[i][1]);
                }}}
            }


    }
}

