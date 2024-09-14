package kn.jktech.exkinilo.mixins;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockLeaves;
import net.minecraft.src.game.block.BlockLeavesBase;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BlockLeavesBase.class)
public class leavesMixin extends Block {

    protected leavesMixin(int id, Material material) {
        super(id, material);
    }
    @Inject(method = "updateTick", at=@At("HEAD"),cancellable = true)
    public void updateTick(World world, int x, int y, int z, Random random, CallbackInfo ci) {
        int []blocks={
                world.getBlockId(x-1,y,z),
                world.getBlockId(x+1,y,z),
                world.getBlockId(x,y,1-z),
                world.getBlockId(x,y,1+z),
                world.getBlockId(x,y-1,z),
                world.getBlockId(x,y+1,z),
        };

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i]==3||blocks[i]==30)if (world.rand.nextFloat()>0.07){
        world.setBlockWithNotify(x, y, z, 30);
        ci.cancel();
    }}
    }
}
