package me.i3ick.omicron;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Karlo on 8/2/2016.
 */
public class OmicornPlayerInfo {


    private OmicronExecutor executor;

    public OmicornPlayerInfo(OmicronExecutor PassPlug){
        this.executor = PassPlug;
    }




    private String name;
    private String spectatorVictim;
    private GameMode gamemode;
    private Location spectatingLocation;
    private int killcount;



    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }


    private boolean isGliding = false;
    public boolean isGliding(){return isGliding;}
    public void setGliding(boolean isGliding){
        this.isGliding = isGliding;
    }

    private boolean willGlide = false;
    public boolean willGlide(){return willGlide;}
    public void setGlide(boolean canGlide){
        this.willGlide = canGlide;
    }

    private boolean isSpectating = false;
    public boolean isSpectating(){return isSpectating;}
    public void setSpectating(boolean spectating){
        this.isSpectating = spectating;
    }

    public void spectating(String p){
        this.spectatorVictim = p;
    }

    public String getSpectated(){
        return spectatorVictim;
    }

    public void saveGamemode(GameMode mode){
        this.gamemode = mode;
    }

    public GameMode getGamemode(){
        return gamemode;
    }

    public void saveSpectatingLocation(Location loc){
        this.spectatingLocation = loc;
    }

    public Location getSpectatingLocation(){
        return spectatingLocation;
    }


}
