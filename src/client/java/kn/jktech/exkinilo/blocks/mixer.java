package kn.jktech.exkinilo.blocks;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockGearConveyorBelt;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.EntityWaterMob;
import net.minecraft.src.game.level.World;

import java.util.Random;

public class mixer extends Block {
    private final boolean isPowered;
    public mixer(int id, Material material,boolean p) {
        super(id, material);
        this.isPowered=p;
    }
    @Override
    public void onBlockPlaced(World world, int x, int y, int z, int blockFace) {
        super.onBlockPlaced(world, x, y, z, blockFace);
        world.setBlockMetadata(x,y,z,0);
    }
    // metadata:
    // [xx] 0 [xo] 1 [ox] 2 [oo] 3 [RR] 4
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int delay) {
        if (!world.multiplayerWorld) {
            this.checkIsPowered(world, x, y, z);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.multiplayerWorld) {
            System.out.println("e");
            int metadata = world.getBlockMetadata(x, y, z);
            if (this.isPowered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
                world.setBlockAndMetadataWithNotify(x, y, z, clinilo.MXIDLE, metadata);
                return;
            }
            if (metadata==3&&world.rand.nextFloat()>0.02) world.setBlockMetadata(x,y,z,4);
        }
    }
    private void checkIsPowered(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (this.isPowered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
        } else if (!this.isPowered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlockAndMetadataWithNotify(x, y, z, Block.conveyorBeltActive.blockID, metadata);
        }
    }
    @Override
    public int tickRate() {
        return 1;
    }

}
