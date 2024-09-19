package kn.jktech.exkinilo.mixins;

import com.fox2code.foxloader.registry.RegisteredBlock;
import kn.jktech.exkinilo.clinilo;
import kn.jktech.exkinilo.tools.sieve;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.EnumTools;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.item.ItemTool;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.src.game.block.Block.*;

@Mixin(Block.class)
public class blockMixin implements RegisteredBlock {
    @Shadow
    protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack itemstack){}

    public Object[] sieving(int id,World world){
        float f=world.rand.nextFloat();
        switch (id){
            case 2:return new Object[]{gravel};
            case 3:return new Object[]{gravel};
            case 12:
                if (f < 0.3) return new Object[]{clay, oreGold};
                return new Object[]{clay};
            case 13:
                if (f < 0.5) {
                    if (f > 0.1) return new Object[]{sand, (Integer)clinilo.ROCK};
                    return new Object[]{sand, (Integer)clinilo.MAGNETITE};
                }
                return new Object[]{sand};
            default:return null;
        }
    }
    public Object[] hammering(int id,World world){
        float f=world.rand.nextFloat();
        if (id==clinilo.PEBBLESTONE.getRegisteredBlockId())return new Object[]{new ItemStack(dirt,8)};
        if (id==clinilo.BOULDERSTONE.getRegisteredBlockId())return new Object[]{new ItemStack(cobblestone,4)};
        switch (id){
            case 4:return new Object[]{new ItemStack(clinilo.PEBBLESTONE.asRegisteredItem().getRegisteredItemId(),4)};
            case 1:return new Object[]{new ItemStack(clinilo.BOULDERSTONE.asRegisteredItem().getRegisteredItemId(),4)};
            case 79:return new Object[]{new ItemStack(snowBlock,4)};
            case 265:return new Object[]{new ItemStack(snowBlock,16)};
            default:return null;
        }
    }
    @Inject(method="doBlockDropEvent",at= @At(value = "HEAD"),cancellable = true)
    public void doBlockDropEventMix(World world, int x, int y, int z, EntityPlayer player, int metadata, CallbackInfo ci) {
        int myid=((Block)(Object)this).blockID;
        Object[] drops={};
        ItemStack it=player.getCurrentEquippedItem();
        if (it!=null) if (it.getItem()instanceof sieve){
        if(((ItemTool)it.getItem()).getToolType()== EnumTools.valueOf("SIEVE")) drops=sieving(myid,world);



        else if(((ItemTool)it.getItem()).getToolType()== EnumTools.valueOf("HAMMER")) drops=hammering(myid,world);
        if (drops!=null){
        for (int i = 0; i < drops.length; i++) {
            if (drops[i] instanceof Block)this.dropBlockAsItem_do(world, x, y, z, new ItemStack((Block)drops[i], 1));
            else if (drops[i] instanceof Item)this.dropBlockAsItem_do(world, x, y, z, new ItemStack((Item)drops[i], 1));
            else if (drops[i] instanceof Integer)this.dropBlockAsItem_do(world, x, y, z, new ItemStack((Integer) drops[i], 1));
            else if (drops[i] instanceof ItemStack)this.dropBlockAsItem_do(world, x, y, z,(ItemStack) drops[i]);
        }
        if (drops.length!=0){
        ci.cancel();
        return;}
        }

        }
        if (myid==2)
        {
            float c = world.rand.nextFloat();
            if (c <= 0.05) {
                c = world.rand.nextFloat();
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(dirt, 1));
                if (c > 0.75) {
                    this.dropBlockAsItem_do(world, x, y, z, new ItemStack(saplingFir, 1));
                    ci.cancel();
                } else if (c < .76 && c > .5) {
                    this.dropBlockAsItem_do(world, x, y, z, new ItemStack(sapling, 1));
                    ci.cancel();
                } else if (c < .51 && c > .25) {
                    this.dropBlockAsItem_do(world, x, y, z, new ItemStack(saplingCherry, 1));
                    ci.cancel();
                } else {
                    this.dropBlockAsItem_do(world, x, y, z, new ItemStack(saplingSpruce, 1));
                    ci.cancel();
                }
            }
        }
    }
}
