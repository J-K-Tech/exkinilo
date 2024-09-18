package kn.jktech.exkinilo.blocks;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockFluid;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.level.World;

import java.util.Random;

public abstract class flowCoolant extends BlockFluid {
    int numAdjacentSources = 0;
    boolean[] isOptimalFlowDirection = new boolean[4];
    int[] flowCost = new int[4];

    public flowCoolant(int id, Material material) {
        super(id, material);
        allocateTextures();
        setRequiresSelfNotify();
    }
    @Override
    public int tickRate() {
        return 20;
    }
    @Override
    protected void allocateTextures() {
        this.addTexture("coolant", Face.ALL);
    }

    private void stabilize(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadata(x, y, z, this.blockID + 1, metadata);
        world.markBlocksDirty(x, y, z, x, y, z);
        world.markBlockNeedsUpdate(x, y, z);
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        int flowDecay = this.getFlowDecay(world, x, y, z);
        byte viscosity = 3;
        if (this.blockMaterial == Material.lava && !world.worldProvider.isHellWorld) {
            viscosity = 2;
        }

        boolean isLavaAndETC = true;
        if (flowDecay > 0) {
            byte init = -100;
            this.numAdjacentSources = 0;
            int smfldcy = this.getSmallestFlowDecay(world, x - 1, y, z, init);
            smfldcy = this.getSmallestFlowDecay(world, x + 1, y, z, smfldcy);
            smfldcy = this.getSmallestFlowDecay(world, x, y, z - 1, smfldcy);
            smfldcy = this.getSmallestFlowDecay(world, x, y, z + 1, smfldcy);
            int reach = smfldcy + viscosity;
            if (reach >= 8 || smfldcy < 0) {
                reach = -1;
            }

            if (this.getFlowDecay(world, x, y + 1, z) >= 0) {
                int xfldcy = this.getFlowDecay(world, x, y + 1, z);
                if (xfldcy >= 8) {
                    reach = xfldcy;
                } else {
                    reach = xfldcy + 8;
                }
            }

            if (this.numAdjacentSources >= 2 && this.blockMaterial == Material.snow) {
                if (world.getBlockMaterial(x, y - 1, z).isSolid()) {
                    reach = 0;
                } else if (world.getBlockMaterial(x, y - 1, z) == this.blockMaterial) {
                    reach = 0;
                }
            }

            if (this.blockMaterial == Material.lava && flowDecay < 8 && reach < 8 && reach > flowDecay) {
                isLavaAndETC = false;
            }

            if (reach != flowDecay) {
                flowDecay = reach;
                if (reach < 0) {
                    world.setBlockWithNotify(x, y, z, 0);
                } else {
                    world.setBlockMetadataWithNotify(x, y, z, reach);
                    world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
                    world.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
                }
            } else if (isLavaAndETC) {
                this.stabilize(world, x, y, z);
            }
        } else {
            this.stabilize(world, x, y, z);
        }

        if (this.liquidCanDisplaceBlock(world, x, y - 1, z)) {
            if (flowDecay >= 8) {
                this.flowIntoBlock(world, x, y - 1, z, flowDecay);
            } else {
                this.flowIntoBlock(world, x, y - 1, z, flowDecay + 8);
            }
        } else if (flowDecay >= 0 && (flowDecay == 0 || this.blockBlocksFlow(world, x, y - 1, z))) {
            boolean[] fldir = this.getOptimalFlowDirections(world, x, y, z);
            int reachx = flowDecay + viscosity;
            if (flowDecay >= 8) {
                reachx = 1;
            }

            if (reachx >= 8) {
                return;
            }

            if (fldir[0]) {
                this.flowIntoBlock(world, x - 1, y, z, reachx);
            }

            if (fldir[1]) {
                this.flowIntoBlock(world, x + 1, y, z, reachx);
            }

            if (fldir[2]) {
                this.flowIntoBlock(world, x, y, z - 1, reachx);
            }

            if (fldir[3]) {
                this.flowIntoBlock(world, x, y, z + 1, reachx);
            }
        }
    }

