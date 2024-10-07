package kn.jktech.exkinilo.blocks;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.client.renderer.block.icon.Icon;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;

public class pebbles extends Block {
    public pebbles(int id, Material material) {
        super(id, material);
        setBlockBounds(0,0,0,1,.1f,1);
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
        if (player.getCurrentEquippedItem().itemID== clinilo.ROCK&&m<=2){
            world.setBlockMetadata(x,y,z,++m);
            return true;
        }
        return false;
    }
}
