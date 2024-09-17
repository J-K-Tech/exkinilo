package kn.jktech.exkinilo.blocks;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockGearBaseGate;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.level.World;

import java.util.Random;

public class smelteryCore extends Block {
    public smelteryCore(int id, Material material) {
        super(id, material);
        this.setTickOnLoad(true);
    }

    @Override
    public int tickRate() {
        return 1;
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.multiplayerWorld) {
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
        for (int i = 0; i < 7; i++) {
            if (world.getBlockId(x+pos[i][0],y,z+pos[i][1])== clinilo.COMPRESSEDCOAL){
                l1+=1;
                if(l1==3)break;
            }
        }for (int i = 0; i < 7; i++) {
            if (world.getBlockId(x+pos[i][0],y-1,z+pos[i][1])== clinilo.COMPRESSEDCOAL){
                l2+=1;
                if(l2==2)break;
            }
        }for (int i = 0; i < 7; i++) {
            if (world.getBlockId(x+pos[i][0],y-2,z+pos[i][1])== clinilo.COMPRESSEDCOAL){
                l3++;
                if(l3==3)break;
            }
        }
        System.out.println(l1);
            System.out.println(l2);
            System.out.println(l3);
        if (l1==3&&l2==2&&l3==3) if (world.getBlockId(x,y-1,z)==Block.stone.blockID){
            if(world.rand.nextFloat()>0.01) {

                System.out.println("smelting");
                world.setBlock(x,y-1,z,Block.lavaStill.blockID);
            }
        }
    }}
}
