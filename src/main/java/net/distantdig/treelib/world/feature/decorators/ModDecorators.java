package net.distantdig.treelib.world.feature.decorators;

import net.distantdig.treelib.TreeLib;
import net.distantdig.treelib.mixin.TreeDecoratorTypeInvoker;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ModDecorators {

    public static final TreeDecoratorType<CarpetDecorator> CARPET_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:carpet_decorator", CarpetDecorator.CODEC);
    public static final TreeDecoratorType<HangingVinesDecorator> HANGING_VINES_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:hanging_vines_decorator", HangingVinesDecorator.CODEC);
    public static final TreeDecoratorType<LogReplacerDecorator> LOG_REPLACER_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:thick_trunk_decorator", LogReplacerDecorator.CODEC);
    public static final TreeDecoratorType<SurroundingsDecorator> SURROUNDINGS_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:surroundings_decorator", SurroundingsDecorator.CODEC);
    public static final TreeDecoratorType<ThickTrunkDecorator> THICK_TRUNK_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:log_replacer_decorator", ThickTrunkDecorator.CODEC);
    public static final TreeDecoratorType<WildCocoaDecorator> WILD_COCOA_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:wild_cocoa_decorator", WildCocoaDecorator.CODEC);
    public static final TreeDecoratorType<LoggyVinesDecorator> LOGGY_VINES_DECORATOR = TreeDecoratorTypeInvoker.callRegister("treelib:loggy_vines_decorator", LoggyVinesDecorator.CODEC);

    public static void registerDecorators() {
        TreeLib.LOGGER.debug("Registering decorators for " + TreeLib.MOD_ID);
    }
}
