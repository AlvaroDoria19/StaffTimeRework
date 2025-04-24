package me.deeent.staffmonitor.commons.utils;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import me.deeent.staffmonitor.commons.StaffPlugin;
import me.deeent.staffmonitor.commons.files.CustomFile;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmbedUtils {

    private final StaffPlugin plugin;
    private final Map<EmbedType, WebhookEmbed> cache;

    public EmbedUtils(StaffPlugin pl) {
        this.plugin = pl;
        this.cache = new HashMap<>();
    }

    public WebhookEmbed getEmbedFromConfig(EmbedType type, Replacer replacer) {
        CustomFile file = this.plugin.getMainConfig();
        String path = "embeds." + type.name().toLowerCase();

        WebhookEmbedBuilder builder = new WebhookEmbedBuilder()
                .setDescription(replacer.replaceAll(file.getString(path + ".description")))
                .setThumbnailUrl(replacer.replaceAll(file.getString(path + ".thumbnail")))
                .setImageUrl(replacer.replaceAll(file.getString(path + ".image")))
                .setColor(getColor(file.getString(path + ".color")));

        String title = file.getString(path + ".title");

        if(title != null && !title.isEmpty())
            builder.setTitle(new WebhookEmbed.EmbedTitle(replacer.replaceAll(title), replacer.replaceAll(file.getString(path + ".url"))));

        String footer = file.getString(path + ".footer.text");

        if(footer != null && !footer.isEmpty())
            builder.setFooter(new WebhookEmbed.EmbedFooter(replacer.replaceAll(footer), replacer.replaceAll(file.getString(path + ".footer.icon"))));

        Collection<String> keys = file.getKeys(path + ".fields");

        if(keys != null && !keys.isEmpty()) {
            String fieldPath = path + ".fields.";

            keys.forEach(key -> {
                String name = file.getString(fieldPath + key + ".name");
                String value = file.getString(fieldPath + key + ".value");
                boolean inLine = file.getBoolean(fieldPath + key + ".in-line", false);

                builder.addField(new WebhookEmbed.EmbedField(inLine, replacer.replaceAll(name), replacer.replaceAll(value)));
            });
        }

        return builder.build();
    }

    private int getColor(String config) {
        if(config == null || config.isEmpty())
            return 0;

        try {
            return Color.decode(config).getRGB();
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public boolean isEmbedEnabled(EmbedType type) {
        CustomFile config = this.plugin.getMainConfig();
        String name = type.toString().toLowerCase();

        return !config.contains("embeds-enabled." + name) || config.getBoolean("embeds-enabled." + name);
    }

    public String getMillisReplace(long millis) {
        CustomFile config = this.plugin.getMainConfig();
        return config.getString("format-date", "<t:%time%>").replaceAll("%time%", String.valueOf((millis / 1000)));
    }

    public String getTimeParsed(int time) {
        CustomFile config = this.plugin.getMainConfig();
        int seconds, minutes, hours, days;

        time = time / 1000;
        seconds = time % 60;
        time /= 60;
        minutes = time % 60;
        time /= 60;
        hours = time % 24;
        time /= 24;
        days = time;

        StringBuilder result = new StringBuilder();

        if(days > 0)
            result.append(days).append(config.getString("format-time.days")).append(" ");
        if(hours > 0)
            result.append(hours).append(config.getString("format-time.hours")).append(" ");
        if(minutes > 0)
            result.append(minutes).append(config.getString("format-time.minutes")).append(" ");
        if(seconds > 0)
            result.append(seconds).append(config.getString("format-time.seconds")).append(" ");

        if(result.toString().isEmpty()) return "0" + config.getString("format-time.seconds");
        else return result.toString();
    }

    public enum EmbedType {
        JOIN, LEAVE, COMMAND
    }

}
