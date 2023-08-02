package net.distantdig.featherlib.world.feature.decorators;

import net.distantdig.immersive_trees.ImmersiveTrees;
import net.distantdig.immersive_trees.mixin.FoliagePlacerTypeInvoker;
import net.distantdig.immersive_trees.mixin.TreeDecoratorTypeInvoker;
import net.distantdig.immersive_trees.mixin.TrunkPlacerTypeInvoker;
import net.distantdig.immersive_trees.world.feature.foliage_placers.WildJungleFoliagePlacer;
import net.distantdig.immersive_trees.world.feature.trunk_placers.WildJungleTrunkPlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModDecorators {

    public static final TreeDecoratorType<CarpetDecorator> CARPET_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:carpet_decorator", CarpetDecorator.CODEC);
    public static final TreeDecoratorType<HangingVinesDecorator> HANGING_VINES_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:hanging_vines_decorator", HangingVinesDecorator.CODEC);
    public static final TreeDecoratorType<LogReplacerDecorator> LOG_REPLACER_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:thick_trunk_decorator", LogReplacerDecorator.CODEC);
    public static final TreeDecoratorType<SurroundingsDecorator> SURROUNDINGS_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:surroundings_decorator", SurroundingsDecorator.CODEC);
    public static final TreeDecoratorType<ThickTrunkDecorator> THICK_TRUNK_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:log_replacer_decorator", ThickTrunkDecorator.CODEC);
    public static final TreeDecoratorType<WildCocoaDecorator> WILD_COCOA_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:wild_cocoa_decorator", WildCocoaDecorator.CODEC);
    public static final TreeDecoratorType<LoggyVinesDecorator> LOGGY_VINES_DECORATOR = TreeDecoratorTypeInvoker.callRegister("immersive_trees:loggy_vines_decorator", LoggyVinesDecorator.CODEC);
    public static final TrunkPlacerType<WildJungleTrunkPlacer> WILD_JUNGLE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("immersive_trees:wild_jungle_trunk_placer", WildJungleTrunkPlacer.CODEC);
    public static final FoliagePlacerType<WildJungleFoliagePlacer> WILD_JUNGLE_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("immersive_trees:wild_jungle_foliage_placer", WildJungleFoliagePlacer.CODEC);
    public static void registerDecorators() {
        ImmersiveTrees.LOGGER.debug("Registering decorators for " + ImmersiveTrees.MOD_ID);
    }
}
