package kn.jktech.exkinilo.blocks;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockStairs;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;

public class Firewood extends Block {
    public Firewood(int id, Material material) {
        super(id, material);
    }
    @Override
    protected void allocateTextures() {
        this.addTexture("oak_log_side", Face.ALL);
        this.addTexture("oak_planks", Face.WEST);
        this.addTexture("oak_planks", Face.EAST);
    }
}
