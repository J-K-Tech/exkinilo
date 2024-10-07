package kn.jktech.exkinilo.blocks;

import net.minecraft.src.client.player.EntityPlayerSP;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockLever;
import net.minecraft.src.game.block.BlockStairs;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemBucket;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;

public class vaporlek extends Block {
    public vaporlek(int id, Material material) {
        super(id, material);
    }

    @Override
    protected void allocateTextures() {
        for (int i = 0; i < 15; i++) {
            this.addTexture("vapor"+i/5, Face.ALL,i);
            this.addTexture("vapor", Face._06,i);
            this.addTexture("base", Face.TOP,i);
            this.addTexture("base", Face.BOTTOM,i);


        }
    }
    @Override
    public void onBlockPlaced(World world, int x, int y, int z, int blockFace) {
        super.onBlockPlaced(world, x, y, z, blockFace);
        world.setBlockMetadata(x,y,z,0);
    }
    

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        super.onBlockClicked(world, x, y, z, player);
        if (player.getCurrentEquippedItem()!=null){
        System.out.println(world.getBlockMetadata(x,y,z));
        int i=player.getCurrentEquippedItem().itemID;
        if (i== ItemBucket.bucketEmpty.itemID){
            if (world.getBlockMetadata(x,y,z)>=12){
                world.setBlockMetadata(x,y,z,0);
                player.inventory.mainInventory[player.inventory.currentItem]
                        =new ItemStack(Item.bucketWater);
                world.playSoundEffect(x,y,z,"kinilo.mixer.mixer_bucket", 3.8F, 1.0F);
            }
        }}
        return true;
    }
}
