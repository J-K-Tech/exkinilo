package kn.jktech.exkinilo.blocks;

import com.fox2code.foxloader.client.WorldProviderCustom;
import com.fox2code.foxloader.client.helpers.WorldProviderHelper;
import net.minecraft.src.game.Direction;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockPortal;
import net.minecraft.src.game.block.BlockTallGrass;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.WorldProvider;
import net.minecraft.src.game.level.chunk.ChunkCoordinates;

import java.lang.reflect.InvocationTargetException;

public class webverseportal extends BlockPortal {
    public webverseportal(int id) {
        super(id);
        this.setBlockBounds(0.0F, 0.0F, 0.F, 1F, 2.0F, 1F);
    }
    public webverseportal(int id, WorldProviderCustom worldProvider , String name) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        this(id);
        this.getClass().getField("wp").set(this,name);
        WorldProviderCustom.addprovider(name,worldProvider);
    }
    @Override
    public boolean tryToCreatePortal(World world, int x, int y, int z) {
        if (world.getRegisteredDimensionID() == 0
                && world.getBlockId(x, y - 1, z) == 0
                && world.getBlockId(x, y, z) == 0
                && world.getBlockId(x, y + 1, z) == 0) {
            world.setBlock(x,y,z,this.blockID);
            return true;

        } else return false;
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
}
