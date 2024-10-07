package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.game.MathHelper;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.StepSound;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public float yOffset;
    @Shadow
    public abstract boolean isSneaking();
    @Shadow
    public World worldObj;
    @ModifyVariable(method = "moveEntity", at = @At("STORE"), ordinal = 0)
    public StepSound moveEntity(StepSound sound){
        int multip2 = MathHelper.floor_double(this.posX);
        int multip3 = MathHelper.floor_double(this.posY - (double)this.yOffset + (this.isSneaking() ? 0.03 : -0.03));
        int multip4 = MathHelper.floor_double(this.posZ);
        int id=this.worldObj.getBlockId(multip2, multip3, multip4);
        if (id== clinilo.PEBBLES){
            int id2=this.worldObj.getBlockId(multip2, multip3-1, multip4);
            sound = Block.blocksList[id2].stepSound;
            return sound;
        }
        return sound;


    }
}
