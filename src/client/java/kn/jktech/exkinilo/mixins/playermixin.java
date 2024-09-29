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
}
