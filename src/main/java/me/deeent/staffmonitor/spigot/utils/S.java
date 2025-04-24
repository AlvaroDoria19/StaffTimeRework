package me.deeent.staffmonitor.spigot.utils;

import lombok.experimental.UtilityClass;
import me.deeent.staffmonitor.bungeecord.BungeeStaff;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import org.bukkit.Bukkit;

@UtilityClass
public class S {

    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotStaff.getInstance(), runnable);
    }

}
