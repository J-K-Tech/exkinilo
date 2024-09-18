package kn.jktech.exkinilo.mixins;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.item.EnumTools;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Unique
@Mixin(EnumTools.class)
public abstract class ToolsMixin {
    @Shadow
    @Final
    @Mutable
    private static EnumTools[]$VALUES;
    private static final EnumTools SIEVE=ToolsMixin$add("SIEVE");
    private static final EnumTools HAMMER=ToolsMixin$add("HAMMER");
            @Invoker("<init>")
    public static EnumTools ToolsMixin$init(String name, int ordinal){throw new AssertionError();}
    private static EnumTools ToolsMixin$add(String name){
        ArrayList<EnumTools> values = new ArrayList<>(Arrays.asList($VALUES));
        EnumTools value = ToolsMixin$init(name, values.get(values.size() - 1).ordinal() + 1);
        values.add(value);
        $VALUES = values.toArray(new EnumTools[0]);
        return value;
    }
}
