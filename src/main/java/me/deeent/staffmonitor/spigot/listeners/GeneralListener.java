package me.deeent.staffmonitor.spigot.listeners;

import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import me.deeent.staffmonitor.spigot.managers.SpigotManager;
import me.deeent.staffmonitor.spigot.utils.S;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class GeneralListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission(SpigotStaff.getInstance().getMainConfig().getString("staff-permission")))
            S.runAsync(() -> SpigotStaff.getInstance().getManager().addStaff(player));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SpigotManager manager = SpigotStaff.getInstance().getManager();

        if(manager.isStaff(player.getUniqueId()))
            S.runAsync(() -> manager.removeStaff(player));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(!SpigotStaff.getInstance().getEmbedUtils().isEmbedEnabled(EmbedUtils.EmbedType.COMMAND))
            return;

        Player player = event.getPlayer();
        List<String> commands = SpigotStaff.getInstance().getMainConfig().getStringList("forbidden-commands");
        String command = event.getMessage().replaceAll("\\s+", " ").replaceAll("^/", "").toLowerCase();

        if(commands.stream().anyMatch(command::contains)) {
            S.runAsync(() ->
                    SpigotStaff.getInstance().getWebhook().sendEmbed(player, null, EmbedUtils.EmbedType.COMMAND,
                            "%command%", command
                    )
            );
        }
    }

}
