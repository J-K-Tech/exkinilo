package kn.jktech.exkinilo.blocks;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.client.physics.AxisAlignedBB;
import net.minecraft.src.client.renderer.block.icon.Icon;
import net.minecraft.src.game.block.*;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;

public class pebbles extends Block {
    public pebbles(int id, Material material) {
        super(id, material);
        setBlockBounds(0f,0f,0f,1f,.1f,1f);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int arg2, int arg3, int arg4) {

        setBlockBounds(0f,0f,0f,1f,.1f,1f);
    }
        @Override
    protected void allocateTextures() {
        this.addTexture("empty",Face.ALL,0);
        this.addTexture("empty",Face.ALL,1);
        this.addTexture("empty",Face.ALL,2);
        this.addTexture("pebble0", Face.TOP,0);
        this.addTexture("pebble1", Face.TOP,1);
        this.addTexture("pebble2", Face.TOP,2);
    }
    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        int m=world.getBlockMetadata(x,y,z);
        if (player.getCurrentEquippedItem()!=null)
        if (player.getCurrentEquippedItem().itemID== clinilo.ROCK&&m<=2){
            world.setBlockAndMetadataWithNotify(x,y,z,this.blockID,++m);
            player.getCurrentEquippedItem().stackSize--;
            return true;
        }
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance)  {
        if (!world.multiplayerWorld) {

            int id = this.idDropped(metadata, world.rand);
            if (id > 0) {
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(clinilo.ROCK, metadata+1, 0));
            }
        }
    }@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int blockFace) {
        return blockFace==1;
    }
}
