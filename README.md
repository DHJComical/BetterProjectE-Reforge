# BetterProjectE - NBT Enhanced

**一个旨在增强等价交换重制版(ProjectE)的模组，赋予其转化桌识别、学习和复制带有NBT数据的物品的能力。**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## 📖 简介

在原版的等价交换重制版([ProjectE](https://github.com/sinkillerj/ProjectE))中，允许所有的NBT物品跳过原来的过滤逻辑加入到转化桌里

现在，你的“锋利 V 经验修补”钻石剑，终于可以在转化系统中拥有一席之地了！

## ✨ 功能特性

*   **NBT学习:**
    *   转化桌现在可以精确地区分并学习带有不同NBT标签的物品。一把“锋利 V”的剑和一把“亡灵杀手 V”的剑，现在是两个完全独立的知识条目。
    *   **满耐久限定:** 只有满耐久的物品才能被学习，确保了EMC系统的平衡性。
*   **EMC计算:**
    *   物品的最终EMC值，将由其**基础EMC**和**NBT数据**共同决定。
    *   **附魔增值:** 物品上的每一个附魔，都会根据其等级和稀有度，为其增加额外的EMC价值。一个神级附魔的工具，现在拥有了它应有的、高昂的EMC价值
    * EMC = 基础值 * 等级^2 (等级越高，价值指数增长)。
*   **复制:**
    *   在转化桌的GUI中，你将能看到所有你已学会的、带有不同NBT的物品，它们都作为独立的选项存在。
    *   你可以选择并复制出任何一个你已学会的NBT物品，只要你有足够的EMC。
*   **遗忘:**
    *   “遗忘”操作现在也能精确识别NBT，允许你只移除知识库中某个特定的NBT版本，而不会影响其他版本。

## 👨‍💻 贡献
- Google Gemini 2.5 Pro（部分代码、README）
- ProjectE（Mod图标）
