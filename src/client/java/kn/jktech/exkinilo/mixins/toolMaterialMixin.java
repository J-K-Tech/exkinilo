package kn.jktech.exkinilo.mixins;

import net.minecraft.src.game.item.EnumToolMaterial;
import net.minecraft.src.game.item.EnumTools;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
@Mixin(EnumToolMaterial.class)
public class toolMaterialMixin {
    @Shadow
    @Final
    @Mutable
    private static EnumToolMaterial[]$VALUES;
private static final EnumToolMaterial HAMMER=ToolsMixin$add("HAMMER",2,2048,3.f,1);
    @Invoker("<init>")
    public static EnumToolMaterial ToolsMixin$init(String name, int ordinal,int hvl,int uses,float eff,int dmg){throw new AssertionError();}
    private static EnumToolMaterial ToolsMixin$add(String name,int hvl,int uses,float eff,int dmg){
        ArrayList<EnumToolMaterial> values = new ArrayList<>(Arrays.asList($VALUES));
        EnumToolMaterial value = ToolsMixin$init(name, values.get(values.size() - 1).ordinal() + 1
        ,hvl,uses,eff,dmg);
        values.add(value);
        $VALUES = values.toArray(new EnumToolMaterial[0]);
        return value;
    }
}
