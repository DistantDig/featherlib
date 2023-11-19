package net.distantdig.treelib.world.feature.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.registry.Registries;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import static net.minecraft.block.SnowBlock.LAYERS;

public class CarpetDecorator extends TreeDecorator {
    public static final Codec<CarpetDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registries.BLOCK.getCodec().fieldOf("block").forGetter(CarpetDecorator::getBlock)
    ).apply(instance, CarpetDecorator::new));

    public final Block block;

    public Block getBlock() {
        return block;
    }

    public CarpetDecorator(Block block) {
        this.block = block;
    }
    @Override
    protected TreeDecoratorType<?> getType() {
        return ModDecorators.CARPET_DECORATOR;
    }


    @Override
    public void generate(Generator generator) {

        Random random = generator.getRandom();
        ObjectArrayList<BlockPos> list = generator.getLogPositions();
        int i = list.get(0).getY();

        list.stream().filter(pos -> pos.getY() - i <= 1).forEach(pos -> {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                Direction direction2 = direction.getOpposite();
                BlockPos blockPos = pos.add(direction2.getOffsetX(), 0, direction2.getOffsetZ());
                int layers = random.nextInt(1) + 1;
                if (!TreeFeature.canReplace(generator.getWorld(), blockPos)
                        || !Feature.isSoil(generator.getWorld(), pos.add(direction2.getOffsetX(), -1, direction2.getOffsetZ()))) continue;

                for (Direction direction3 : Direction.Type.HORIZONTAL) {
                    if (!(generator.isAir(blockPos.offset(direction3)) || isReplaceablePlant(generator.getWorld(), blockPos.offset(direction3)))) {
                        ++layers;
                        ++layers;
                    } else {
                        generator.replace(blockPos.offset(direction3), block.getDefaultState().with(LAYERS, random.nextInt(1) + 1));
                    }
                }
                if (layers > 6) {
                    layers = 6;
                }
                generator.replace(blockPos, block.getDefaultState().with(LAYERS, layers));
            }
        });
    }

    public static boolean isReplaceablePlant(BlockState state) {
        return state.isIn(BlockTags.REPLACEABLE_BY_TREES);
    }

    public static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, CarpetDecorator::isReplaceablePlant);
    }
}