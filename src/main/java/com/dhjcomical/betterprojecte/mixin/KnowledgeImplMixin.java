package com.dhjcomical.betterprojecte.mixin;

import com.dhjcomical.betterprojecte.logic.CustomEMCLogic;
import moze_intel.projecte.api.capabilities.IKnowledgeProvider;
import moze_intel.projecte.playerData.Transmutation;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mixin(targets = "moze_intel.projecte.impl.KnowledgeImpl$DefaultImpl", remap = false)
public abstract class KnowledgeImplMixin implements IKnowledgeProvider {

//    static {
//        if (BetterProjectE.LOGGER.isDebugEnabled()) {
//            BetterProjectE.LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            BetterProjectE.LOGGER.info("!!! BetterProjectE Mixin is being loaded successfully! !!!");
//            BetterProjectE.LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        }
//
//    }

    @Shadow @Final
    @Mutable
    private List<ItemStack> knowledge;

    @Shadow private boolean fullKnowledge;

    @Shadow protected abstract void fireChangedEvent();

    /**
     * @author DHJComcial
     * @reason Completely rewrote the core logic of the knowledge base to support accurate matching of NBT items.
     */
    @Overwrite
    public boolean hasKnowledge(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (this.fullKnowledge) {
            return true;
        }

        for (ItemStack s : this.knowledge) {
            if (CustomEMCLogic.isSpecialNBTItem(stack) || CustomEMCLogic.isSpecialNBTItem(s)) {
                if (ItemStack.areItemStacksEqual(s, stack)) {
                    return true;
                }
            } else {
                if (ItemHelper.basicAreStacksEqual(s, stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author DHJComcial
     * @reason Completely rewritten learning logic to support accurate storage of NBT items.
     */
    @Overwrite
    public boolean addKnowledge(@Nonnull ItemStack stack) {

        if (this.hasKnowledge(stack)) {
            return false;
        }

        if (!CustomEMCLogic.isSpecialNBTItem(stack) && moze_intel.projecte.utils.EMCHelper.getEmcValue(stack) <= 0) {
            return false;
        }

        if (stack.getItem() == moze_intel.projecte.gameObjs.ObjHandler.tome) {
            this.setFullKnowledge(true);

            if (!this.hasKnowledge(stack)) {
                this.knowledge.add(stack.copy());
            }
        } else {
            this.knowledge.add(stack.copy());
        }

        this.fireChangedEvent();
        return true;
    }

    /**
     * @author DHJComcial
     * @reason Completely rewritten oblivion logic to support accurate removal of NBT items.
     */
    @Overwrite
    public boolean removeKnowledge(@Nonnull ItemStack stack) {
        boolean removed = false;

        Iterator<ItemStack> iter = this.knowledge.iterator();
        while (iter.hasNext()) {
            ItemStack s = iter.next();
            boolean shouldRemove = false;
            if (CustomEMCLogic.isSpecialNBTItem(stack) || CustomEMCLogic.isSpecialNBTItem(s)) {
                if (ItemStack.areItemStacksEqual(s, stack)) {
                    shouldRemove = true;
                }
            } else {
                if (ItemHelper.basicAreStacksEqual(s, stack)) {
                    shouldRemove = true;
                }
            }

            if (shouldRemove) {
                iter.remove();
                removed = true;
            }
        }

        if (removed) {
            this.fireChangedEvent();
        }
        return removed;
    }

    @Inject(method = "getKnowledge", at = @At("HEAD"), cancellable = true)
    private void onGetKnowledge(CallbackInfoReturnable<List<ItemStack>> cir) {
        if (this.fullKnowledge) {
            cir.setReturnValue(Transmutation.getCachedTomeKnowledge());
        } else {
            List<ItemStack> safeList = new ArrayList<>();
            for (ItemStack stack : this.knowledge) {
                safeList.add(stack.copy());
            }
            cir.setReturnValue(Collections.unmodifiableList(safeList));
        }
    }

}