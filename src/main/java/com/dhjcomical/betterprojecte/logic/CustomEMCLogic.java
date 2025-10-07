package com.dhjcomical.betterprojecte.logic;

import com.dhjcomical.betterprojecte.BetterProjectE;
import moze_intel.projecte.api.ProjectEAPI;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.Map;

public class CustomEMCLogic {

    public static long calculate(ItemStack stack) {
        if (stack.isEmpty() || stack.isItemDamaged() || !stack.isItemEnchanted()) {
            return ProjectEAPI.getEMCProxy().getValue(stack);
        }

        long totalEmc = ProjectEAPI.getEMCProxy().getValue(stack);
        if (totalEmc <= 0) {
            return 0;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            long enchantEmc = getEmcForEnchantment(enchantment, level);
            totalEmc += enchantEmc;
        }

        return totalEmc;
    }

    public static boolean isSpecialNBTItem(ItemStack stack) {
        if (BetterProjectE.enableDebugLogging) {
            BetterProjectE.LOGGER.info("--- Checking isSpecialNBTItem for: {} ---", stack.getDisplayName());
            BetterProjectE.LOGGER.info("--- Item NBT: {}", stack.getTagCompound());
        }

        if (stack.isEmpty()) {
            if (BetterProjectE.enableDebugLogging) BetterProjectE.LOGGER.info("Check FAILED: Item is empty.");
            return false;
        }

        if (stack.isItemDamaged()) {
            if (BetterProjectE.enableDebugLogging) BetterProjectE.LOGGER.info("Check FAILED: Item is damaged! Damage value: " + stack.getItemDamage());
            return false;
        }

        if (!stack.hasTagCompound()) {
            if (BetterProjectE.enableDebugLogging) BetterProjectE.LOGGER.info("Check FAILED: Item has no NBTTagCompound.");
            return false;
        }

        if (!stack.getTagCompound().hasKey("ench", Constants.NBT.TAG_LIST)) {
            if (BetterProjectE.enableDebugLogging) BetterProjectE.LOGGER.info("Check FAILED: NBTTagCompound does not contain 'ench' tag list.");
            return false;
        }

        long baseEmc = ProjectEAPI.getEMCProxy().getValue(stack);
        if (baseEmc <= 0) {
            if (BetterProjectE.enableDebugLogging) BetterProjectE.LOGGER.info("Check FAILED: Item has no base EMC value. Base EMC: " + baseEmc);
            return false;
        }

        if (BetterProjectE.enableDebugLogging) BetterProjectE.LOGGER.info("Check PASSED! This is a special NBT item.");
        return true;
    }

    private static long getEmcForEnchantment(Enchantment enchantment, int level) {
        ResourceLocation regName = enchantment.getRegistryName();
        if (regName == null) return 0;

        long baseEmc = 0;
        switch (regName.toString()) {
            case "minecraft:protection":
            case "minecraft:sharpness":
            case "minecraft:efficiency":
            case "minecraft:power":
            case "minecraft:looting":
                baseEmc = 2000;
                break;
            case "minecraft:unbreaking":
                baseEmc = 1500;
                break;
            case "minecraft:silk_touch":
                baseEmc = 30000;
                break;
            case "minecraft:fortune":
                baseEmc = 5000;
                break;
            case "minecraft:mending":
                baseEmc = 200000;
                break;
            default:
                switch (enchantment.getRarity()) {
                    case COMMON: baseEmc = 500; break;
                    case UNCOMMON: baseEmc = 1000; break;
                    case RARE: baseEmc = 2000; break;
                    case VERY_RARE: baseEmc = 4000; break;
                }
        }
        // EMC = 基础值 * 等级^2 (等级越高，价值指数增长)
        return baseEmc * (long)Math.pow(level, 2);
    }
}