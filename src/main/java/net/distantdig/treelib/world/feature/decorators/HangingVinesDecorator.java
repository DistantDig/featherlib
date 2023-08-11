package net.distantdig.treelib.world.feature.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class HangingVinesDecorator extends TreeDecorator {
    public static final float PROBABILITY = 0.2f;
    public static final Codec<HangingVinesDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("leafBlock").forGetter(HangingVinesDecorator::getLeafBlock),
            Registry.BLOCK.getCodec().fieldOf("plantBlock").forGetter(HangingVinesDecorator::getPlantBlock)
    ).apply(instance, HangingVinesDecorator::new));
    public static final List<Direction> ACCEPTABLE_POS = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    public final Block leafBlock;
    public final Block plantBlock;

    public HangingVinesDecorator(Block leafBlock, Block plantBlock) {
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
        return ModDecorators.HANGING_VINES_DECORATOR;
    }

    @Override
    public void generate(Generator generator) {
        List<BlockPos> leaves = generator.getLeavesPositions().stream().filter(blockPos -> generator.isAir(blockPos.down())).toList();
        for (BlockPos leaf : leaves) {
            if (generator.getRandom().nextFloat() >= 0.3f) continue;
            int limit = generator.getRandom().nextBetween(1, 5);
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