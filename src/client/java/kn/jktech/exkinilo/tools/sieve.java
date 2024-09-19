package kn.jktech.exkinilo.tools;

import net.minecraft.src.game.item.EnumToolMaterial;
import net.minecraft.src.game.item.EnumTools;
import net.minecraft.src.game.item.ItemTool;
import net.minecraft.src.game.item.ItemToolSpade;

public class sieve extends ItemTool {
    public sieve(int id, int damage, EnumToolMaterial mat, EnumTools tool) {

        super(id, damage, mat, tool);
        maxStackSize=1;
    }
}
