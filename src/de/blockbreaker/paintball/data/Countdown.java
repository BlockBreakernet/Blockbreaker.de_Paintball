package de.blockbreaker.paintball.data;

import de.blockbreaker.paintball.Paintball;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;


/**
 * Created by Janne on 01.06.2015.
 */
public class Countdown{

    public static void startCountdown() {
        if(GameState.isState(GameState.IN_LOBBY)) {
            if(Bukkit.getOnlinePlayers().size() == 4) {

                 Paintball.getInstance().countdownID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Paintball.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        //Standard durchlauf:
                        if(Data.counter == 60 || Data.counter == 45 || Data.counter == 30 || Data.counter == 20 || Data.counter == 10 || Data.counter == 3 || Data.counter == 2) {
                            Bukkit.broadcastMessage(Data.Prefix + ChatColor.BLUE + "Das Match startet in " + ChatColor.GREEN + Data.counter + " Sekunden");
                            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 21));
                        }

                        if(Data.counter == 1) {
                            Bukkit.broadcastMessage(Data.Prefix + ChatColor.RED + "Das Match startet " + ChatColor.DARK_RED + "jetzt");
                            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 21));
                        }

                        if(Data.counter == 0) {
                            Bukkit.getOnlinePlayers().forEach(player -> player.setExp(0));
                            Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(0));
                            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 35));
                            GameState.setState(GameState.IN_GAME);
                            //inv getten wichtig nach ingam ^^^^^!!!
                            //tpen
                            Bukkit.getScheduler().cancelTask(Paintball.getInstance().countdownID);
                        }

                        Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(Data.counter));
                        Bukkit.getOnlinePlayers().forEach(player -> player.setExp(Data.counter/60/100));//TODO: Die EP leiste l�uft noch nich
                        Data.counter --;
                    }
                }, 0, 20);
            }
        }


        if(GameState.isState(GameState.POST_GAME)) {
            Bukkit.getScheduler().scheduleAsyncRepeatingTask(Paintball.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Data.counter = 10;

                    if(Data.counter != 0) {
                        Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(Data.counter));
                        Bukkit.getOnlinePlayers().forEach(player -> player.setExp(Data.counter/10));
                        Data.counter --;
                    }

                    if(Data.counter == 0) {
                        Bukkit.getScheduler().cancelAllTasks();
                        Bukkit.shutdown();
                    }
                }
            }, 0, 20);
        }
    }
}