    private void flowIntoBlock(World world, int x, int y, int z, int metad) {
        if (this.liquidCanDisplaceBlock(world, x, y, z)) {
            int blid = world.getBlockId(x, y, z);
            if (blid > 0) {
                if (this.blockMaterial == Material.lava) {
                    this.triggerLavaMixEffects(world, x, y, z);
                } else {
                    Block.blocksList[blid].dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
                }
            }

            world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, metad);
        }
    }

    private int calculateFlowCost(World world, int x, int y, int z, int depth, int fcai) {
        int flowcost = 1000;

        for (int flciter = 0; flciter < 4; flciter++) {
            if ((flciter != 0 || fcai != 1)
                    && (flciter != 1 || fcai != 0)
                    && (flciter != 2 || fcai != 3)
                    && (flciter != 3 || fcai != 2)) {
                int fcx = x;
                int fcz = z;
                if (flciter == 0) {
                    fcx = x - 1;
                }

                if (flciter == 1) {
                    fcx++;
                }

                if (flciter == 2) {
                    fcz = z - 1;
                }

                if (flciter == 3) {
                    fcz++;
                }

                if (!this.blockBlocksFlow(world, fcx, y, fcz)
                        && (
                        world.getBlockMaterial(fcx, y, fcz) != this.blockMaterial
                                || world.getBlockMetadata(fcx, y, fcz) != 0
                )) {
                    if (!this.blockBlocksFlow(world, fcx, y - 1, fcz)) {
                        return depth;
                    }

                    if (depth < 4) {
                        int tflowcost = this.calculateFlowCost(world, fcx, y, fcz, depth + 1, flciter);
                        if (tflowcost < flowcost) {
                            flowcost = tflowcost;
                        }
                    }
                }
            }
        }

        return flowcost;
    }

    private boolean[] getOptimalFlowDirections(World world, int x, int y, int z) {
        for (int mulp = 0; mulp < 4; mulp++) {
            this.flowCost[mulp] = 1000;
            int mulp2 = x;
            int zulp = z;
            if (mulp == 0) {
                mulp2 = x - 1;
            }

            if (mulp == 1) {
                mulp2++;
            }

            if (mulp == 2) {
                zulp = z - 1;
            }

            if (mulp == 3) {
                zulp++;
            }

            if (!this.blockBlocksFlow(world, mulp2, y, zulp)
                    && (
                    world.getBlockMaterial(mulp2, y, zulp) != this.blockMaterial
                            || world.getBlockMetadata(mulp2, y, zulp) != 0
            )) {
                if (!this.blockBlocksFlow(world, mulp2, y - 1, zulp)) {
                    this.flowCost[mulp] = 0;
                } else {
                    this.flowCost[mulp] = this.calculateFlowCost(world, mulp2, y, zulp, 1, mulp);
                }
            }
        }

        int var8 = this.flowCost[0];

        for (int mulp2x = 1; mulp2x < 4; mulp2x++) {
            if (this.flowCost[mulp2x] < var8) {
                var8 = this.flowCost[mulp2x];
            }
        }

        for (int var10 = 0; var10 < 4; var10++) {
            this.isOptimalFlowDirection[var10] = this.flowCost[var10] == var8;
        }

        return this.isOptimalFlowDirection;
    }

    private boolean blockBlocksFlow(World world, int x, int y, int z) {
        int blid = world.getBlockId(x, y, z);
        if (blid == Block.doorWood.blockID
                || blid == Block.doorSteel.blockID
                || blid == Block.signPost.blockID
                || blid == Block.ladder.blockID
                || blid == Block.sugarCane.blockID
                || blid == Block.gearRelayIdle.blockID
                || blid == Block.gearRelayActive.blockID
                || blid == Block.gearWaitIdle.blockID
                || blid == Block.gearWaitActive.blockID
                || blid == Block.motorIdle.blockID
                || blid == Block.motorActive.blockID) {
            return true;
        } else if (blid == 0) {
            return false;
        } else {
            Material mat = Block.blocksList[blid].blockMaterial;
            return mat.getIsSolid();
        }
    }

    protected int getSmallestFlowDecay(World world, int x, int y, int z, int flwdcy2) {
        int flwdcy = this.getFlowDecay(world, x, y, z);
        if (flwdcy < 0) {
            return flwdcy2;
        } else {
            if (flwdcy == 0) {
                this.numAdjacentSources++;
            }

            if (flwdcy >= 8) {
                flwdcy = 0;
            }

            return flwdcy2 >= 0 && flwdcy >= flwdcy2 ? flwdcy2 : flwdcy;
        }
    }

    private boolean liquidCanDisplaceBlock(World world, int x, int y, int z) {
        Material mat = world.getBlockMaterial(x, y, z);
        if (mat == this.blockMaterial) {
            return false;
        } else {
            return mat == Material.lava ? false : !this.blockBlocksFlow(world, x, y, z);
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        if (world.getBlockId(x, y, z) == this.blockID) {
            world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
        }
    }
}
