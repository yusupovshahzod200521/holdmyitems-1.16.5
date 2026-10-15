package uz.shahzod.holdmyitems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HoldMyItems.MOD_ID)
public class HoldMyItems {
    public static final String MOD_ID = "holdmyitems";
    private static final Logger LOGGER = LogManager.getLogger();

    public HoldMyItems() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Hold My Items 1.16.5 yuklandi");
    }
}
