package kn.jktech.exkinilo.mixins;

import net.minecraft.src.client.player.EntityPlayerSP;
import net.minecraft.src.game.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayerSP.class)
public class playermixin  {
    public int goingtodim=0;
    public boolean incustomportal=false;

}
