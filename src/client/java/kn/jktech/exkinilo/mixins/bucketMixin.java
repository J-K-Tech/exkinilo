package kn.jktech.exkinilo.mixins;

import kn.jktech.exkinilo.clinilo;
import net.minecraft.src.client.physics.MovingObjectPosition;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemBucket;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.EnumMovingObjectType;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemBucket.class)
public class bucketMixin extends Item {
    protected bucketMixin(int id) {
        super(id);
    }
@Shadow

private int heldLiquid;
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        boolean flag = this.heldLiquid == 0;
        MovingObjectPosition hitResult = this.getHitResult(world, player, flag);
        if (hitResult == null) {
            return itemstack;

        } else {
            if (hitResult.typeOfHit == EnumMovingObjectType.TILE) {
                int x = hitResult.blockX;
                int y = hitResult.blockY;
                int z = hitResult.blockZ;
                if (this.heldLiquid == 0) {
                    if (world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0) {
                        world.playSoundAtEntity(player, "item.bucket.fill", 1.0F, 1.0F);
                        world.setBlockWithNotify(x, y, z, 0);
                        player.swingItem();
                        if (player.capabilities.isCreativeMode) {
                            return itemstack;
                        }

                        return new ItemStack(Item.bucketWater);
                    }

                    if (world.getBlockMaterial(x, y, z) == Material.lava && world.getBlockMetadata(x, y, z) == 0) {
                        world.playSoundAtEntity(player, "item.bucket.fill_lava", 1.0F, 1.0F);
                        world.setBlockWithNotify(x, y, z, 0);
                        player.swingItem();
                        if (player.capabilities.isCreativeMode) {
                            return itemstack;
                        }

                        return new ItemStack(Item.bucketLava);
                    }
                    if (world.getBlockMaterial(x, y, z) == Material.snow && world.getBlockMetadata(x, y, z) == 0) {
                        world.playSoundAtEntity(player, "item.bucket.fill", 1.0F, 1.0F);
                        world.setBlockWithNotify(x, y, z, 0);
                        player.swingItem();
                        if (player.capabilities.isCreativeMode) {
                            return itemstack;
                        }

                        return new ItemStack(clinilo.COOLANTBUCKET,1);
                    }
                } else {
                    if (this.heldLiquid < 0) {
                        return new ItemStack(Item.bucketEmpty);
                    }

                    if (hitResult.sideHit == 0) {
                        y--;
                    }

                    if (hitResult.sideHit == 1) {
                        y++;
                    }

                    if (hitResult.sideHit == 2) {
                        z--;
                    }

                    if (hitResult.sideHit == 3) {
                        z++;
                    }

                    if (hitResult.sideHit == 4) {
                        x--;
                    }

                    if (hitResult.sideHit == 5) {
                        x++;
                    }

                    if (world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid()) {
                        if (world.worldProvider.isHellWorld && this.heldLiquid == Block.waterMoving.blockID) {
                            world.playAuxSFX(1004, (double)x, (double)y, (double)z, 0);
                        } else {
                            player.swingItem();
                            if (this.heldLiquid == Block.waterMoving.blockID) {
                                world.playSoundAtEntity(player, "item.bucket.empty", 1.0F, 1.0F);
                            }

                            if (this.heldLiquid == Block.lavaMoving.blockID) {
                                world.playSoundAtEntity(player, "item.bucket.empty_lava", 1.0F, 1.0F);
                            }

                            world.setBlockAndMetadataWithNotify(x, y, z, this.heldLiquid, 0);
                        }

                        player.swingItem();
                        if (player.capabilities.isCreativeMode) {
                            return itemstack;
                        }

                        return new ItemStack(Item.bucketEmpty);
                    }
                }
            }

            return itemstack;
        }
    }
}
