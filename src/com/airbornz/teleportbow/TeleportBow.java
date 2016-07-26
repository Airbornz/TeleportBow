/**
 * Teleport Bow
 * @author Airbornz
 */
package com.airbornz.teleportbow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TeleportBow extends JavaPlugin{

    private static String usePermission = "";

    @Override
    public void onEnable(){
        Bukkit.getLogger().info("Loading config...");
        if (!new File(getDataFolder()+"config.yml").exists())
            this.saveDefaultConfig();
        usePermission = getConfig().getString("permission");
        Bukkit.getPluginManager().registerEvents(new BowListener(), this);
    }

    public static String getUsePermission(){
        return usePermission;
    }

}
