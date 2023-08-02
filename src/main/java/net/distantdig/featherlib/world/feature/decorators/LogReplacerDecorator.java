package net.distantdig.featherlib.world.feature.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class LogReplacerDecorator extends TreeDecorator {
    public static final Codec<LogReplacerDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("block").forGetter(LogReplacerDecorator::getBlock)
    ).apply(instance, LogReplacerDecorator::new));

    public final Block block;

    public Block getBlock() {
        return block;
    }

    public LogReplacerDecorator(Block block) {
        this.block = block;
    }
    @Override
    protected TreeDecoratorType<?> getType() {
        return ModDecorators.LOG_REPLACER_DECORATOR;
    }


    @Override
    public void generate(Generator generator) {

        Random random = generator.getRandom();
        ObjectArrayList<BlockPos> list = generator.getLogPositions();
        int i = list.get(0).getY();

        list.stream().filter(pos -> pos.getY() - i <= 6).forEach(pos -> {
            if (pos.getY() - i == 6 && (random.nextFloat() <= 0.25f)){
                generator.replace(pos, block.getDefaultState());
            }
            if (pos.getY()- i == 5 && (random.nextFloat() <= 0.5f)) {
                generator.replace(pos, block.getDefaultState());
            }
            if (pos.getY()- i <= 4) {
                generator.replace(pos, block.getDefaultState());
            }
        });
    }
}