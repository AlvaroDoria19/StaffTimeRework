package me.deeent.staffmonitor.commons.files;

import com.tchristofferson.configupdater.ConfigUpdater;

import java.util.Collection;
import java.util.List;

public interface CustomFile {

    String getString(String path, String def);
    String getString(String path);

    List<String> getStringList(String path);

    int getInt(String path);
    int getInt(String path, int def);

    double getDouble(String path);
    double getDouble(String path, int def);

    boolean getBoolean(String path);
    boolean getBoolean(String path, boolean def);

    boolean contains(String path);

    Collection<String> getKeys(String path);

}
