package net.distantdig.featherlib.world.feature.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class LoggyVinesDecorator extends TreeDecorator {
    public static final float PROBABILITY = 0.2f;
    public static final Codec<LoggyVinesDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("leafBlock").forGetter(LoggyVinesDecorator::getLeafBlock),
            Registry.BLOCK.getCodec().fieldOf("plantBlock").forGetter(LoggyVinesDecorator::getPlantBlock)
    ).apply(instance, LoggyVinesDecorator::new));
    public static final List<Direction> ACCEPTABLE_POS = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    public final Block leafBlock;
    public final Block plantBlock;

    public LoggyVinesDecorator(Block leafBlock, Block plantBlock) {
        this.leafBlock = leafBlock;
        this.plantBlock = plantBlock;
    }

    public Block getLeafBlock() {
        return leafBlock;
    }

    public Block getPlantBlock() {
        return plantBlock;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModDecorators.LOGGY_VINES_DECORATOR;
    }

    @Override
    public void generate(Generator generator) {
        List<BlockPos> logs = generator.getLogPositions().stream().filter(blockPos -> generator.isAir(blockPos.down())).toList();
        for (BlockPos leaf : logs) {
            if (generator.getRandom().nextFloat() >= 0.3f) continue;
            int limit = generator.getRandom().nextBetween(6, 10);
            for (int i = 1; i <= limit; i++) {
                BlockPos decoration = leaf.down(i).toImmutable();
                if (generator.isAir(decoration)) {
                    generator.replace(decoration, this.getLeafBlock().getDefaultState());
                    if (i == limit) {generator.replace(decoration, this.getPlantBlock().getDefaultState());}
                }
            }
        }
    }
}