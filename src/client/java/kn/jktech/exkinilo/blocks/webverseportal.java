package kn.jktech.exkinilo.blocks;

import net.minecraft.src.game.Direction;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockPortal;
import net.minecraft.src.game.block.BlockTallGrass;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.chunk.ChunkCoordinates;

public class webverseportal extends BlockPortal {
    public webverseportal(int id) {
        super(id);
        this.setBlockBounds(0.0F, 0.0F, 0.F, 1F, 2.0F, 1F);
    }
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 2.0F, 1F);}
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int blockFace) {
        if (blockFace == 0 && this.minY > 0.0) {
            return true;
        } else if (blockFace == 1 && this.maxY < 1.0) {
            return true;
        } else if (blockFace == 2 && this.minZ > 0.0) {
            return true;
        } else if (blockFace == 3 && this.maxZ < 1.0) {
            return true;
        } else if (blockFace == 4 && this.minX > 0.0) {
            return true;
        } else {
            return blockFace == 5 && this.maxX < 1.0 ? true : !blockAccess.isBlockOpaqueCube(x, y, z);
        }
    }
    public static class PortalSize {
        private final World world;
        private final int direction;
        private final int rotation2;
        private final int rotation1;
        private int surface = 0;
        private ChunkCoordinates chunk;
        private int height;
        private int width;

        public PortalSize(World world, int x, int y, int z, int direction) {

            this.world = world;
            this.direction = direction;
            this.rotation1 = BlockPortal.rotationTable[direction][0];
            this.rotation2 = BlockPortal.rotationTable[direction][1];
            int i = y;

            while (y > i - 21 && y > 0 && this.isValidPortal(world.getBlockId(x, y - 1, z))) {
                y--;
            }

            i = this.getWidth(x, y, z, this.rotation1) - 1;
            if (i >= 0) {
                this.chunk = new ChunkCoordinates(
                        x + i * Direction.offsetX[this.rotation1], y, z + i * Direction.offsetZ[this.rotation1]
                );
                this.width = this.getWidth(this.chunk.x, this.chunk.y, this.chunk.z, this.rotation2);
                if (this.width < 2 || this.width > 21) {
                    this.chunk = null;
                    this.width = 0;
                }
            }

            if (this.chunk != null) {
                this.height = this.getHeight();
            }

        }

        protected int getWidth(int x, int y, int z, int direction) {
            return 1;
        }

        protected int getHeight() {
            return 3;
        }

        protected boolean isValidPortal(int block) {
            return Block.blocksList[block].blockMaterial == Material.air
                    || block == Block.fire.blockID
                    || block == Block.portal.blockID;
        }

        public boolean isPortalSizeValid() {
            return this.chunk != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void makePortal() {
            for (int i = 0; i < this.width; i++) {
                int x = this.chunk.x + Direction.offsetX[this.rotation2] * i;
                int z = this.chunk.z + Direction.offsetZ[this.rotation2] * i;

                for (int j = 0; j < this.height; j++) {
                    int y = this.chunk.y + j;
                    this.world.setBlockAndMetadata(x, y, z, Block.portal.blockID, this.direction);
                }
            }
        }





    }
}
