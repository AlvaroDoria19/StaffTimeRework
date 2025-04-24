package me.deeent.staffmonitor.commons.permissions.impl;

import me.deeent.staffmonitor.commons.permissions.PermissionHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

import java.util.UUID;

public class LuckpermsImpl implements PermissionHandler {

    private final LuckPerms api;

    public LuckpermsImpl() {
        this.api = LuckPermsProvider.get();
    }

    @Override
    public String getPrimaryGroup(UUID uuid) {
        User user = api.getUserManager().getUser(uuid);

        if(user == null)
            return "";

        return user.getPrimaryGroup();
    }
}
