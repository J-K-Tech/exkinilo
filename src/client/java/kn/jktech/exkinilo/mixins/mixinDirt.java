package kn.jktech.exkinilo.mixins;

import com.fox2code.foxloader.registry.RegisteredBlock;
import kn.jktech.exkinilo.clinilo;
import kn.jktech.exkinilo.tools.sieve;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Block.class)
public abstract class mixinDirt implements RegisteredBlock {
    @Inject(method="dropBlockAsItemWithChance",cancellable = true,at= @At(remap = false,value = "INVOKE",target =
            "Lnet/minecraft/src/game/block/Block;dropBlockAsItem_do(Lnet/minecraft/src/game/level/World;IIILnet/minecraft/src/game/item/ItemStack;)V"))
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance,CallbackInfo ci) {
            if (world.getClosestPlayer((double) x,(double) y,(double) z,16.).getCurrentEquippedItem()!=null){
            if (world.getClosestPlayer((double) x,(double) y,(double) z,16.).getCurrentEquippedItem().getItem() instanceof sieve){
                System.out.println(world.getBlockId(x,y,z));

                switch (((Block)(Object)this).idDropped(metadata, world.rand)){
                    case 3:
                        this.dropBlockAsItem_do(world, x, y, z, new ItemStack(13,1));
                        ci.cancel();
                        break;
                    case 12:
                        this.dropBlockAsItem_do(world, x, y, z, new ItemStack(82,1));
                        ci.cancel();

                        break;
                    case 13:
                        this.dropBlockAsItem_do(world, x, y, z, new ItemStack(12,1));

                        if (world.rand.nextFloat()<0.2) this.dropBlockAsItem_do(world, x, y, z, new ItemStack(clinilo.ROCK,1));
                        ci.cancel();

                        break;
                    default:break;
                }

            }}

    }
    @Shadow
    protected abstract void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack itemstack);
    }
