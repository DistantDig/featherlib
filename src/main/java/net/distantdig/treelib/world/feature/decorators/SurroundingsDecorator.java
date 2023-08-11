package net.distantdig.treelib.world.feature.decorators;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;

public class SurroundingsDecorator extends TreeDecorator {
    public static final Codec<SurroundingsDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("innerBlock").forGetter(SurroundingsDecorator::getInnterBlock),
            Registry.BLOCK.getCodec().fieldOf("outerBlock").forGetter(SurroundingsDecorator::getOuterBlock),

            Registry.BLOCK.getCodec().fieldOf("foliage1").forGetter(SurroundingsDecorator::getFoliage1),
            Registry.BLOCK.getCodec().fieldOf("foliage2").forGetter(SurroundingsDecorator::getFoliage2),
            Registry.BLOCK.getCodec().fieldOf("foliage3").forGetter(SurroundingsDecorator::getFoliage3),
            Registry.BLOCK.getCodec().fieldOf("foliage4").forGetter(SurroundingsDecorator::getFoliage4),
            Registry.BLOCK.getCodec().fieldOf("foliage5").forGetter(SurroundingsDecorator::getFoliage5)
    ).apply(instance, SurroundingsDecorator::new));

    public final Block groundInnerBlock;
    public final Block groundOuterBlock;
    public final Block foliage1;
    public final Block foliage2;
    public final Block foliage3;
    public final Block foliage4;
    public final Block foliage5;

    public double foliageChance;
    public int radius;

    public Block getOuterBlock() {
        return groundOuterBlock;
    }
    public Block getInnterBlock() {
        return groundInnerBlock;
    }

    public Block getFoliage1() {
        return foliage1;
    }
    public Block getFoliage2() {
        return foliage2;
    }
    public Block getFoliage3() {
        return foliage3;
    }
    public Block getFoliage4() {
        return foliage4;
    }
    public Block getFoliage5() {
        return foliage5;
    }

    public SurroundingsDecorator(Block groundInnerBlock, Block groundOuterBlock) {
        this.groundOuterBlock = groundOuterBlock;
        this.groundInnerBlock = groundInnerBlock;

        this.foliage1 = null;
        this.foliage2 = null;
        this.foliage3 = null;
        this.foliage4 = null;
        this.foliage5 = null;
    }
    public SurroundingsDecorator(Block groundInnerBlock, Block groundOuterBlock, Block foliage1) {
        this.groundOuterBlock = groundOuterBlock;
        this.groundInnerBlock = groundInnerBlock;

        this.foliage1 = foliage1;
        this.foliage2 = null;
        this.foliage3 = null;
        this.foliage4 = null;
        this.foliage5 = null;
    }
    public SurroundingsDecorator(Block groundInnerBlock, Block groundOuterBlock, Block foliage1, Block foliage2) {
        this.groundOuterBlock = groundOuterBlock;
        this.groundInnerBlock = groundInnerBlock;

        this.foliage1 = foliage1;
        this.foliage2 = foliage2;
        this.foliage3 = null;
        this.foliage4 = null;
        this.foliage5 = null;
    }
    public SurroundingsDecorator(Block groundInnerBlock, Block groundOuterBlock, Block foliage1, Block foliage2, Block foliage3) {
        this.groundOuterBlock = groundOuterBlock;
        this.groundInnerBlock = groundInnerBlock;

        this.foliage1 = foliage1;
        this.foliage2 = foliage2;
        this.foliage3 = foliage3;
        this.foliage4 = null;
        this.foliage5 = null;
    }
    public SurroundingsDecorator(Block groundInnerBlock, Block groundOuterBlock, Block foliage1, Block foliage2, Block foliage3, Block foliage4) {
        this.groundOuterBlock = groundOuterBlock;
        this.groundInnerBlock = groundInnerBlock;

        this.foliage1 = foliage1;
        this.foliage2 = foliage2;
        this.foliage3 = foliage3;
        this.foliage4 = foliage4;
        this.foliage5 = null;
    }
    public SurroundingsDecorator(Block groundInnerBlock, Block groundOuterBlock, Block foliage1, Block foliage2, Block foliage3, Block foliage4, Block foliage5) {
        this.groundOuterBlock = groundOuterBlock;
        this.groundInnerBlock = groundInnerBlock;

        this.foliage1 = foliage1;
        this.foliage2 = foliage2;
        this.foliage3 = foliage3;
        this.foliage4 = foliage4;
        this.foliage5 = foliage5;
    }
    @Override
    protected TreeDecoratorType<?> getType() {
        return ModDecorators.SURROUNDINGS_DECORATOR;
    }


    @Override
    public void generate(Generator generator) {

        radius = 5;

        ArrayList<BlockPos> list = Lists.newArrayList();
        ObjectArrayList<BlockPos> list3 = generator.getLogPositions();
        list.addAll(list3);
        if (list.isEmpty()) {
            return;
        }
        int i = list.get(0).getY();
        list.stream().filter(pos -> pos.getY() == i).forEach(pos -> {

            for (int h = 0; h < 5; ++h) {
                int j = generator.getRandom().nextInt(64);
                int k = j % 8;
                int l = j / 8;
                if (k != 0 && k != 7 && l != 0 && l != 7) continue;
                this.setArea(generator, pos.add(-3 + k, 0, -3 + l));
            }

            this.setArea(generator, pos.west().north());
            this.setArea(generator, pos.east(2).north());
            this.setArea(generator, pos.west().south(2));
            this.setArea(generator, pos.east(2).south(2));
        });
    }

    private void setArea(Generator generator, BlockPos origin) {
        int radius2 = radius - (radius / 3);
        for (int x = origin.getX() - radius; x <= origin.getX() + radius; ++x) {
            for (int y = origin.getY() - radius; y <= origin.getY() + radius; ++y) {
                if (Math.pow(radius + generator.getRandom().nextInt(2), 2) <= Math.pow(x - origin.getX(), 2) + Math.pow(y - origin.getY(), 2)) continue;
                this.setColumn(groundOuterBlock, generator, origin.add(x - origin.getX(), 0, y - origin.getY()));
            }
        }
        for (int x = origin.getX() - radius2; x <= origin.getX() + radius2; ++x) {
            for (int y = origin.getY() - radius2; y <= origin.getY() + radius2; ++y) {
                if (Math.pow(radius2 + generator.getRandom().nextInt(2), 2) <= Math.pow(x - origin.getX(), 2) + Math.pow(y - origin.getY(), 2)) continue;
                this.setColumn(groundInnerBlock, generator, origin.add(x - origin.getX(), 0, y - origin.getY()));
            }
        }
    }

    private void setColumn(Block block, Generator generator, BlockPos origin) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockPos = origin.up(i);
            foliageChance = getFoliageChance();

            if (Feature.isSoil(generator.getWorld(), blockPos) && !SurroundingsDecorator.isLog(generator.getWorld(), blockPos)) {
                generator.replace(blockPos, block.getDefaultState());
                Block foliage = this.getRandomFoliage(generator);

                if (foliage == foliage3 || foliage == foliage4 || foliage == foliage5) {
                    foliageChance = foliageChance / 2;
                }

                if (generator.getRandom().nextFloat() <= foliageChance && foliage != null && generator.isAir(blockPos.add(0, 1, 0))) {

                    if (foliage instanceof TallPlantBlock && generator.getRandom().nextFloat() <= foliageChance / 2) {
                        generator.replace(blockPos.add(0, 1, 0), foliage.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER));
                        generator.replace(blockPos.add(0, 2, 0), foliage.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                    } else  {
                        generator.replace(blockPos.add(0, 1, 0), foliage.getDefaultState());
                    }
                }
                break;
            }
            if (!generator.isAir(blockPos) && i < 0) break;
        }
    }

    private Block getRandomFoliage(Generator generator) {
        int direction1Raw = generator.getRandom().nextInt(5);
        return switch (direction1Raw) {
            case 0 -> foliage1;
            case 1 -> foliage2;
            case 2 -> foliage3;
            case 3 -> foliage4;
            case 4 -> foliage5;

            default -> throw new IllegalStateException("Unexpected value: " + direction1Raw);
        };
    }

    private double getFoliageChance() {
        foliageChance = 0.0f;
        if (foliage1 != null) {
            foliageChance += 0.1;
        }
        if (foliage2 != null) {
            foliageChance += 0.1;
        }
        if (foliage3 != null) {
            foliageChance += 0.1;
        }
        if (foliage4 != null) {
            foliageChance += 0.1;
        }
        if (foliage5 != null) {
            foliageChance += 0.1;
        }
        return foliageChance;
    }

    public static boolean isLog(BlockState state) {
        return state.isIn(BlockTags.LOGS);
    }

    public static boolean isLog(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, SurroundingsDecorator::isLog);
    }
}