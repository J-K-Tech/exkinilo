package kn.jktech.exkinilo.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.client.inventory.IInventory;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockContainer;
import net.minecraft.src.game.block.BlockFurnace;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.tileentity.TileEntity;
import net.minecraft.src.game.block.tileentity.TileEntityFurnace;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.recipe.FurnaceRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityFurnace.class)
public abstract class furnaceMixin extends TileEntity implements IInventory {
    @Inject(method = "smeltItem",at=@At(value = "INVOKE",shift = At.Shift.AFTER,
    target =
"Lnet/minecraft/src/game/recipe/FurnaceRecipes;getSmeltingResult(II)Lnet/minecraft/src/game/item/ItemStack;"))

    public void smeltItem(CallbackInfo ci, @Local ItemStack is){
        if (is.itemID==new ItemStack(Block.clay).itemID){
            int [][] pos={
                            {-1,0},

                    {0,-1},        {0,1},

                             {1,0}
            };
            for (int i = 0; i < pos.length; i++) {
                if (worldObj.getBlockId(xCoord+pos[i][0],yCoord,zCoord+pos[i][1])== clinilo.VAPORCOLEK){
                    int m=worldObj.getBlockMetadata(xCoord+pos[i][0],yCoord,zCoord+pos[i][1])+1;
                    if (m==15) continue;

                    worldObj.setBlockMetadata(xCoord+pos[i][0],yCoord,zCoord+pos[i][1],
                    m+1);

                }

            }
        }
    }

}
