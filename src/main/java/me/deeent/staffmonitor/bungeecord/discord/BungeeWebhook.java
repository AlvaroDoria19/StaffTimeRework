package me.deeent.staffmonitor.bungeecord.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import me.deeent.staffmonitor.bungeecord.BungeeStaff;
import me.deeent.staffmonitor.bungeecord.utils.BungeeReplacer;
import me.deeent.staffmonitor.commons.storage.Staff;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.Nullable;

public class BungeeWebhook {

    private final BungeeStaff plugin;
    private final WebhookClient client;

    private boolean disable = false;

    public BungeeWebhook(BungeeStaff pl) {
        this.plugin = pl;
        String url = pl.getMainConfig().getString("webhook");

        if(url == null || url.isEmpty() || url.equalsIgnoreCase("none")) {
            this.disable = true;
            this.client = null;
            return;
        }

        this.client = WebhookClient.withUrl(url);
    }

    public void sendEmbed(ProxiedPlayer player, @Nullable Staff staff, EmbedUtils.EmbedType type, Object ...extra) {
        if(disable)
            return;

        BungeeReplacer replacer = new BungeeReplacer(plugin, player, staff, extra);
        WebhookEmbed embed = this.plugin.getEmbedUtils().getEmbedFromConfig(type, replacer);

        this.client.send(embed);
    }

}
