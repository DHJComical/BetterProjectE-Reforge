package com.dhjcomical.betterprojecte.network;

import io.netty.buffer.ByteBuf;
import moze_intel.projecte.gameObjs.container.TransmutationContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UpdateClientTransmutationPKT implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<UpdateClientTransmutationPKT, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(UpdateClientTransmutationPKT message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Container container = Minecraft.getMinecraft().player.openContainer;
                if (container instanceof TransmutationContainer) {
                    ((TransmutationContainer) container).transmutationInventory.updateClientTargets();
                }
            });
            return null;
        }
    }
}