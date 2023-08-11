package net.distantdig.treelib;

import net.distantdig.treelib.world.feature.decorators.ModDecorators;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeLib implements ModInitializer {

    public static final String MOD_ID = "treelib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ModDecorators.registerDecorators();
    }
}
