package kn.jktech.exkinilo.mixins;

import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.WorldInfo;
import net.minecraft.src.game.level.WorldProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class worldmixin {
    @Shadow
    @Final
    public WorldProvider worldProvider;
@Redirect(method = "init()V",at = @At(value = "FIELD",target = "Lnet/minecraft/src/game/level/World;worldProvider:Lnet/minecraft/src/game/level/WorldProvider", opcode = Opcodes.PUTFIELD,ordinal =2))
    public void worldtype(World self, WorldProvider worldProvider){
    this.worldProvider=WorldProvider.getProviderForDimension(self.worldInfo.getDimension());
}
}
