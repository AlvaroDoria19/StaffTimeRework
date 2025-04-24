package me.deeent.staffmonitor.commons.storage;

import me.deeent.staffmonitor.commons.StaffPlugin;
import me.deeent.staffmonitor.commons.files.CustomFile;
import me.deeent.staffmonitor.commons.utils.DependencyManager;
import me.deeent.staffmonitor.commons.utils.IsolatedClassLoader;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class Storage {

    private static StaffPlugin plugin;
    
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String pass;

    private final boolean mysql;
    
    public Storage(StaffPlugin pl) {
        plugin = pl;

        CustomFile config = pl.getMainConfig();

        this.host = config.getString("storage.host");
        this.port = config.getInt("storage.port");
        this.database = config.getString("storage.database");
        this.username = config.getString("storage.username");
        this.pass = config.getString("storage.password");

        this.mysql = config.getBoolean("storage.mysql", false);

        loadConnection();
    }


    public static Connection openSQLite(java.nio.file.Path file) {
        DependencyManager.downloadJDBC(plugin);

        try {
            IsolatedClassLoader loader = DependencyManager.getLoader(plugin);
            Class<?> connectionClass = loader.loadClass("org.sqlite.jdbc4.JDBC4Connection");
            Constructor<?> constructor = connectionClass.getConstructor(String.class, String.class, Properties.class);

            return (Connection) constructor.newInstance("jdbc:sqlite:" + file.toString(), file.toString(), new Properties());
        } catch (ReflectiveOperationException | MalformedURLException e) {
            System.err.println("NO SE ENCONTRO EL JDBC");
            e.printStackTrace();
            return null;
        }
    }

    public static Connection openMySQL(String ip, int port, String username, String password, String database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/?user=" + username + "&password=" + password);
            connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS `" + database + "`;");
            connection.createStatement().execute("USE `" + database + "`;");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Connection connection;

    public void loadConnection() {
        if (mysql) {
            plugin.getLogger().info("Loading storage (MySQL)...");

            if (host == null || database == null || username == null || pass == null) {
                plugin.getLogger().severe("The mysql configuration is wrong, check your configuration, disabling plugin...");
                plugin.stop();
                return;
            }

            this.connection = openMySQL(host, port, username, pass, database);
        } else {
            plugin.getLogger().info("Loading storage (SQLite)...");
            this.connection = openSQLite(plugin.getDataFolder().toPath().resolve("data.db"));
        }

        execute("CREATE TABLE IF NOT EXISTS StaffData (uuid VARCHAR(36) NOT NULL, activity BIGINT)");
        plugin.getLogger().info("Storage loaded successfully.");
    }

    public void execute(String command, Object... fields) {
        try {
            PreparedStatement statement = prepareStatement(command, fields);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getStaffActivity(UUID uuid) {
        long result = 0;

        try {
            PreparedStatement statement = prepareStatement("SELECT activity FROM StaffData WHERE uuid = ?;", uuid.toString());
            ResultSet results = statement.executeQuery();

            if(results == null || !results.next()) {
                execute("INSERT INTO StaffData (uuid, activity) VALUES (?, ?)", uuid.toString(), 0);
                return 0;
            } else {
                result = results.getLong(1);
            }

            results.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void saveStaffActivity(UUID uuid, long newAct) {
        execute("UPDATE StaffData SET activity = ? WHERE uuid = ?", newAct, uuid.toString());
    }

    public PreparedStatement prepareStatement(String query, Object... fields) {
        try {
            if(connection == null)
                loadConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            int i = 1;
            for (Object object : fields) {
                statement.setObject(i, object);
                i++;
            }
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized void close() {
        try {
            plugin.getStaffManager().saveAll();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
