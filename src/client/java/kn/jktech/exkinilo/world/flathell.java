package kn.jktech.exkinilo.world;

import net.minecraft.src.client.gui.IProgressUpdate;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockPortal;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.chunk.Chunk;
import net.minecraft.src.game.level.chunk.IChunkLoader;
import net.minecraft.src.game.level.chunk.IChunkProvider;

public class flathell  implements IChunkProvider {
    private World worldObj;

    public flathell(World world, long seed, boolean structures) {
        this.worldObj = world;
    }

    public void generateTerrain(int x, int y, int z, short[] blocks) {
        if (y < 4) {
            for (int i = 0; i < 4096; i++) {
                blocks[i] = 87;

            }
        }

        if (y == 3) {
            for (int i = 0; i < 256; i++) {
                blocks[((i & 15) << 8) + (i & 240) + 15] = 87;
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
