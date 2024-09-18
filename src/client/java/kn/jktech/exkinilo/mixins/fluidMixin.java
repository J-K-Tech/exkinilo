package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.client.renderer.Vec3D;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockFluid;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFluid.class)
public abstract class fluidMixin extends Block {
    @Shadow
    public abstract Vec3D getFlowVector(IBlockAccess blockAccess, int x, int y, int z);


    protected fluidMixin(int id, Material material) {

        super(id, material);

    }
    @Inject(method = "checkForHarden",at = @At("HEAD"),cancellable = true)
    private void checkForHarden(World world, int arg2, int arg3, int arg4, CallbackInfo ci){
        if (this.blockMaterial==Material.snow){
            if (world.getBlockId(arg2, arg3, arg4 - 1) == Block.waterStill.blockID) {
                world.setBlockWithNotify(arg2, arg3, arg4-1, Block.ice.blockID);
            }

            if ( world.getBlockId(arg2, arg3, arg4+1) == Block.waterStill.blockID) {
                world.setBlockWithNotify(arg2, arg3, arg4+1, Block.ice.blockID);
            }

            if (world.getBlockId(arg2-1, arg3, arg4) == Block.waterStill.blockID) {
                world.setBlockWithNotify(arg2-1, arg3, arg4, Block.ice.blockID);
            }

            if (world.getBlockId(arg2+1, arg3, arg4) == Block.waterStill.blockID) {
                world.setBlockWithNotify(arg2+1, arg3, arg4, Block.ice.blockID);
            }

            if (world.getBlockId(arg2, arg3+1, arg4) == Block.waterStill.blockID) {
                world.setBlockWithNotify(arg2, arg3+1, arg4, Block.ice.blockID);
            }
            if (world.getBlockId(arg2, arg3-1, arg4) == Block.waterStill.blockID) {
                world.setBlockWithNotify(arg2, arg3-1, arg4, Block.ice.blockID);
            }
            ci.cancel();
        }
    }
    @Overwrite
    public static double func_293_a(IBlockAccess blockAccess, int arg1, int arg2, int arg3, Material material) {
        Vec3D vec3D = null;
        if (material == Material.water) {
            vec3D = ((fluidMixin)Block.waterMoving).getFlowVector(blockAccess, arg1, arg2, arg3);
        }

        if (material == Material.lava) {
            vec3D = ((fluidMixin)Block.lavaMoving).getFlowVector(blockAccess, arg1, arg2, arg3);
        }
        if (material == Material.snow) {
            vec3D = ((fluidMixin) clinilo.COOLANTFLOW).getFlowVector(blockAccess, arg1, arg2, arg3);
        }

        return vec3D.xCoord == 0.0 && vec3D.zCoord == 0.0
                ? -1000.0
                : Math.atan2(vec3D.zCoord, vec3D.xCoord) - (Math.PI / 2);
    }
}
