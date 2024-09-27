package kn.jktech.exkinilo.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.src.client.gui.GuiButton;
import net.minecraft.src.client.gui.GuiCreateWorld;
import net.minecraft.src.client.gui.StringTranslate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCreateWorld.class)
public class guicreateworldmixin {
    @Shadow
    private int gentype;
    @Shadow
    private GuiButton worldTypeButton;
    @Shadow
    public String worldtypeString;
    private int worldtype=3;
    @Shadow
    private String[] worldtypes = new String[worldtype];
    @Inject(method = "func_35363_g",at=@At(value = "TAIL",shift = At.Shift.BEFORE))
    private void func_35363_g(CallbackInfo ci,@Local StringTranslate stringtranslate){
        onaddworldtype("noodle");
    }
    public void onaddworldtype(String key){
        System.out.println("REGISTERING WORLD TYPE "+key);
        String[] wt=new String[++this.worldtype];
        int i=0;
        for (String s : this.worldtypes){
            System.out.println("EXISTING WORLD TYPES "+s);
            wt[i++]=s;
        }
        wt[i]=key;
        this.worldtypes=wt;
        System.out.println("REGISTERED "+this.worldtypes[i]+" WORLD TYPE");
    }

    @Inject(method = "actionPerformed",at = @At(value = "TAIL"))
    public void onaddworldtype(GuiButton guibutton,CallbackInfo ci){
        if (guibutton.id == 5) {

            this.gentype++;
            this.gentype %= worldtype;
            this.worldTypeButton.displayString = this.worldtypeString + this.worldtypes[this.gentype];
            System.out.println(this.gentype);
    }}
}
