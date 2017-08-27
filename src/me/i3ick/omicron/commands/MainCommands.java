package me.i3ick.omicron.commands;

import me.i3ick.omicron.OmicronEvents;
import me.i3ick.omicron.OmicronExecutor;
import me.i3ick.omicron.OmicronMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Karlo on 8/2/2016.
 */
public class MainCommands implements CommandExecutor{

    OmicronMain plugin;
    OmicronExecutor executor;
    SubCommands subcmnds;


    public MainCommands(OmicronMain passPlug1, OmicronExecutor passPlug2){
        this.plugin = passPlug1;
        this.executor = passPlug2;
        this.subcmnds = new SubCommands(passPlug1, passPlug2);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0 || args[0].equalsIgnoreCase("help")){
            if(sender.hasPermission("winterslash.*")){
            }
            else if(sender.hasPermission("winterslashplayers.*")){
            }
            else{
                player.sendMessage(ChatColor.YELLOW + "Proper forumalation is: /dv help");
            }
            return false;
        }


        if (args[0].equalsIgnoreCase("spectate")) {
            if(!sender.hasPermission("omicron.spectate")){
                sender.sendMessage("No permission!");
                return true;
            }

            if ((args.length == 1)) {
                subcmnds.endSpectate(player);
                return true;
            }

            if ((args.length == 2)) {
                subcmnds.spectate(player, Bukkit.getPlayer(args[1]));
                return true;
            }
            player.sendMessage(ChatColor.YELLOW + "Proper forumalation is: /dv spectate <player>");
            return true;
        }


        if (args[0].equalsIgnoreCase("glide")) {
            if(!sender.hasPermission("omicron.glide")){
                sender.sendMessage("No permission!");
                return true;
            }
            if ((args.length == 1)) {
                subcmnds.glide(player);
                return true;
            }
            player.sendMessage(ChatColor.YELLOW + "Proper forumalation is: /dv glide");
            return true;
        }

        if (args[0].equalsIgnoreCase("tab")) {
            if (!sender.hasPermission("omicron.tab")) {
                sender.sendMessage("No permission!");
                return true;
            }
            if ((args.length == 1)) {
                subcmnds.header(player);
                return true;
            }
            player.sendMessage(ChatColor.YELLOW + "Proper forumalation is: /dv tab");
            return true;

        }


        if (args[0].equalsIgnoreCase("particles")) {
            if (!sender.hasPermission("omicron.particle")) {
                sender.sendMessage("No permission!");
                return true;
            }

            subcmnds.particle();

        }


        return false;
    }
}
