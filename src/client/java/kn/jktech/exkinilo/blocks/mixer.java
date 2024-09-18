package kn.jktech.exkinilo.blocks;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockGearConveyorBelt;
import net.minecraft.src.game.block.BlockStairs;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.entity.EntityWaterMob;
import net.minecraft.src.game.entity.other.EntityItem;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;

import java.util.Random;

public class mixer extends Block {
    private final boolean isPowered;
    private World tp;
    public mixer(int id, Material material,boolean p) {
        super(id, material);
        this.isPowered=p;
        this.maxMetadata=5;
        this.bypassMaximumMetadataLimit=true;
            if (p){this.setTickOnLoad(true);
            setRequiresSelfNotify();}

        allocateTextures();
    }
    @Override
    public void onBlockPlaced(World world, int x, int y, int z, int blockFace) {
        super.onBlockPlaced(world, x, y, z, blockFace);
        world.setBlockMetadata(x,y,z,0);
    }
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int delay) {
        if (!world.multiplayerWorld) {
            this.checkIsPowered(world, x, y, z);
        }

    }
    @Override
    protected void allocateTextures() {
        this.addTexture("mixer_0",Face.NORTH,0);
        this.addTexture("mixer_0",Face.SOUTH,0,true,false);
        this.addTexture("mixer_1",Face.NORTH,1);
        this.addTexture("mixer_1",Face.SOUTH,1,true,false);
        this.addTexture("mixer_2",Face.NORTH,2);
        this.addTexture("mixer_2",Face.SOUTH,2,true,false);
        this.addTexture("mixer_3",Face.NORTH,3);
        this.addTexture("mixer_3",Face.SOUTH,3,true,false);
        this.addTexture("mixer_4",Face.NORTH,4);
        this.addTexture("mixer_4",Face.SOUTH,4,true,false);
        this.addTexture("base",Face._06,0);
        this.addTexture("base",Face._06,1);
        this.addTexture("base",Face._06,2);
        this.addTexture("base",Face._06,3);
        this.addTexture("mixed",Face._06,4);
        this.addTexture("base",Face._07,0);
        this.addTexture("base",Face._07,1);
        this.addTexture("base",Face._07,2);
        this.addTexture("base",Face._07,3);
        this.addTexture("base",Face._07,4);
        if (this.isPowered){
            this.addTexture("mixer_active_top", Face.TOP,0);
            this.addTexture("mixer_active_top", Face.BOTTOM,0);
            this.addTexture("mixer_active_f0",Face.WEST,0);
            this.addTexture("mixer_active_f0",Face.EAST,0);
            this.addTexture("mixer_active_top", Face.TOP,1);
            this.addTexture("mixer_active_top", Face.BOTTOM,1);
            this.addTexture("mixer_active_f0",Face.WEST,1);
            this.addTexture("mixer_active_f0",Face.EAST,1);
            this.addTexture("mixer_active_top", Face.TOP,2);
            this.addTexture("mixer_active_top", Face.BOTTOM,2);
            this.addTexture("mixer_active_f0",Face.WEST,2);
            this.addTexture("mixer_active_f0",Face.EAST,2);
            this.addTexture("mixer_active_top", Face.TOP,3);
            this.addTexture("mixer_active_top", Face.BOTTOM,3);
            this.addTexture("mixer_active_f0",Face.WEST,3);
            this.addTexture("mixer_active_f0",Face.EAST,3);
            this.addTexture("mixer_active_top", Face.TOP,4);
            this.addTexture("mixer_active_top", Face.BOTTOM,4);
            this.addTexture("mixer_active_f1",Face.WEST,4);
            this.addTexture("mixer_active_f1",Face.EAST,4);

        }
        else {
            this.addTexture("mixer_idle_top", Face.TOP,0);
            this.addTexture("mixer_idle_top", Face.BOTTOM,0);
            this.addTexture("mixer_idle_f0",Face.WEST,0);
            this.addTexture("mixer_idle_f0",Face.EAST,0);
            this.addTexture("mixer_idle_top", Face.TOP,1);
            this.addTexture("mixer_idle_top", Face.BOTTOM,1);
            this.addTexture("mixer_idle_f0",Face.WEST,1);
            this.addTexture("mixer_idle_f0",Face.EAST,1);
            this.addTexture("mixer_idle_top", Face.TOP,2);
            this.addTexture("mixer_idle_top", Face.BOTTOM,2);
            this.addTexture("mixer_idle_f0",Face.WEST,2);
            this.addTexture("mixer_idle_f0",Face.EAST,2);
            this.addTexture("mixer_idle_top", Face.TOP,3);
            this.addTexture("mixer_idle_top", Face.BOTTOM,3);
            this.addTexture("mixer_idle_f0",Face.WEST,3);
            this.addTexture("mixer_idle_f0",Face.EAST,3);
            this.addTexture("mixer_idle_top", Face.TOP,4);
            this.addTexture("mixer_idle_top", Face.BOTTOM,4);
            this.addTexture("mixer_idle_f1",Face.WEST,4);
            this.addTexture("mixer_idle_f1",Face.EAST,4);

        }
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        super.blockActivated(world, x, y, z, player);
        int meta=world.getBlockMetadata(x,y,z);
        int bucketwater= Item.bucketWater.itemID;
        int bucket=Item.bucketEmpty.itemID;
        int coal=Item.coal.itemID;
        int hand=player.getCurrentEquippedItem().itemID;
        switch (meta){
            // metadata:
            // [xx] 0 [xo] 1 [ox] 2 [oo] 3 [RR] 4
            case 0:
                if (hand==bucketwater) {
                    world.setBlockMetadataWithNotify(x,y,z,2);
                    player.inventory.mainInventory[player.inventory.currentItem]
                            =new ItemStack(bucket,1);
                }
                if (hand==coal) {
                    player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
                    world.setBlockMetadataWithNotify(x,y,z,1);
                }
                break;
            case 1:
                if (hand==bucketwater) {
                    world.setBlockMetadataWithNotify(x,y,z,3);
                    player.inventory.mainInventory[player.inventory.currentItem]
                            =new ItemStack(bucket,1);
                }
                else {
                    world.setBlockMetadataWithNotify(x,y,z,0);
                    float amp = 0.7F;
                    double motX = (double)(world.rand.nextFloat() * amp) + (double)(1.0F - amp) * 0.5;
                    double motY = (double)(world.rand.nextFloat() * amp) + (double)(1.0F - amp) * 0.5;
                    double motZ = (double)(world.rand.nextFloat() * amp) + (double)(1.0F - amp) * 0.5;
                    EntityItem item = new EntityItem(world, (double)x + motX, (double)y + motY+1, (double)z + motZ, new ItemStack(Item.coal,1));
                    item.delayBeforeCanPickup = 10;
                    world.entityJoinedWorld(item);}
                break;
            case 2:
                if (hand==coal) {
                    player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
                    world.setBlockMetadataWithNotify(x,y,z,3);
                }
                if (hand==bucket) {
                    world.setBlockMetadataWithNotify(x,y,z,0);
                    player.inventory.mainInventory[player.inventory.currentItem]
                            =new ItemStack(bucketwater,1);
                }

                break;
            case 3:
                if (hand==bucket) {
                    world.setBlockMetadataWithNotify(x,y,z,1);
                    player.inventory.mainInventory[player.inventory.currentItem]
                            =new ItemStack(bucketwater,1);
                }
                else {
                    world.setBlockMetadataWithNotify(x,y,z,2);
                    float amp = 0.7F;
                    double motX = (double)(world.rand.nextFloat() * amp) + (double)(1.0F - amp) * 0.5;
                    double motY = (double)(world.rand.nextFloat() * amp) + (double)(1.0F - amp) * 0.5;
                    double motZ = (double)(world.rand.nextFloat() * amp) + (double)(1.0F - amp) * 0.5;
                    EntityItem item = new EntityItem(world, (double)x + motX, (double)y + motY+1, (double)z + motZ, new ItemStack(Item.coal,1));
                    item.delayBeforeCanPickup = 10;
                    world.entityJoinedWorld(item);}

                break;
            case 4:
                if (hand==bucket) {
                    world.setBlockMetadataWithNotify(x,y,z,0);
                    player.inventory.mainInventory[player.inventory.currentItem]
                            =new ItemStack(clinilo.COOLANTBUCKET,1);
                }
                break;
        }
        return true;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        tp=world;
        if (!world.multiplayerWorld) {
            int metadata = world.getBlockMetadata(x, y, z);
            System.out.println("mixin");
            if (this.isPowered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
                world.setBlockAndMetadataWithNotify(x, y, z, clinilo.MXIDLE, metadata);
                return;
            }
            if (metadata==3&&world.rand.nextFloat()<0.5) {
                world.setBlockMetadataWithNotify(x,y,z,4);
            }
        }
    }
    private void checkIsPowered(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (this.isPowered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
        } else if (!this.isPowered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlockAndMetadataWithNotify(x, y, z, clinilo.MXACTIVE, metadata);
        }
    }
    @Override
    public int tickRate() {
        return 1;
    }

}
