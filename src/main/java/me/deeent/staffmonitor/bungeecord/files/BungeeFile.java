package me.deeent.staffmonitor.bungeecord.files;

import me.deeent.staffmonitor.commons.StaffPlugin;
import me.deeent.staffmonitor.commons.files.CustomFile;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class BungeeFile implements CustomFile {

    private final Configuration config;

    public BungeeFile(StaffPlugin pl) {
        Configuration con = null;

        try {
            con = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(pl.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            pl.stop();
        }

        this.config = con;
    }

    @Override
    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    @Override
    public int getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    @Override
    public double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public double getDouble(String path, int def) {
        return config.getDouble(path, def);
    }

    @Override
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    @Override
    public boolean contains(String path) {
        return config.contains(path);
    }

    @Override
    public Collection<String> getKeys(String path) {
        Configuration section = config.getSection(path);
        return section != null ? section.getKeys() : null;
    }
}
