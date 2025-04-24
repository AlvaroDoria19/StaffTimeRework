package me.deeent.staffmonitor.bungeecord.listeners;

import me.deeent.staffmonitor.bungeecord.BungeeStaff;
import me.deeent.staffmonitor.bungeecord.managers.BungeeManager;
import me.deeent.staffmonitor.bungeecord.utils.S;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class GeneralListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if(player.hasPermission(BungeeStaff.getInstance().getConfig().getString("staff-permission")))
            S.runAsync(() -> BungeeStaff.getInstance().getManager().addStaff(player));
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        BungeeManager manager = BungeeStaff.getInstance().getManager();

        if(manager.isStaff(player.getUniqueId()))
            S.runAsync(() -> manager.removeStaff(player));
    }

    @EventHandler
    public void onCommand(ChatEvent event) {
        if(!event.isCommand() && !event.isProxyCommand())
            return;
        else if(!(event.getSender() instanceof ProxiedPlayer))
            return;
        else if(!BungeeStaff.getInstance().getEmbedUtils().isEmbedEnabled(EmbedUtils.EmbedType.COMMAND))
            return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        List<String> commands = BungeeStaff.getInstance().getConfig().getStringList("forbidden-commands");
        String command = event.getMessage().replaceAll("\\s+", " ").replaceAll("^/", "").toLowerCase();

        if(commands.stream().anyMatch(command::contains)) {
            S.runAsync(() ->
                    BungeeStaff.getInstance().getWebhook().sendEmbed(player, null, EmbedUtils.EmbedType.COMMAND,
                            "%command%", command
                    )
            );
        }
    }

}
