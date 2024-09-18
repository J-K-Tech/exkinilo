package kn.jktech.exkinilo.blocks;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockFluid;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.level.World;

import java.util.Random;

public class stillCoolant extends BlockFluid {
    public stillCoolant(int id, Material material) {
        super(id, material);
        allocateTextures();
        setRequiresSelfNotify();
    }
    @Override
    protected void allocateTextures() {
        this.addTexture("coolant", Face.ALL);
    }
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int meta) {
        super.onNeighborBlockChange(world, x, y, z, meta);
        if (world.getBlockId(x, y, z) == this.blockID) {
            this.destabilize(world, x, y, z);
        }
    }

    private void destabilize(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.editingBlocks = true;
        world.setBlockAndMetadata(x, y, z, this.blockID - 1, metadata);
        world.markBlocksDirty(x, y, z, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this.blockID - 1, this.tickRate());
        world.editingBlocks = false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (this.blockMaterial == Material.lava) {
            int fireattempts = rand.nextInt(3);

            for (int fiter = 0; fiter < fireattempts; fiter++) {
                x += rand.nextInt(3) - 1;
                y++;
                z += rand.nextInt(3) - 1;
                int blidat = world.getBlockId(x, y, z);
                if (blidat == 0) {
                    if (this.isBlockFlammable(world, x - 1, y, z)
                            || this.isBlockFlammable(world, x + 1, y, z)
                            || this.isBlockFlammable(world, x, y, z - 1)
                            || this.isBlockFlammable(world, x, y, z + 1)
                            || this.isBlockFlammable(world, x, y - 1, z)
                            || this.isBlockFlammable(world, x, y + 1, z)) {
                        world.setBlockWithNotify(x, y, z, Block.fire.blockID);
                        return;
                    }
                } else if (Block.blocksList[blidat].blockMaterial.getIsSolid()) {
                    return;
                }
            }
        }
    }

    private boolean isBlockFlammable(World world, int x, int y, int z) {
        return world.getBlockMaterial(x, y, z).getBurning();
    }
}
