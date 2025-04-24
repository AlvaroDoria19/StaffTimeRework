package me.deeent.staffmonitor.spigot.hooks;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.deeent.staffmonitor.commons.storage.Staff;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class PlaceholderHook extends PlaceholderExpansion {

    private final SpigotStaff plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "monitor";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Deeent";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player == null || !player.isOnline())
            return null;

        Staff staff = plugin.getStaffManager().getStaff(player.getUniqueId());

        if(staff == null)
            return null;

        switch (params.toLowerCase()) {
            case "time": {
                return SpigotStaff.getInstance().getEmbedUtils().getTimeParsed((int) staff.getCurrentActivity());
            }
            default:
                return null;
        }
    }

    public static String replace(Player player, String text) {
        if(text == null || text.isEmpty())
            return text;

        if(Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }

        return text;
    }

}
