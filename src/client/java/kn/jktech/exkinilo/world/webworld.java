package kn.jktech.exkinilo.world;

import net.minecraft.src.game.level.WorldProvider;
import net.minecraft.src.game.level.chunk.ChunkProviderHell;
import net.minecraft.src.game.level.chunk.IChunkProvider;
import org.spongepowered.asm.mixin.Shadow;

public class webworld extends WorldProvider {
    public webworld(){
        this.worldType=1;
    }

    @Override
    protected void generateLightBrightnessTable() {
        float var1 = 0.3F;

        for (int var2 = 0; var2 <= 15; var2++) {
            float var3 = 1.0F - (float)var2 / 15.0F;
            this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }@Override
    public IChunkProvider getChunkProvider() {
        return new noodle(this.worldObj);
    }
    @Shadow
    public static WorldProvider getProviderForDimension(int arg0) {

        return new webworld();
    }

    @Override
    public float calculateCelestialAngle(long arg1, float arg3) {
        return 0.5F;
    }

    @Override
    public float getCloudHeight() {
        return 50000.0F;
    }
}
