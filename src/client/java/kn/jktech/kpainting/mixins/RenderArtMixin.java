package kn.jktech.kpainting.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.renderer.GLAllocation;
import net.minecraft.src.client.renderer.RenderEngine;
import net.minecraft.src.client.renderer.entity.Render;
import net.minecraft.src.client.renderer.entity.RenderPainting;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.EnumArt;
import net.minecraft.src.game.entity.other.EntityPainting;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import kn.jktech.exkinilo.clinilo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static kn.jktech.kpainting.clipaint.PAINTINGS;

@Mixin(RenderPainting.class)
public abstract class RenderArtMixin extends Render {
    @Shadow
    private void renderPainting(EntityPainting painting, int sizeX, int sizeY){}
    @Shadow
    protected void renderLivingLabel(Entity entity, String label, double x, double y, double z, int distMax) {}
    @Overwrite
    public void renderPainting(EntityPainting painting, double x, double y, double z, float rotation, float deltaTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        EnumArt artData = painting.art;
        this.loadTexture( artData.title );
        float scale = 0.0625F;
        GL11.glScalef(scale, scale, scale);
        this.renderPainting(painting, artData.sizeX, artData.sizeY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        if (Minecraft.isDebugInfoEnabled()) {
            this.renderLivingLabel(painting, artData.title, x, y, z, 64);
        }
    }
    @Override
    protected void loadTexture(String s) {
        RenderEngine re = this.renderManager.renderEngine;
        Integer i=(Integer)((RenderEngineAccessor)re).getTextureMap().get("/art/" +s+ ".png");
        if (i!=null) {re.bindTexture(re.getTexture(s));}
        else{
            File f=new File(PAINTINGS,s+".png");
            if (f.exists()){
            int id = GLAllocation.generateTextureNames();
                BufferedImage img= null;
                try {
                    img = ImageIO.read(f);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                re.setupTextureExt(img, id, false, false);

            ((RenderEngineAccessor)re).getTextureMap().put(s, id);
            re.bindTexture(id);}

        }
    }
}