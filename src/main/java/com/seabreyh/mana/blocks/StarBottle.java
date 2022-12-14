package com.seabreyh.mana.blocks;

import java.util.Random;
import java.util.stream.Stream;

import com.seabreyh.mana.registry.ManaParticles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StarBottle extends LanternBlock {

    public StarBottle(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 7, 11),
            Block.box(6, 7, 6, 10, 8, 10),
            Block.box(5.5, 8, 5.5, 10.5, 9, 10.5),
            Block.box(6.5, 7, 6.5, 9.5, 8, 9.5)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_HANGING = Stream.of(
            Block.box(5, 1, 5, 11, 8, 11),
            Block.box(6, 8, 6, 10, 9, 10),
            Block.box(5.5, 9, 5.5, 10.5, 10, 10.5),
            Block.box(6.5, 8, 6.5, 9.5, 9, 9.5)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(HANGING) ? SHAPE_HANGING : SHAPE;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        double x = (double) blockPos.getX() + 0.25;
        double y = (double) blockPos.getY();
        double z = (double) blockPos.getZ() + 0.25;

        for (int i = 0; i < 2; i++) {
            level.addParticle(ManaParticles.STAR_POWER.get(),
                    x + 0.5 * random.nextDouble(0.2, 0.8),
                    y + 0.3 * random.nextDouble(0.2, 0.8),
                    z + 0.5 * random.nextDouble(0.2, 0.8),
                    Math.sin(i * random.nextDouble(10)) * 0.01d, Math.cos(i * random.nextDouble(10)) * 0.01d,
                    Math.sin(i * random.nextDouble(10)) * 0.01d);
        }

    }

}