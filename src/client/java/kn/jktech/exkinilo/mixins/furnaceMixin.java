package kn.jktech.exkinilo.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.client.inventory.IInventory;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockContainer;
import net.minecraft.src.game.block.BlockFurnace;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.tileentity.TileEntity;
import net.minecraft.src.game.block.tileentity.TileEntityBlastFurnace;
import net.minecraft.src.game.block.tileentity.TileEntityFurnace;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.recipe.FurnaceRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityFurnace.class)
public abstract class furnaceMixin extends TileEntity implements IInventory {
    @Shadow
    public abstract boolean canSmelt();
    @Shadow
    public ItemStack[] slotItemStacks;

    @Overwrite
    public void smeltItem(){
        if (this.canSmelt()) {
            System.out.println("hihi");
            ItemStack result = FurnaceRecipes.instance
                    .getSmeltingResult(this.slotItemStacks[0].itemID, this.slotItemStacks[0].itemDamage);

            if (this.slotItemStacks[2] == null) {
                this.slotItemStacks[2] = result.copy();
            } else if (this.slotItemStacks[2].itemID == result.itemID) {
                this.slotItemStacks[2].stackSize++;
            }

            if (this.slotItemStacks[0].getItem() != Item.bucketMilk) {
                this.slotItemStacks[0].stackSize--;
            } else {
                this.slotItemStacks[0] = new ItemStack(Item.bucketEmpty, 1);
            }

            if (this.slotItemStacks[0].stackSize <= 0) {
                this.slotItemStacks[0] = null;
            }
        if (this.slotItemStacks[2].itemID==new ItemStack(Block.hardenedClay).itemID){
            int [][] pos={
                            {-1,0},

                    {0,-1},        {0,1},

                             {1,0}
            };
            for (int i = 0; i < pos.length; i++) {
                if (worldObj.getBlockId(xCoord+pos[i][0],yCoord,zCoord+pos[i][1])== clinilo.VAPORCOLEK){
                    int m=worldObj.getBlockMetadata(xCoord+pos[i][0],yCoord,zCoord+pos[i][1])+1;
                    if (m==15) {
                        worldObj.playSoundEffect(xCoord,yCoord,zCoord,"kinilo.mixer.mixer_pour", 3.8F, 1.0F);
                        continue;
                    }

                    worldObj.setBlockMetadata(xCoord+pos[i][0],yCoord,zCoord+pos[i][1],
                    m+1);

                }

            }
        }}
    }

}
