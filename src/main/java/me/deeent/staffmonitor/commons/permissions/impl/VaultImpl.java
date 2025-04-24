package me.deeent.staffmonitor.commons.permissions.impl;

import me.deeent.staffmonitor.commons.permissions.PermissionHandler;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class VaultImpl implements PermissionHandler {

    private final JavaPlugin pl;
    private static Permission perms = null;

    public VaultImpl(JavaPlugin plugin) {
        this.pl = plugin;

        load();
    }

    private void load() {
        RegisteredServiceProvider<Permission> rsp = this.pl.getServer().getServicesManager().getRegistration(Permission.class);

        if(rsp != null) {
            perms = rsp.getProvider();
            this.pl.getLogger().info("Vault hooked.");
        }
    }

    @Override
    public String getPrimaryGroup(UUID uuid) {
        OfflinePlayer player = this.pl.getServer().getOfflinePlayer(uuid);

        if(perms != null) {
            String primary = perms.getPrimaryGroup(pl.getServer().getWorlds().get(0).getName(), player);
            return primary == null ? "INCOMPATIBLE PERM PLUGIN" : primary;
        }

        return "INCOMPATIBLE PERM PLUGIN";
    }
}
