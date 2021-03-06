package de.blockbreaker.paintball;

import de.blockbreaker.paintball.data.Config;
import de.blockbreaker.paintball.data.Data;
import de.blockbreaker.paintball.data.GameState;
import de.blockbreaker.paintball.data.RegisterUtil;
import de.blockbreaker.paintball.listener.CancelledEvents;
import de.blockbreaker.paintball.listener.InteractListener;
import de.blockbreaker.paintball.listener.JoinListener;
import de.blockbreaker.paintball.listener.LeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static de.blockbreaker.paintball.data.GameState.*;

/**
 * Created by Janne on 01.06.2015.
 */
public class Paintball extends JavaPlugin{

    //Instance:
    private static Paintball instance;

    //TaskID�s:
    public int countdownID;
    public int teamInventoryID;


    public void onEnable() {

        //Events Registrieren:
        RegisterUtil<Paintball> register = new RegisterUtil<Paintball>(this);
        register.registerEvents(JoinListener.class, LeaveListener.class, InteractListener.class, CancelledEvents.class);

        //Config erstellen:
        Config.createConfig();

        //GameState:
        GameState.setState(IN_LOBBY);

        //Instance:
        instance = this;

        //Registriert PluginChannel
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //Konsolenausgabe:
        System.out.println(" =-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(Data.Prefix + ChatColor.GREEN + "Erfolgreich geladen!");
        System.out.println(" =-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(Data.BlockBreaker);
        System.out.println(" =-=-=-=-=-=-=-=-=-=-=-=");
    }



    public void onDisable() {

        //Konsolenausgabe:
        System.out.println(" =-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(Data.Prefix + ChatColor.DARK_RED +  "Nicht geladen!!!");
        System.out.println(" =-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(Data.BlockBreaker);
        System.out.println(" =-=-=-=-=-=-=-=-=-=-=-=");
    }



    //Instance:
    public static Paintball getInstance () {
        return instance;
    }
}
