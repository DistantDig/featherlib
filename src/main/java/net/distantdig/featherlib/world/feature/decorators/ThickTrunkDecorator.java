package net.distantdig.featherlib.world.feature.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ThickTrunkDecorator extends TreeDecorator {
    public static final Codec<ThickTrunkDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("block").forGetter(ThickTrunkDecorator::getBlock)
    ).apply(instance, ThickTrunkDecorator::new));

    public final Block block;

    public Block getBlock() {
        return block;
    }

    public ThickTrunkDecorator(Block block) {
        this.block = block;
    }
    @Override
    protected TreeDecoratorType<?> getType() {
        return ModDecorators.THICK_TRUNK_DECORATOR;
    }


    @Override
    public void generate(Generator generator) {

        Random random = generator.getRandom();
        ObjectArrayList<BlockPos> list = generator.getLogPositions();
        int i = list.get(0).getY();

        list.stream().filter(pos -> pos.getY() - i == 3).forEach(pos -> {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                Direction direction2 = direction.getOpposite();
                BlockPos blockPos = pos.add(direction2.getOffsetX(), 0, direction2.getOffsetZ());
                generator.replace(blockPos.down(), block.getDefaultState());
                generator.replace(blockPos.down().down(), block.getDefaultState());
                if (!(random.nextFloat() <= 0.5f)) continue;
                generator.replace(blockPos, block.getDefaultState());
            }
        });
    }
}