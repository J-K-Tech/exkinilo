package kn.jktech.exkinilo.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.gui.GuiScreen;
import net.minecraft.src.client.player.EntityPlayerSP;
import net.minecraft.src.game.achievements.AchievementList;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EntityPlayerSP.class)
public abstract class playermixin  {
    public int goingtodim=0;
    @Shadow
    public World worldObj;
    @Shadow
    public Minecraft mc;
    @Shadow
    protected Random rand;
    @Shadow
    public Entity ridingEntity;
    @Shadow
    public abstract void mountEntity(Entity entity);
    public float timeInPortalcustom = 0.f;
    public boolean incustomportal=false;
    public int timeUntilPortalcustom=0;


    @Inject(method = "onLivingUpdate",at=@At("HEAD"))
    public void onLivingUpdate(CallbackInfo ci) {
if (this.incustomportal){
        if (!this.worldObj.multiplayerWorld && this.ridingEntity != null) {
            this.mountEntity((Entity)null);
        }

        if (this.mc.currentScreen != null) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }

        if (this.timeInPortalcustom == 0.0F) {
            this.mc.sndManager.playSoundFX("portal.trigger", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
        }

        this.timeInPortalcustom += 0.0125F;
        if (this.timeInPortalcustom >= 1.0F) {
            this.timeInPortalcustom = 1.0F;
            if (!this.worldObj.multiplayerWorld) {
                this.timeUntilPortalcustom = 10;
                this.mc.sndManager.playSoundFX("portal.travel", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
                this.mc.usePortal();
//                this.triggerAchievement(AchievementList.portal);
            }
        }

        this.incustomportal = false;
    }
     else {
        if (this.timeInPortalcustom > 0.0F) {
            this.timeInPortalcustom -= 0.05F;
        }

        if (this.timeInPortalcustom < 0.0F) {
            this.timeInPortalcustom = 0.0F;
        }
    }

        if (this.timeUntilPortalcustom > 0) {
        this.timeUntilPortalcustom--;
    }
    }

}
