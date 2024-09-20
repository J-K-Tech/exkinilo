package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockFire;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;

import java.util.Random;

@Mixin(BlockFire.class)
public class fireMixin {
    @Shadow
    private int[] abilityToCatchFire;
    @Inject(method = "tryToCatchBlockOnFire",at=@At(value = "HEAD"),cancellable = true)
    public void tryToCatchBlockOnFire(
            World world, int x, int y, int z, int bound, Random random, int arg7,CallbackInfo co
    ){
        System.out.println("a");
        if (world.getBlockId(x,y,z)== clinilo.FIREWOOD
        &&world.getBlockId(x,y-1,z)==Block.blockIron.blockID){
            System.out.println("b");
            int ability = this.abilityToCatchFire[world.getBlockId(x, y, z)];
            if (random.nextInt(bound) < ability){
            world.setBlockWithNotify(x,y,z, Block.blockCoal.blockID);
            co.cancel();}
        }
    }
}
