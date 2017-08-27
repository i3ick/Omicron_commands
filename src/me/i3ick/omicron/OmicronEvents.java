package me.i3ick.omicron;

import net.minecraft.server.v1_11_R1.Village;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.EnderChest;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/**
 * Created by Karlo on 8/2/2016.
 */
public class OmicronEvents implements Listener {

    private OmicronMain plugin;
    private OmicronExecutor executor;


    public OmicronEvents(OmicronMain PassPlug, OmicronExecutor PassPlug2){
        this.plugin = PassPlug;
        this.executor = PassPlug2;
    }


    @EventHandler

    public void nofall(PlayerMoveEvent e){

        if(executor.getPlayerData(e.getPlayer().getName())==null){
            executor.registerPlayer(e.getPlayer());
        }


        Location ploc = e.getPlayer().getLocation();
        int highestblock = ploc.getWorld().getHighestBlockYAt(ploc.getBlockX(), ploc.getBlockZ());
        int disable = highestblock + 3;
        if (ploc.getY() < disable){
            ItemStack chest = new ItemStack(Material.AIR, 1);
            executor.getPlayerData(e.getPlayer().getName()).setGliding(false);
            e.getPlayer().getInventory().setChestplate(chest);
        }

        if(executor.getPlayerData(e.getPlayer().getName()).willGlide()) {
            if (!e.getPlayer().isOnGround()) {
                if(ploc.getY() > disable) {
                    if (e.getPlayer().getVelocity().getY() < 0) {
                        if (!executor.getPlayerData(e.getPlayer().getName()).isGliding()) {
                            ItemStack chest = new ItemStack(Material.ELYTRA, 1);
                            e.getPlayer().getInventory().setChestplate(chest);
                            e.getPlayer().setGliding(true);
                        }
                    }
                }
            }


        }
    }


    public void  runDelayElytra(final Player p) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (!executor.getPlayerData(p.getName()).isGliding()) {
                    ItemStack chest = new ItemStack(Material.ELYTRA, 1);
                    p.getInventory().setChestplate(chest);
                    p.setGliding(true);
                }
            }
        }, 10L);
    }

    @EventHandler
    public void chatevent(PlayerCommandPreprocessEvent e){
        if(executor.getPlayerData(e.getPlayer().getName())==null){
            executor.registerPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void oninventory(PlayerAchievementAwardedEvent e){
        Player p = (Player) e.getPlayer();

        if(executor.getPlayerData(e.getPlayer().getName())==null){
            executor.registerPlayer(e.getPlayer());
        }

        if(!e.getAchievement().equals(Achievement.OPEN_INVENTORY)){
            return;
        }
        e.setCancelled(true);
        if(executor.getPlayerData(e.getPlayer().getName()).isSpectating()){
            String pl = executor.getPlayerData(e.getPlayer().getName()).getSpectated();
            Player player = Bukkit.getServer().getPlayer(pl);
            e.getPlayer().openInventory(player.getInventory());
        }
        Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.ENCHANTING);

        if(!p.hasPermission("omicron.workbench")) {

            ItemStack workbenchPerk = new ItemStack(Material.DIAMOND);
            ItemMeta wbPerk = (ItemMeta)workbenchPerk.getItemMeta();
            wbPerk.setDisplayName("WorkBench -" + ChatColor.YELLOW + " VIP PERK");
            workbenchPerk.setItemMeta(wbPerk);
            p.getInventory().setItem(17, workbenchPerk);
            p.updateInventory();
            return;
        }else{
            ItemStack newItem = new ItemStack(Material.DIAMOND);
            ItemMeta am = (ItemMeta)newItem.getItemMeta();
            am.setDisplayName(ChatColor.LIGHT_PURPLE + "WorkBench");
            newItem.setItemMeta(am);
            p.getInventory().setItem(17, newItem);
            p.updateInventory();

        }

    }

    @EventHandler
    public void oninvClick(InventoryClickEvent e){
        Player pl = (Player) e.getWhoClicked();

        if(!pl.hasPermission("omicron.workbench")) {
            if(e.getSlot() == 17) {
            e.setCancelled(true);
            }
            return;
        }

        if(e.getSlot() == 17){
            e.setCancelled(true);
            InventoryType inv = e.getInventory().getType();
            if(inv == InventoryType.WORKBENCH) {
                Player p = (Player) e.getInventory().getHolder();
                ItemStack newItem = new ItemStack(Material.DIAMOND);
                ItemMeta am = (ItemMeta)newItem.getItemMeta();
                am.setDisplayName(ChatColor.LIGHT_PURPLE + "WorkBench");
                newItem.setItemMeta(am);
                p.getInventory().setItem(17, newItem);
                p.updateInventory();
                p.closeInventory();
                return;
            }else{
                Player p = (Player) e.getInventory().getHolder();
                ItemStack newItem = new ItemStack(Material.DIAMOND);
                ItemMeta am = (ItemMeta)newItem.getItemMeta();
                am.setDisplayName(ChatColor.LIGHT_PURPLE + "WorkBench");
                newItem.setItemMeta(am);
                p.getInventory().setItem(17, newItem);
                p.updateInventory();
            }

                if(!(e.getInventory().getHolder() instanceof Chest)&&!(e.getInventory().getHolder() instanceof DoubleChest)) {
                    if (!(e.getInventory().getHolder() instanceof EnderChest)){
                        Player p = (Player) e.getInventory().getHolder();
                        p.updateInventory();
                      p.openWorkbench(null, true);
                }
                }

        }


    }

    @EventHandler
    public void oninvclose(InventoryCloseEvent e){
        Player p = Bukkit.getServer().getPlayer(e.getPlayer().getName());
        p.removeAchievement(Achievement.OPEN_INVENTORY);
    }

    @EventHandler
    public void  onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.updateInventory();
        executor.registerPlayer(p);
        if(!executor.isTabSet) {
            executor.SetTab(true);
            String header = plugin.getConfig().get("HeaderTabMessage").toString();
            String footer = plugin.getConfig().get("FooterTabMessage").toString();
            executor.sendTabTitle(p, header, footer);
        }
    }

    @EventHandler
    public void onUseItem(PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SADDLE){
            if(e.getAction() == Action.RIGHT_CLICK_AIR){
                Location loc = e.getPlayer().getLocation();
                Sheep shep = (Sheep) e.getPlayer().getWorld().spawnEntity(loc, EntityType.SHEEP);
                Horse pig = (Horse) e.getPlayer().getWorld().spawnEntity(loc, EntityType.HORSE);


                Vector direction = loc.getDirection().normalize().add(Vector.getRandom());
                pig.setInvulnerable(false);
                pig.setPassenger(e.getPlayer());
                pig.setGravity(false);
                pig.setDomestication(3);

            }
        }

        if(e.getAction() == Action.LEFT_CLICK_AIR){
            OParticles particles = new OParticles(plugin);
            particles.Spire(e.getPlayer());

        }


    }





}
