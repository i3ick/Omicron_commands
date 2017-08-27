package me.i3ick.omicron;

import com.avaje.ebeaninternal.server.cluster.Packet;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutBoss;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Karlo on 8/2/2016.
 */
public class OmicronExecutor {

    static OmicronMain plugin;

    public OmicronExecutor(OmicronMain passPlugin){
        OmicronExecutor.plugin = passPlugin;
    }


    public HashMap<String, OmicornPlayerInfo> stats = new HashMap<String, OmicornPlayerInfo>();
    public boolean isTabSet = false;
    public void SetTab(boolean set){this.isTabSet = set;}

    public OmicornPlayerInfo getPlayerData(String name){
        OmicornPlayerInfo obj = null;
        if(this.stats.containsKey(name)){
            obj = this.stats.get(name);
        }
        return obj;
    }

    public void registerPlayer(Player pl){
        OmicornPlayerInfo stats = new OmicornPlayerInfo(this);
        stats.setName(pl.getName());
        addToPlayerHash(pl.getName(), stats);
    }

    public void addToPlayerHash(String name, OmicornPlayerInfo data){
        stats.put(name, data);
    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null) header = "";
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null) footer = "";
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());


        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);




        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }



}
