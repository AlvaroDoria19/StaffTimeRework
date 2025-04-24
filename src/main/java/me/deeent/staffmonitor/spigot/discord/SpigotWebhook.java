package me.deeent.staffmonitor.spigot.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import me.deeent.staffmonitor.commons.StaffPlugin;
import me.deeent.staffmonitor.commons.storage.Staff;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import me.deeent.staffmonitor.spigot.utils.SpigotReplacer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class SpigotWebhook {

    private final SpigotStaff plugin;
    private final WebhookClient client;

    private boolean disable = false;

    public SpigotWebhook(SpigotStaff pl) {
        this.plugin = pl;
        String url = pl.getMainConfig().getString("webhook");

        if(url == null || url.isEmpty() || url.equalsIgnoreCase("none")) {
            this.disable = true;
            this.client = null;
            return;
        }

        this.client = WebhookClient.withUrl(url);
    }

    public void sendEmbed(Player player, @Nullable Staff staff, EmbedUtils.EmbedType type, Object ...extra) {
        if(disable)
            return;

        SpigotReplacer replacer = new SpigotReplacer(plugin, player, staff, extra);
        WebhookEmbed embed = this.plugin.getEmbedUtils().getEmbedFromConfig(type, replacer);

        this.client.send(embed);
    }

}
