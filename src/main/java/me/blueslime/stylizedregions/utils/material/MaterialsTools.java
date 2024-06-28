package me.blueslime.stylizedregions.utils.material;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class MaterialsTools {
    public static boolean containsInventory(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        return containsInventory(itemStack.getType());
    }

    public static boolean containsInventory(final Material material) {
        String name = material.toString().toLowerCase(Locale.ENGLISH);
        return name.contains("chest")
                || name.contains("jukebox")
                || name.contains("dispenser")
                || name.contains("furnace")
                || name.contains("brewing_stand")
                || name.contains("trapped_chest")
                || name.contains("hopper")
                || name.contains("dropper")
                || name.contains("barrel")
                || name.contains("blast_furnace")
                || name.contains("smoker")
                || name.contains("chiseled_bookshelf");
    }
}
