package me.deeent.staffmonitor.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.deeent.staffmonitor.commons.files.CustomFile;
import me.deeent.staffmonitor.commons.managers.StaffManager;
import me.deeent.staffmonitor.commons.permissions.PermissionHandler;

import java.io.File;
import java.util.logging.Logger;

public interface StaffPlugin {

    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    PermissionHandler handler = null;

    CustomFile getMainConfig();

    File getDataFolder();

    Logger getLogger();

    StaffManager getStaffManager();

    void stop();

}
