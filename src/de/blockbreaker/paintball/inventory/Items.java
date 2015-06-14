package de.blockbreaker.paintball.inventory;


import de.blockbreaker.paintball.data.Config;
import de.blockbreaker.paintball.data.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Janne on 02.06.2015.
 */
public class Items {

    public static void getSwitchItem(Player p) {
        //Wenn man ein Spieler ist:
        if(Data.players.contains(p)) {
            ItemStack switchItem = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = switchItem.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Rechtsklick," + ChatColor.BLUE + " um zu den " + ChatColor.DARK_GRAY + "Zuschauern" + ChatColor.BLUE + "  zu wechseln");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.BLUE + "Du bist " + ChatColor.YELLOW + "Spieler");
            meta.setLore(lore);
            //Enchantment adden, wenn man wechseln kann:
            if(Data.players.size() > 4) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
            }
            switchItem.setItemMeta(meta);

            p.getInventory().setItem(8, switchItem);
        }

        //Wenn man Zuschauer ist:
        if(!Data.players.contains(p)) {
            ItemStack switchItem = new ItemStack(Material.STICK);
            ItemMeta meta = switchItem.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Rechtsklick," + ChatColor.BLUE + " um zu den " + ChatColor.YELLOW + "Spielern" + ChatColor.BLUE + " zu wechseln");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Du bist " + ChatColor.DARK_GRAY + "Zuschauer");
            meta.setLore(lore);
            //Enchantment adden, wenn man wechseln kann:
            if(Data.players.size() < Config.cfg.getInt("maxPlayer")) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
            }
            switchItem.setItemMeta(meta);

            p.getInventory().setItem(8, switchItem);
        }
    }







}
