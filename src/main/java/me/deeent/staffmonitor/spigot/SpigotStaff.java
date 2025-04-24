package me.deeent.staffmonitor.spigot;

import lombok.Getter;
import me.deeent.staffmonitor.commons.StaffPlugin;
import me.deeent.staffmonitor.commons.files.CustomFile;
import me.deeent.staffmonitor.commons.managers.StaffManager;
import me.deeent.staffmonitor.commons.permissions.PermissionHandler;
import me.deeent.staffmonitor.commons.permissions.impl.LuckpermsImpl;
import me.deeent.staffmonitor.commons.permissions.impl.VaultImpl;
import me.deeent.staffmonitor.commons.storage.Storage;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import me.deeent.staffmonitor.spigot.discord.SpigotWebhook;
import me.deeent.staffmonitor.spigot.files.SpigotFile;
import me.deeent.staffmonitor.spigot.hooks.PlaceholderHook;
import me.deeent.staffmonitor.spigot.listeners.GeneralListener;
import me.deeent.staffmonitor.spigot.managers.SpigotManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SpigotStaff extends JavaPlugin implements StaffPlugin {

    @Getter private static SpigotStaff instance;

    private CustomFile mainConfig;
    private Storage storage;

    private SpigotManager manager;
    private PermissionHandler permissions;

    private EmbedUtils embedUtils;
    private SpigotWebhook webhook;

    @Override
    public void onEnable() {
        instance = this;

        this.mainConfig = new SpigotFile(this);
        this.storage = new Storage(this);

        this.manager = new SpigotManager(this);
        this.permissions = getPermsHandler();

        this.embedUtils = new EmbedUtils(this);
        this.webhook = new SpigotWebhook(this);

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHook(this).register();
        }

        getServer().getPluginManager().registerEvents(new GeneralListener(), this);
    }

    @Override
    public void onDisable() {
        if(this.manager != null) {
            this.manager.saveAll();
        }

        if(this.storage != null) {
            this.storage.close();
        }
    }

    public PermissionHandler getPermsHandler() {
        if(getServer().getPluginManager().isPluginEnabled("LuckPerms"))
            return new LuckpermsImpl();
        else if(getServer().getPluginManager().isPluginEnabled("Vault"))
            return new VaultImpl(this);
        else
            return null;
    }

    @Override
    public CustomFile getMainConfig() {
        return this.mainConfig;
    }

    @Override
    public StaffManager getStaffManager() {
        return this.manager;
    }

    @Override
    public void stop() {
        this.getServer().getPluginManager().disablePlugin(this);
    }
}
