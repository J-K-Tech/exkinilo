package kn.jktech.exkinilo.mixins;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.item.EnumTools;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemTool.class)
public class itemToolMixin extends Item {
    @Shadow
    protected EnumTools toolType;
    protected itemToolMixin(int id) {
        super(id);
    }
    @Inject(method = "isToolEffectiveOnBlock",at=@At("HEAD"),cancellable = true)
    public void isToolEffectiveOnBlock(Block block, CallbackInfoReturnable ci) {
        int i=block.blockID;
        if (i==2||i==3||i==12||i==13) {
            if (toolType.equals(EnumTools.valueOf("SIEVE"))) {
                ci.setReturnValue(true);
                ci.cancel();
            }
        }
    }
}
