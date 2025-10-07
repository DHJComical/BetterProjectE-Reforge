package com.dhjcomical.betterprojecte.mixin;

import com.dhjcomical.betterprojecte.BetterProjectE;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TransmutationInventory.class, remap = false)
public class TransmutationInventoryMixin {

    @Redirect(
            method = {"handleKnowledge", "handleUnlearn"},
            at = @At(
                    value = "INVOKE",
                    target = "Lmoze_intel/projecte/utils/NBTWhitelist;shouldDupeWithNBT(Lnet/minecraft/item/ItemStack;)Z"
            )
    )
    private boolean forceAllowAllNBT(ItemStack stack) {
        if (BetterProjectE.enableDebugLogging) {
            BetterProjectE.LOGGER.info("[TransmutationInventoryMixin] Force skipping NBT whitelist check for item: {}", stack.getDisplayName());
        }
        return true;
    }
}