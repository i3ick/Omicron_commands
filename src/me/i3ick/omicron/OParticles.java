package me.i3ick.omicron;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Karlo on 8/4/2016.
 */
public class OParticles {
    OmicronMain plugin;

    public OParticles(OmicronMain passPlug1) {
        plugin = passPlug1;
    }


    public void ParticleEffect(Player p, String EffectName) {

        switch (EffectName) {
            case "Heart": Heart(p);
                break;

        }


    }


    public void Heart(Player p) {
        new BukkitRunnable() {
            Location loc = p.getEyeLocation();
            double r = 1;
            Vector direction = loc.getDirection().normalize();

            public void run() {

                for (int t = 0; t < 20; t++) {
                    double z = (Math.cos(t)) / 10;
                    double y = ((13 * Math.cos(t) - 5 * Math.cos(2 * t) - 3 * Math.cos(3 * t) - Math.cos(4 * t)) / 13) + 2;
                    double x = r * Math.sin(t);
                    loc.add(x, y, z);
                    loc.getWorld().playEffect(loc, Effect.HEART, 1);
                    loc.subtract(x, y, z);

                    this.cancel();

                }


            }
        }.runTaskTimer(plugin, 0, 1);


    }



    public void Spire(Player p) {
        new BukkitRunnable() {
            Location loc = p.getEyeLocation();
            double r = 2;
            double t = 0;

            public void run() {

                t = t + Math.PI/16;
                double x = r*Math.cos(t);
                double y = r*Math.sin(t);
                double z = r*Math.sin(t);


                    loc.add(x, y, z);
                    loc.getWorld().playEffect(loc, Effect.FLYING_GLYPH, 1);
                    loc.subtract(x, y, z);
                    this.cancel();
                    if (t > Math.PI * 8) {
                        this.cancel();
                    }




            }
        }.runTaskTimer(plugin, 0, 1);


    }

}
