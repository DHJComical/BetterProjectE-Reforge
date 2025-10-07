package com.dhjcomical.betterprojecte;

import com.dhjcomical.betterprojecte.network.UpdateClientTransmutationPKT;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "required-after:projecte")
public class BetterProjectE {
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_ID);

    public static boolean enableDebugLogging = false;

    public static final SimpleNetworkWrapper HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        HANDLER.registerMessage(UpdateClientTransmutationPKT.Handler.class, UpdateClientTransmutationPKT.class, 0, Side.CLIENT);
    }

}