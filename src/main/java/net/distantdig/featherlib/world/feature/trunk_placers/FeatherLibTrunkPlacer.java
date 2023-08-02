package net.distantdig.featherlib.world.feature.trunk_placers;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class FeatherLibTrunkPlacer extends net.minecraft.world.gen.trunk.TrunkPlacer {
    //Big Branch
    int bigBranchHeight;
    int bigBranchLength;
    Direction bigBranchDirection1;
    Direction bigBranchDirection2;

    //Big Branch 2
    int bigYBranchHeight;
    int bigYBranchLength;
    Direction bigYBranchDirection1;
    Direction bigYBranchDirection2;

    //Small Branch
    int smallBranchLength;
    int smallBranchOffset;
    Direction smallBranchDirection1;
    Direction smallBranchDirection2;
    boolean alreadyBranched;

    //Small Branch 2
    int smallYBranchLength;
    int smallYBranchHeightOffset;
    int smallYBranchLengthOffset;
    Direction smallYBranchDirection1;
    Direction smallYBranchDirection2;

    //Y Branch
    int yBranchHeight;
    int yBranchLength;
    Direction yBranchDirection1;
    Direction yBranchDirection2;



    /* ADD TO TREE TRUNK PLACER
    public static final Codec<TreelibTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            fillTrunkPlacerFields(instance).apply(instance, TreelibTrunkPlacer::new));
    @Override
    protected TrunkPlacerType<?> getType() {
        return ImmersiveTrees.TREELIB_TRUNK_PLACER;
    }
     */

    public FeatherLibTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {

        bigBranchHeight = 0;
        bigBranchLength = 0;
        bigBranchDirection1 = this.getDirection(Direction.NORTH, Direction.SOUTH, random);
        bigBranchDirection2 = this.getDirection(Direction.EAST, Direction.WEST, random);

        setToDirt(world, replacer, random, startPos.down(), config);

        for (int trunkHeight = 0; trunkHeight < height; trunkHeight++) {

            //Roots Example
            if (trunkHeight == 0){
                this.generateRoots(Direction.NORTH, Direction.WEST, 0, 0, trunkHeight, world, replacer, random, startPos, config);
                this.generateRoots(Direction.NORTH, Direction.EAST, 0, 1, trunkHeight, world, replacer, random, startPos, config);
                this.generateRoots(Direction.SOUTH, Direction.WEST, -1, 0, trunkHeight, world, replacer, random, startPos, config);
                this.generateRoots(Direction.SOUTH, Direction.EAST, -1, 1, trunkHeight, world, replacer, random, startPos, config);
            }

            //Main Trunk Example
            this.generateTrunk(trunkHeight, world, replacer, random, startPos, config);

            //Branch Example
            if (trunkHeight == height - 1) {
                this.generateBigBranch(3, trunkHeight, world, replacer, random, startPos, config);
            }
        }

        //Leaves Example
        return ImmutableList.of(
                //If branching off a previous branch, be sure to add all the other branch's offsets as well
                new FoliagePlacer.TreeNode(startPos.up(height + bigBranchLength + bigBranchHeight).offset(bigBranchDirection1, bigBranchLength).offset(bigBranchDirection2, bigBranchLength), 1, false)
        );
    }

    public void generateRoots(Direction directionFirst, Direction directionSecond, int offsetNS, int offsetEW, int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {

        // Original V shape roots
        this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
        if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 1 + offsetEW))) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
        }

        if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 3 + offsetNS).offset(directionSecond, 1 + offsetEW))) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 3 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
        } else {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 3 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
        }

        if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 2 + offsetEW))) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 2 + offsetEW), config);
        } else {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 2 + offsetEW), config);
        }

        //Extended Sides
        if (!(random.nextFloat() <= 0.5f)) {
            if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 4 + offsetNS).offset(directionSecond, 1 + offsetEW))) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 4 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
                if (this.canReplace(world, startPos.up(trunkHeight - 2).offset(directionFirst, 4 + offsetNS).offset(directionSecond, 1 + offsetEW))) {
                    this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 2).offset(directionFirst, 4 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
                }
            } else {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 4 + offsetNS).offset(directionSecond, 1 + offsetEW), config);
            }
        }

        if (!(random.nextFloat() <= 0.5f)) {
            if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 3 + offsetEW))) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 3 + offsetEW), config);
                if (this.canReplace(world, startPos.up(trunkHeight - 2).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 3 + offsetEW))) {
                    this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 2).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 3 + offsetEW), config);
                }
            } else {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 2 + offsetNS).offset(directionSecond, 3 + offsetEW), config);
            }
        }

        //Next to trunk roots
        if (!(random.nextFloat() <= 0.5f)) {
            if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 3 + offsetNS))) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 3 + offsetNS), config);
            } else {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 3 + offsetNS), config);
            }
        }

        if (!(random.nextFloat() <= 0.5f)) {
            if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionSecond, 2  + offsetEW))) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionSecond, 2  + offsetEW), config);
            } else {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionSecond, 2  + offsetEW), config);
            }
        }

        if (!(random.nextFloat() <= 0.5f)) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionFirst, 2 + offsetNS), config);
        }
        if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS))) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionFirst, 2 + offsetNS), config);
            if (!(random.nextFloat() <= 0.5f) && this.canReplace(world, startPos.up(trunkHeight - 2).offset(directionFirst, 2 + offsetNS).offset(directionSecond, offsetEW))) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 2).offset(directionFirst, 2 + offsetNS).offset(directionSecond, offsetEW), config);
            }
        }

        if (!(random.nextFloat() <= 0.5f)) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).offset(directionSecond, 1  + offsetEW), config);
        }
        if (this.canReplace(world, startPos.up(trunkHeight - 1).offset(directionSecond, 1  + offsetEW))) {
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 1).offset(directionSecond, 1  + offsetEW), config);
            if (!(random.nextFloat() <= 0.5f) && this.canReplace(world, startPos.up(trunkHeight - 2).offset(directionSecond, 1  + offsetEW).offset(directionFirst, offsetNS))) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight - 2).offset(directionSecond, 1  + offsetEW).offset(directionFirst, offsetNS), config);
            }
        }
    }

    public void generateTrunk(int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {
        this.getAndSetState(world, replacer, random, startPos.up(trunkHeight), config);
        this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).east(), config);
        this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).north(), config);
        this.getAndSetState(world, replacer, random, startPos.up(trunkHeight).east().north(), config);
    }

    public void generateYBranch(int steps, int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {

        int branchHeight = 0;
        int branchLength;

        for (branchLength = 0; branchLength <= steps; ++branchLength) {

            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(yBranchDirection1, branchLength).offset(yBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(yBranchDirection1, branchLength + 1).offset(yBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(yBranchDirection1, branchLength).offset(yBranchDirection2, branchLength + 1), config);

            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(yBranchDirection1, branchLength).offset(yBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(yBranchDirection1, branchLength + 1).offset(yBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(yBranchDirection1, branchLength).offset(yBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(yBranchDirection1, branchLength + 1).offset(yBranchDirection2, branchLength + 1), config);

            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 2).offset(yBranchDirection1, branchLength).offset(yBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 2).offset(yBranchDirection1, branchLength + 1).offset(yBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 2).offset(yBranchDirection1, branchLength).offset(yBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 2).offset(yBranchDirection1, branchLength + 1).offset(yBranchDirection2, branchLength + 1), config);
            branchHeight += 1;
        }

        this.generateYBigBranch(2, branchHeight, branchLength, trunkHeight, world, replacer, random, startPos, config);
        this.generateYSmallBranch(3, branchHeight, branchLength, trunkHeight, world, replacer, random, startPos, config);

        this.yBranchHeight = branchHeight;
        this.yBranchLength = branchLength;
    }
    public void generateYBigBranch(int steps, int heightOffset, int lengthOffset, int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {
        int branchHeight = 0;
        int branchLength;
        for (branchLength = 0; branchLength <= steps; ++branchLength) {

            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength).offset(bigYBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength + 1).offset(bigYBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength).offset(bigYBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength + 1).offset(bigYBranchDirection2, branchLength + 1), config);

            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset + 1).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength).offset(bigYBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset + 1).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength + 1).offset(bigYBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset + 1).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength).offset(bigYBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + heightOffset + lengthOffset + 1).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(bigYBranchDirection1, branchLength + 1).offset(bigYBranchDirection2, branchLength + 1), config);
        }
        this.bigYBranchHeight = branchHeight;
        this.bigYBranchLength = branchLength;
    }
    public void generateYSmallBranch(int steps, int heightOffset, int lengthOffset, int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {
        int branchLength;
        for (branchLength = 0; branchLength <= steps; ++branchLength) {
            if (branchLength <=1) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + heightOffset + lengthOffset - 1).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(smallYBranchDirection1, branchLength).offset(smallYBranchDirection2, branchLength), config);
            }
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + heightOffset + lengthOffset).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(smallYBranchDirection1, branchLength).offset(smallYBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + heightOffset + lengthOffset).offset(yBranchDirection1, lengthOffset).offset(yBranchDirection2, lengthOffset).offset(smallYBranchDirection1, branchLength).offset(smallYBranchDirection2, branchLength + 1), config);
        }
        this.smallYBranchLength = branchLength;
        this.smallYBranchHeightOffset = heightOffset;
        this.smallYBranchLengthOffset = lengthOffset + branchLength;
    }

    public void generateBigBranch(int steps, int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {
        int branchHeight = 0;
        int branchLength;
        for (branchLength = 0; branchLength <= steps; ++branchLength) {

            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(bigBranchDirection1, branchLength).offset(bigBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(bigBranchDirection1, branchLength + 1).offset(bigBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(bigBranchDirection1, branchLength).offset(bigBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight).offset(bigBranchDirection1, branchLength + 1).offset(bigBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength).offset(bigBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength + 1).offset(bigBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength).offset(bigBranchDirection2, branchLength + 1), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength + 1).offset(bigBranchDirection2, branchLength + 1), config);

            if (!(random.nextFloat() >= 0.7f)) {
                branchHeight += 1;
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength).offset(bigBranchDirection2, branchLength), config);
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength + 1).offset(bigBranchDirection2, branchLength), config);
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength).offset(bigBranchDirection2, branchLength + 1), config);
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + branchLength + branchHeight + 1).offset(bigBranchDirection1, branchLength + 1).offset(bigBranchDirection2, branchLength + 1), config);
            }

            if ((random.nextFloat() <= 0.2f) && !alreadyBranched) {
                this.generateSmallBranch(2, branchLength, trunkHeight, world, replacer, random, startPos, config);
            }
        }
        this.bigBranchHeight = branchHeight;
        this.bigBranchLength = branchLength;
    }

    public void generateSmallBranch(int steps, int offset, int trunkHeight, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config) {
        int branchLength;
        for (branchLength = 0; branchLength <= steps; ++branchLength) {
            if (branchLength <=1) {
                this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + offset - 1).offset(bigBranchDirection1, offset).offset(bigBranchDirection2, offset).offset(smallBranchDirection1, branchLength).offset(smallBranchDirection2, branchLength), config);
            }
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + offset).offset(bigBranchDirection1, offset).offset(bigBranchDirection2, offset).offset(smallBranchDirection1, branchLength).offset(smallBranchDirection2, branchLength), config);
            this.getAndSetState(world, replacer, random, startPos.up(trunkHeight + offset).offset(bigBranchDirection1, offset).offset(bigBranchDirection2, offset).offset(smallBranchDirection1, branchLength).offset(smallBranchDirection2, branchLength + 1), config);
        }
        this.smallBranchLength = branchLength;
        this.smallBranchOffset = offset;
        alreadyBranched = true;
    }

    public Direction getDirection(Direction directionPrimary, Direction directionOpposite, Random random) {
        int direction1Raw = random.nextInt(2);
        return switch (direction1Raw) {
            case 0 -> directionPrimary;
            case 1 -> directionOpposite;

            default -> throw new IllegalStateException("Unexpected value: " + direction1Raw);
        };
    }

    public Direction getDirectionOpposite(Direction directionReference, Direction directionPrimary, Direction directionOpposite) {
        if (directionReference == directionPrimary) {
            return directionOpposite;
        } else {
            return directionPrimary;
        }
    }

    public Direction preventDoubles(Direction firstDirection, Direction secondDirection, Direction directionPrimary, Direction directionOpposite, Random random) {
        if(firstDirection == secondDirection) {
            return this.getDirection(directionPrimary, directionOpposite, random);
        }
        else {
            return secondDirection;
        }
    }

    public Direction detectDoubles(Direction set1Direction1, Direction set1Direction2, Direction set2Direction1, Direction set2Direction2, Direction directionPrimary, Direction directionOpposite, Random random) {
        if (set1Direction1 == set2Direction1 && set1Direction2 == set2Direction2) {
            return preventDoubles(set1Direction1, set2Direction1, directionPrimary, directionOpposite, random);
        } else {
            return set1Direction1;
        }
    }
}
