package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.blocks.mixer;
import kn.jktech.exkinilo.blocks.vaporlek;
import net.minecraft.src.client.renderer.RenderBlocks;
import net.minecraft.src.client.renderer.Tessellator;
import net.minecraft.src.client.renderer.block.icon.Icon;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBlocks.class)
public abstract class mixinRenderBlock {
    @Shadow
    private boolean enableAO;
    @Shadow
    private boolean renderAllFaces;
    @Shadow
    public abstract boolean renderStandardBlock(Block block, int x, int y, int z);
    @Shadow
    private IBlockAccess blockAccess;
    @Shadow
    public abstract boolean renderStandardBlockWithColorMultiplier(Block block, int x, int y, int z, float r, float g, float b);
    @Inject(method = "renderBlockByRenderType",at=@At("HEAD"),cancellable = true)
    public void renderBlockByRenderType(Block block, int x, int y, int z, CallbackInfoReturnable ci){
        if (block instanceof mixer){
            ci.setReturnValue(renderMixerBlock(block,x,y,z));
            ci.cancel();
        }else if (block instanceof vaporlek){
            ci.setReturnValue(renderinsideoutBlock(block,x,y,z));
            ci.cancel();
        }
    }



    public boolean renderMixerBlock(Block block, int x, int y, int z) {
        int color = block.colorMultiplier(this.blockAccess, x, y, z);
        float r = (float)(color >> 16 & 0xFF) / 255.0F;
        float g = (float)(color >> 8 & 0xFF) / 255.0F;
        float b = (float)(color & 0xFF) / 255.0F;
        this.renderStandardBlockWithColorMultiplier(block, x, y, z, r, g, b);
        renderMixerFaces(block,x,y,z,r,g,b);
        return true;
    }
    public boolean renderinsideoutBlock(Block block, int x, int y, int z) {
        int color = block.colorMultiplier(this.blockAccess, x, y, z);
        float r = (float)(color >> 16 & 0xFF) / 255.0F;
        float g = (float)(color >> 8 & 0xFF) / 255.0F;
        float b = (float)(color & 0xFF) / 255.0F;
        this.renderStandardBlockWithColorMultiplier(block, x, y, z, r, g, b);
        renderinsideFaces(block,x,y,z,r,g,b);
        if (blockAccess.getBlockMetadata(x,y,z)>=5&&
                blockAccess.getBlockMetadata(x,y,z)<=9)
            ((RenderBlocks)(Object)this).renderTopFace(block, (double)x, (double)y-.5, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 6));
        return true;
    }
    public void renderinsideFaces(Block block, int x, int y, int z, float r, float g, float b){
        this.enableAO = false;
        Tessellator instance = Tessellator.instance;
        float var11 = 1.0F;
        float var14 = var11 * r;
        float var15 = var11 * g;
        float var16 = var11 * b;
        float var26 = block.getBlockBrightness(this.blockAccess, x, y+1, z);
        float lightFloatx = var26;





            instance.setColorOpaque_F(var14 * lightFloatx, var15 * lightFloatx, var16 * lightFloatx);

            if(
            block.shouldSideBeRendered(this.blockAccess, x, y+1, z, 0)
            ||block.shouldSideBeRendered(this.blockAccess, x, y-1, z, 1)
            ||block.shouldSideBeRendered(this.blockAccess, x, y, z+1, 2)
            ||block.shouldSideBeRendered(this.blockAccess, x, y, z-1, 3)
            ||block.shouldSideBeRendered(this.blockAccess, x+1, y, z, 4)
            ||block.shouldSideBeRendered(this.blockAccess, x-1, y, z, 5)
            ) {
                ((RenderBlocks) (Object) this).renderTopFace(
                        block, (double) x, (double) y - 1, (double) z, block.getBlockTexture(this.blockAccess, x, y, z, 1)
                );
                ((RenderBlocks) (Object) this).renderBottomFace(block, x, y + 1, z, block.getBlockTexture(this.blockAccess, x, y, z, 0));

                ((RenderBlocks) (Object) this).renderWestFace(
                        block, (double) x, (double) y, (double) z - 1, block.getBlockTexture(this.blockAccess, x, y, z, 3));

                ((RenderBlocks) (Object) this).renderEastFace(
                        block, (double) x, (double) y, (double) z + 1, block.getBlockTexture(this.blockAccess, x, y, z, 2));

                ((RenderBlocks)(Object)this).renderNorthFace(block,
                        (double)x+1, (double)y, (double)z,block.getBlockTexture(this.blockAccess, x, y, z, 4));

                ((RenderBlocks)(Object)this).renderSouthFace(block,
                        (double)x-1, (double)y, (double)z,block.getBlockTexture(this.blockAccess, x, y, z, 5));
            }
        }

    public void renderMixerFaces(Block block, int x, int y, int z, float r, float g, float b){
        this.enableAO = false;
        Tessellator instance = Tessellator.instance;
        float var11 = 1.0F;
        float var14 = var11 * r;
        float var15 = var11 * g;
        float var16 = var11 * b;
        float var26 = block.getBlockBrightness(this.blockAccess, x, y+1, z);
        float lightFloatx = var26;


        int meta=blockAccess.getBlockMetadata(x,y,z);

        if (meta!=3&&(
        block.shouldSideBeRendered(this.blockAccess, x+1, y, z, 5)
        ||this.renderAllFaces||
        block.shouldSideBeRendered(this.blockAccess, x-1, y, z, 4))){




        instance.setColorOpaque_F(var14 * lightFloatx, var15 * lightFloatx, var16 * lightFloatx);
            Icon seven=block.getBlockTexture(this.blockAccess, x, y, z, 7);
            ((RenderBlocks)(Object)this).renderTopFace(
                    block, (double)x, (double)y-(meta==4?.5625:1), (double)z, block.getBlockTexture(this.blockAccess, x, y, z, meta==4?6:7)
            );
            ((RenderBlocks)(Object)this).renderWestFace(
                    block,(double)x, (double)y, (double)z-.5,   seven);
            ((RenderBlocks)(Object)this).renderEastFace(
                    block,(double)x, (double)y, (double)z+.5, seven);
            ((RenderBlocks)(Object)this).renderWestFace(
                    block,(double)x, (double)y, (double)z-1, seven);
            ((RenderBlocks)(Object)this).renderEastFace(
                    block,(double)x, (double)y, (double)z+1,seven);
            ((RenderBlocks)(Object)this).renderBottomFace(block,x,y+1,z,block.getBlockTexture(this.blockAccess, x, y, z, 0));

            if(block.shouldSideBeRendered(this.blockAccess, x-1, y, z, 4)){
                ((RenderBlocks)(Object)this).renderNorthFace(block,
                        (double)x+1, (double)y, (double)z,block.getBlockTexture(this.blockAccess, x, y, z, 4));
            }if(block.shouldSideBeRendered(this.blockAccess, x+1, y, z, 5)){
                ((RenderBlocks)(Object)this).renderSouthFace(block,
                        (double)x-1, (double)y, (double)z,block.getBlockTexture(this.blockAccess, x, y, z, 5));
            }
        }
    }
}
