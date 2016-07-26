/**
 * Teleport Bow
 * @author Airbornz
 */
package com.airbornz.teleportbow;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BowListener implements Listener{

    private Map<UUID, Location> origins = new HashMap<>();

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onHit(ProjectileHitEvent event){
        if (event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getShooter() instanceof Player){
                Player shooter = (Player) arrow.getShooter();
                if (TeleportBow.getUsePermission().equals("") || shooter.hasPermission(TeleportBow.getUsePermission())){
                    shooter.teleport(arrow.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST) //Highest so it can be cancelled by Monitor
    public void onShot(ProjectileLaunchEvent event){
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getShooter() instanceof Player){
                Player shooter = (Player) arrow.getShooter();
                if (TeleportBow.getUsePermission().equals("") || shooter.hasPermission(TeleportBow.getUsePermission())){
                    origins.put(shooter.getUniqueId(), shooter.getLocation());
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event){
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)){
            Player player = event.getPlayer();
            if (origins.containsKey(player.getUniqueId())){
                if (event.isCancelled()){
                    player.teleport(origins.get(player.getUniqueId()));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 0);
                    player.sendMessage(ChatColor.RED+"You cannot teleport there!");
                }
                origins.remove(player.getUniqueId());
            }
        }
    }
}
