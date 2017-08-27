package me.i3ick.omicron;

import me.i3ick.omicron.commands.MainCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Karlo on 8/2/2016.
 */
public class OmicronMain extends JavaPlugin{

    @Override
    public void onDisable() {

        getLogger().info("Omicron commands disabled!");



    }

    @Override
    public void onEnable() {


        // load world
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getLogger().info("config exists and loaded!");
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
            saveDefaultConfig();
        }

        OmicronExecutor executor = new OmicronExecutor(this);

        // register commands
        this.getCommand("dv").setExecutor(new MainCommands(this, executor));

        // register events
        this.getServer().getPluginManager().registerEvents(new OmicronEvents(this, executor), this);

        PluginDescriptionFile pdf = this.getDescription();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage("\n\n#####~~" + ChatColor.AQUA +" Drunken Valley COMMANDS " + ChatColor.GRAY +"~~##### \n" +
                        "   Drunken Valleyg version " + pdf.getVersion() + " is now running!\n" +
                        "\n\n" + "#####~~~~~~ by" + ChatColor.WHITE +" i3ick " + ChatColor.GRAY +"~~~~~~#####"

        );


    }

}
