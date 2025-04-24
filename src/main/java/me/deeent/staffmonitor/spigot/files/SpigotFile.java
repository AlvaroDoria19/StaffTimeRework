package me.deeent.staffmonitor.spigot.files;

import me.deeent.staffmonitor.commons.files.CustomFile;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.List;

public class SpigotFile implements CustomFile {

    private final FileConfiguration config;

    public SpigotFile(SpigotStaff pl) {
        pl.saveDefaultConfig();

        this.config = pl.getConfig();
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
        ConfigurationSection section = config.getConfigurationSection(path);
        return section != null ? section.getKeys(false) : null;
    }
}
