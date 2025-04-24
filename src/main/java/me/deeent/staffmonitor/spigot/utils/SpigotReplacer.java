package me.deeent.staffmonitor.spigot.utils;

import lombok.AllArgsConstructor;
import me.deeent.staffmonitor.commons.storage.Staff;
import me.deeent.staffmonitor.commons.utils.Replacer;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import me.deeent.staffmonitor.spigot.hooks.PlaceholderHook;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SpigotReplacer implements Replacer {

    private final SpigotStaff plugin;
    private final Player player;
    private final Staff staff;
    private final Object[] extra;

    @Override
    public String replaceAll(String replace) {
        if(replace == null || replace.isEmpty())
            return null;

        String data = replace.replaceAll("%blank%", "\u200b")
                .replaceAll("%player%", player.getName())
                .replaceAll("%uuid%", player.getUniqueId().toString())
                .replaceAll("%rank%", plugin.getPermissions() != null ? plugin.getPermissions().getPrimaryGroup(player.getUniqueId()) : "NO PERMS PLUGIN.")
                .replaceAll("%count%", String.valueOf(plugin.getManager().getStaffCount()))
                .replaceAll("%site%", player.getWorld().getName())
                .replaceAll("%now%", plugin.getEmbedUtils().getMillisReplace(System.currentTimeMillis()))
                .replaceAll("%nl%", "\n");

        if(staff != null)
            data = data.replaceAll("%join%", plugin.getEmbedUtils().getMillisReplace(staff.getJoin()))
                    .replaceAll("%activity%", plugin.getEmbedUtils().getTimeParsed((int) staff.getAllTime()))
                    .replaceAll("%leave%",  plugin.getEmbedUtils().getMillisReplace(staff.getLeave()))
                    .replaceAll("%played%", plugin.getEmbedUtils().getTimeParsed((int) staff.getCurrentActivity()));

        if(extra.length > 0) {
            for (int i = 0; i < extra.length; i += 2) {
                String search = (String) extra[i];
                String rp = (String) extra[i + 1];
                data = data.replaceAll(search, rp);
            }
        }

        return PlaceholderHook.replace(player, data);
    }
}
