package com.terraformersmc.campanion.mixin;

import com.terraformersmc.campanion.block.BaseTentBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.terraformersmc.campanion.block.BaseTentBlock.DESTROYED;

@Mixin(Explosion.class)
public class MixinExplosion {
	@Redirect(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private boolean mixinDestroyBlocks(World world, BlockPos pos, BlockState newState, int notifyFlag) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof BaseTentBlock) {
			return world.setBlockState(pos, state.with(DESTROYED, true), Block.NOTIFY_ALL);
		} else {
			return world.setBlockState(pos, newState, Block.NOTIFY_ALL);
		}
	}
}
