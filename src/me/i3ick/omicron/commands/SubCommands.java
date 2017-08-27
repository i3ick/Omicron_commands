package me.i3ick.omicron.commands;

import me.i3ick.omicron.OmicornPlayerInfo;
import me.i3ick.omicron.OmicronExecutor;
import me.i3ick.omicron.OmicronMain;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Karlo on 8/2/2016.
 */
public class SubCommands {

    OmicronMain plugin;
    OmicronExecutor executor;

    public SubCommands(OmicronMain passPlugin, OmicronExecutor passPlug2) {
        this.plugin = passPlugin;
        this.executor = passPlug2;

    }

    public void spectate(Player spectator, Player target){

        if(!Bukkit.getOnlinePlayers().contains(target)){
            spectator.sendMessage("Sorry, player isn't online.");
            return;
        }

        if(target == spectator){
            spectator.sendMessage("Can't spectate self.");
            return;
        }

        OmicornPlayerInfo pData = executor.getPlayerData(spectator.getName());
        pData.saveGamemode(spectator.getGameMode());
        pData.saveSpectatingLocation(spectator.getLocation());
        pData.setSpectating(true);
        pData.spectating(target.getName());

        spectator.setGameMode(GameMode.SPECTATOR);
        spectator.setSpectatorTarget(target);

    }


    public void endSpectate(Player spectator){
        String target = "";
        OmicornPlayerInfo pData = executor.getPlayerData(spectator.getName());
        spectator.setGameMode(pData.getGamemode());
        spectator.teleport(pData.getSpectatingLocation());
        pData.setSpectating(false);
        pData.spectating(target);
    }

    public void glide(Player p){
        if(executor.getPlayerData(p.getName()).willGlide()){
            executor.getPlayerData(p.getName()).setGlide(false);
            p.sendMessage("off");
        }else{
            executor.getPlayerData(p.getName()).setGlide(true);
            p.sendMessage("on");
        }
    }

    public void header(Player p){
        String header = "\\u00a7a\\u2583\\u2584\\u2586\\u00a7e \\u27a1 You are now playing on\\u00a7r \\u00a7bDrunken Valley!\\u00a7r \\u00a7e\\u2b05 \\u00a7a\\u2586\\u2585\\u2584\\u00a7r";
        String footer = "\\u00a7e\\u22d9 \\u263b Thank you and come again! \\u263b \\u22d8\\u00a7r";
        executor.sendTabTitle(p, header, footer);
    }

    public void particle(){

    }





}
