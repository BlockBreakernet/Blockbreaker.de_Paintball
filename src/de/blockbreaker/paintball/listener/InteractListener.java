package de.blockbreaker.paintball.listener;

import de.blockbreaker.paintball.api.PlayerAPI;
import de.blockbreaker.paintball.data.Data;
import de.blockbreaker.paintball.inventory.Items;
import de.blockbreaker.paintball.inventory.StandartInventory;
import de.blockbreaker.paintball.inventory.TeamInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Janne on 14.06.2015.
 */
public class InteractListener implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            //von Spielern zu Zuschauern wechseln
            if(e.getMaterial().equals(Material.BLAZE_ROD)) {
                if(Data.players.size() > 4) {
                    Data.players.remove(p);
                    if(Data.teamGreen.contains(p)) {
                        Data.teamGreen.remove(p);
                    }
                    if(Data.teamOrange.contains(p)) {
                        Data.teamOrange.remove(p);
                    }
                    Bukkit.getOnlinePlayers().forEach(player -> StandartInventory.refreshSwitchItem(player));
                    p.sendMessage(Data.Prefix + ChatColor.BLUE + "Du bist nun " + ChatColor.DARK_GRAY + "Zuschauer");
                } else {
                    p.sendMessage(Data.Prefix + ChatColor.BLUE + "Du kannst leider nicht wechseln, da es sonst " + ChatColor.DARK_BLUE + "zu wenig Spieler" + ChatColor.BLUE + " gibt!");
                }
                p.getInventory().setBoots(null);
            }

            //Von Zuschauern zu Spielern wechseln
            if(e.getMaterial().equals(Material.STICK)) {
                if(Data.players.size() < Data.maxPlayer) {
                    Data.players.add(p);
                    StandartInventory.getStandardInventory(p);
                    Bukkit.getOnlinePlayers().forEach(player -> StandartInventory.refreshSwitchItem(player));
                    p.sendMessage(Data.Prefix + ChatColor.BLUE + "Du bist nun " + ChatColor.YELLOW + "Spieler");
                }
                else {
                    p.sendMessage(Data.Prefix + ChatColor.BLUE + "Du kannst leider nicht wechseln, da schon " + ChatColor.DARK_BLUE + "alle Slots belegt " + ChatColor.BLUE + " sind!");
                }
            }

            //Mit dem Leave Item in die Lobby springen
            if(e.getMaterial().equals(Material.EMERALD)) {
                if(Data.players.contains(p)) {
                    Data.players.remove(p);
                }
                PlayerAPI.connectLobby(p);
            }

            //Team Inventar �ffnen
            if(e.getMaterial().equals(Material.SNOW_BALL)) {
                TeamInventory.open(p);
                Items.getTeamItem(p);
                p.updateInventory();
            }
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);

        //Gr�nes Team betreten:
        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Team 1")) {
            if(Data.teamGreen.contains(p)) {
                p.sendMessage(Data.Prefix + ChatColor.RED + "Du bist schon Mitglied in " + ChatColor.GREEN + "Team 1");
                p.updateInventory();
            } else if(Data.teamGreen.size() < Data.maxPlayer/2) {
                if(Data.teamOrange.contains(p)) {
                    Data.teamOrange.remove(p);
                    p.updateInventory();
                }
                Data.teamGreen.add(p);
                p.sendMessage(Data.Prefix + ChatColor.BLUE + "Du bist nun Mitglied in " + ChatColor.GREEN + "Team 1");
                Items.getTeamBoots(p);
                p.updateInventory();
            } else {
                p.sendMessage(Data.Prefix + ChatColor.GREEN + "Team 1 " + ChatColor.RED + "ist bereits voll");
                p.updateInventory();
            }
            Items.getTeamItem(p);
            p.updateInventory();
            p.closeInventory();
        }

        //Oranges Team betreten:
        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Team 2")) {
            if(Data.teamOrange.contains(p)) {
                p.sendMessage(Data.Prefix + ChatColor.RED + "Du bist schon Mitglied in " + ChatColor.GOLD + "Team 2");
                p.updateInventory();
            } else if(Data.teamOrange.size() < Data.maxPlayer/2) {
                if(Data.teamGreen.contains(p)) {
                    Data.teamGreen.remove(p);
                    p.updateInventory();
                }
                Data.teamOrange.add(p);
                p.sendMessage(Data.Prefix + ChatColor.BLUE + "Du bist nun Mitglied in " + ChatColor.GOLD + "Team 2");
                Items.getTeamBoots(p);
                p.updateInventory();
            } else {
                p.sendMessage(Data.Prefix + ChatColor.GOLD + "Team 2 " + ChatColor.RED + "ist bereits voll");
                p.updateInventory();
            }
            Items.getTeamItem(p);
            p.updateInventory();
            p.closeInventory();
        }
    }
}
