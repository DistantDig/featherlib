package net.distantdig.featherlib.world.feature.decorators;

import net.distantdig.featherlib.FeatherLib;
import net.distantdig.featherlib.mixin.TreeDecoratorTypeInvoker;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ModDecorators {

    public static final TreeDecoratorType<CarpetDecorator> CARPET_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:carpet_decorator", CarpetDecorator.CODEC);
    public static final TreeDecoratorType<HangingVinesDecorator> HANGING_VINES_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:hanging_vines_decorator", HangingVinesDecorator.CODEC);
    public static final TreeDecoratorType<LogReplacerDecorator> LOG_REPLACER_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:thick_trunk_decorator", LogReplacerDecorator.CODEC);
    public static final TreeDecoratorType<SurroundingsDecorator> SURROUNDINGS_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:surroundings_decorator", SurroundingsDecorator.CODEC);
    public static final TreeDecoratorType<ThickTrunkDecorator> THICK_TRUNK_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:log_replacer_decorator", ThickTrunkDecorator.CODEC);
    public static final TreeDecoratorType<WildCocoaDecorator> WILD_COCOA_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:wild_cocoa_decorator", WildCocoaDecorator.CODEC);
    public static final TreeDecoratorType<LoggyVinesDecorator> LOGGY_VINES_DECORATOR = TreeDecoratorTypeInvoker.callRegister("featherlib:loggy_vines_decorator", LoggyVinesDecorator.CODEC);
    public static void registerDecorators() {
        FeatherLib.LOGGER.debug("Registering decorators for " + FeatherLib.MOD_ID);
    }
}
