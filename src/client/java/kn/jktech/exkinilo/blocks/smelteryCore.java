package kn.jktech.exkinilo.blocks;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.game.block.*;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.level.World;

import java.util.Random;

public class smelteryCore extends Block {
    public smelteryCore(int id, Material material) {
        super(id, material);
        this.setTickOnLoad(true);
        setRequiresSelfNotify();
    }

    @Override
    protected void allocateTextures() {

        this.addTexture("base", Face.ALL);
        this.addTexture("smeltery core", Face.TOP);

    }

    @Override
    public int tickRate() {
        return 50;
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.multiplayerWorld&&world.isDaytime()&&world.getBlockLightValue(x,y+1,z)>4) {
        //3
        //2
        //3
        int l1=0;
        int l2=0;
        int l3=0;
        System.out.println("trying to smelt");
        int [][]pos={
                {-1,-1},{-1,0},{-1,1},

                {0,-1},        {0,1},

                {1,-1}, {1,0}, {1,1}
        };
        for (int i = 0; i < 8; i++) {
            if (world.getBlockId(x+pos[i][0],y,z+pos[i][1])== clinilo.COMPRESSEDCOAL){
                l1+=1;
                if(l1==3)break;
            }
        }for (int i = 0; i < 8; i++) {
            if (world.getBlockId(x+pos[i][0],y-1,z+pos[i][1])== clinilo.COMPRESSEDCOAL){
                l2+=1;
                if(l2==2)break;
            }
        }for (int i = 0; i < 8; i++) {
            if (world.getBlockId(x+pos[i][0],y-2,z+pos[i][1])== clinilo.COMPRESSEDCOAL){
                l3++;
                if(l3==3)break;
            }
        }
        System.out.println("l1: "+l1+" l2: "+l2+" l3: "+l3);
        if (l1==3&&l2==2&&l3==3) if (world.getBlockId(x,y-1,z)==Block.stone.blockID){
            if(world.rand.nextFloat()>0.1) {

                world.playSoundEffect(x,y,z,"kinilo.smelter", 0.8F, 1.0F);
                world.setBlock(x,y-1,z,Block.lavaStill.blockID);
            }
        }
    }}
}
