package net.distantdig.featherlib.world.feature.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class WildCocoaDecorator extends TreeDecorator {

    public static final Codec<WildCocoaDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.POSITIVE_FLOAT.fieldOf("probability").forGetter(WildCocoaDecorator::getProbability)
    ).apply(instance, WildCocoaDecorator::new));
    private final float probability;

    public Float getProbability() {
        return probability;
    }
    public WildCocoaDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModDecorators.WILD_COCOA_DECORATOR;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        ObjectArrayList<BlockPos> list = generator.getLogPositions();
        int i = list.get(0).getY();
        list.stream().filter(pos -> pos.getY() - i >= 2).forEach(pos -> {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                Direction direction2;
                BlockPos blockPos;
                if (random.nextFloat() >= 0.02f || !generator.isAir(blockPos = pos.add((direction2 = direction.getOpposite()).getOffsetX(), 0, direction2.getOffsetZ()))) continue;
                generator.replace(blockPos, Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, random.nextInt(3)).with(CocoaBlock.FACING, direction));
            }
        });
    }
}
