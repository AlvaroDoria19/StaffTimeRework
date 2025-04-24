package me.deeent.staffmonitor.bungeecord;

import lombok.Getter;
import me.deeent.staffmonitor.bungeecord.discord.BungeeWebhook;
import me.deeent.staffmonitor.bungeecord.files.BungeeFile;
import me.deeent.staffmonitor.bungeecord.listeners.GeneralListener;
import me.deeent.staffmonitor.bungeecord.managers.BungeeManager;
import me.deeent.staffmonitor.commons.StaffPlugin;
import me.deeent.staffmonitor.commons.files.CustomFile;
import me.deeent.staffmonitor.commons.managers.StaffManager;
import me.deeent.staffmonitor.commons.permissions.PermissionHandler;
import me.deeent.staffmonitor.commons.permissions.impl.LuckpermsImpl;
import me.deeent.staffmonitor.commons.storage.Storage;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Handler;
import java.util.logging.Level;

@Getter
public class BungeeStaff extends Plugin implements StaffPlugin {

    @Getter private static BungeeStaff instance;

    private CustomFile config;
    private Storage storage;

    private BungeeManager manager;
    private PermissionHandler permissions;

    private EmbedUtils embedUtils;
    private BungeeWebhook webhook;


    @Override
    public void onEnable() {
        instance = this;

        try {
            makeConfig();
        } catch (IOException e) {
            getLogger().severe("ERROR on create config. " + e);
            stop();
        }

        this.config = new BungeeFile(this);
        this.storage = new Storage(this);

        this.manager = new BungeeManager(this);
        this.permissions = getPermsHandler();

        this.embedUtils = new EmbedUtils(this);
        this.webhook = new BungeeWebhook(this);

        getProxy().getPluginManager().registerListener(this, new GeneralListener());
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
        if(getProxy().getPluginManager().getPlugin("LuckPerms") != null)
            return new LuckpermsImpl();
        else
            return null;
    }

    public void makeConfig() throws IOException {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CustomFile getMainConfig() {
        return this.config;
    }

    @Override
    public StaffManager getStaffManager() {
        return this.manager;
    }

    @Override
    public void stop() {
        try {
            onDisable();

            for (Handler handler : getLogger().getHandlers()) {
                handler.close();
            }
        } catch (Throwable t) {
            getLogger().log(Level.SEVERE, "Exception disabling the plugin.", t);
        }
        getProxy().getScheduler().cancel(this);
        getExecutorService().shutdownNow();
    }
}
