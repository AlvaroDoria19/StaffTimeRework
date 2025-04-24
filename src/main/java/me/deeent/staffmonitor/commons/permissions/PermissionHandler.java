package me.deeent.staffmonitor.commons.permissions;

import java.util.UUID;

public interface PermissionHandler {

    String getPrimaryGroup(UUID uuid);

}
