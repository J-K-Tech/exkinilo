package kn.jktech.exkinilo.world;

import kn.jktech.exkinilo.clinilo;
import kn.jktech.exkinilo.perlin;
import net.minecraft.src.client.gui.IProgressUpdate;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.chunk.Chunk;
import net.minecraft.src.game.level.chunk.ChunkProviderFlatworld;
import net.minecraft.src.game.level.chunk.IChunkLoader;
import net.minecraft.src.game.level.chunk.IChunkProvider;
import net.minecraft.src.game.level.map.MapGenCaves;
import net.minecraft.src.game.level.noisegen.NoiseGenerator;
import net.minecraft.src.game.level.noisegen.NoiseGeneratorPerlin;

import java.util.BitSet;

public class noodle implements IChunkProvider {
    private World worldObj;

    public noodle(World world, long seed, boolean structures) {
        this.worldObj = world;
    }
    public noodle (World w){this.worldObj=w;}

    public void generateTerrain(int x, int y, int z, short[] blocks) {
            perlin noice = new perlin(worldObj.getWorldSeed(),1f/50f);
            int block = this.worldObj.getRegisteredDimensionID() == 0 ? 1 : 87;
    int blockup =clinilo.SWARD;
        int blockmid =clinilo.SWARD;
        boolean ybridge=y==4;
        boolean xbridge=x%3==0;
        boolean zbridge=z%10==0;
        float tr=-.3f;
            for (byte xx = 0; xx < 16; xx++) {
                for (byte yy = 0; yy < 16; yy++) {
                    for (byte zz = 0; zz < 16; zz++) {if (blocks[xx << 8 | zz << 4 | yy] ==0)
                        {


                            if (ybridge && xbridge && xx < 6 && yy >= 8 && yy < 13) {
                                if (yy == 8)
                                    blocks[xx << 8 | zz << 4 | yy] = (short) (xx == 0 || xx == 5 ? 217 : 146);
                                else blocks[xx << 8 | zz << 4 | yy] = (short) (0);
                            } else {
                                double v = noice.noise(((double) (((16 * x)) + xx)), ((double) ((y * 16) + yy)), ((double) ((16 * z) + zz)));

                                if (v <= tr) {
                                    double val[] = {
                                            v,
                                            noice.noise(((double) (((16 * x)) + xx)), ((double) ((y * 16) + yy + 1)), ((double) ((16 * z) + zz))),
                                            noice.noise(((double) (((16 * x)) + xx)), ((double) ((y * 16) + yy + 2)), ((double) ((16 * z) + zz))),
                                    };


                                    if (val[1] > tr && val[2] > tr) {

                                    } else
                                    if (val[1] <= tr && val[2] > tr) {
                                        blocks[xx << 8 | zz << 4 | yy] = (short) (blockup);
                                        blocks[xx << 8 | zz << 4 | (yy>0?yy-1:yy)] = (short) (blockmid);
                                        blocks[xx << 8 | zz << 4 | (yy>1?yy-2:yy)] = (short) (blockmid);
                                    } else {
                                        blocks[xx << 8 | zz << 4 | yy] = (short) (block);
                                    }

                                } else {
                                    if (zbridge && xbridge && y <= 4 && xx < 6) {

                                        if (ybridge && yy < 8) {
                                            switch (yy) {
                                                case 7:
                                                    blocks[xx << 8 | zz << 4 | yy] = (short) (zz > 5 ? 0 : 146);
                                                    break;
                                                case 6:
                                                    blocks[xx << 8 | zz << 4 | yy] = (short) (xx == 0 || xx == 5 || zz > 4 || zz < 1
                                                            ? 0 : 146);
                                                    break;
                                                default:
                                                    blocks[xx << 8 | zz << 4 | yy] = (short) (xx < 2 || xx > 3 || zz > 3 || zz < 2
                                                            ? 0 : 146);
                                                    break;

                                            }
                                        } else if (!ybridge) {
                                            blocks[xx << 8 | zz << 4 | yy] = (short) (xx < 2 || xx > 3 || zz > 3 || zz < 2
                                                    ? 0 : 146);

                                        }
                                    } else blocks[xx << 8 | zz << 4 | yy] = (short) (0);
                                }
                            }

                        }
                }
            }
        }

    }










    @Override
    public Chunk prepareChunk(int x, int y, int z) {
        return this.provideChunk(x, y, z);
    }

    @Override
    public void eatChunk(Chunk chunk, int x, int y, int z) {
    }

    @Override
    public void markChunkForPopulation(Chunk chunk, int x, int y, int z) {
    }

    @Override
    public Chunk provideChunkSafe(int x, int y, int z) {
        System.out.println("How did I get here? provideChunkSafe call for " + this + ", this should not happen!");
        return null;
    }

    @Override
    public Chunk provideChunk(int x, int y, int z) {
        short[] blocks = new short[4096];
        Chunk chunk = new Chunk(this.worldObj, blocks, x, y, z);
        this.generateTerrain(x, y, z, blocks);
        chunk.prepareLighting();
        return chunk;
    }

    @Override
    public boolean chunkExists(int x, int y, int z) {
        return true;
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int x, int y, int z) {
    }

    @Override
    public boolean saveChunks(boolean arg1, IProgressUpdate progressUpdate) {
        return true;
    }

    @Override
    public IChunkLoader getChunkLoader() {
        return null;
    }

    @Override
    public boolean unload100OldestChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "RandomLevelSource";
    }
}
