package me.deeent.staffmonitor.commons.utils;

import lombok.experimental.UtilityClass;
import me.deeent.staffmonitor.commons.StaffPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

@UtilityClass
public class DependencyManager {

    private final String FILE_NAME = "sqlite-jdbc-3.36.0.3.jar";

    public void downloadJDBC(StaffPlugin pl) {
        File parent = new File(pl.getDataFolder(), "libs");

        if (!parent.exists()) {
            parent.mkdirs();
        }

        File file = new File(parent, FILE_NAME);

        if(file.exists())
            return;

        String jdbcUrl = "https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar";

        try {
            URL url = new URL(jdbcUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     OutputStream outputStream = Files.newOutputStream(file.toPath())) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    System.out.println("SQLITE successfully hooked: " + file.getAbsolutePath());
                }
            } else {
                System.err.println("Error download SQLITE: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
                pl.stop();
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IsolatedClassLoader getLoader(StaffPlugin pl) throws MalformedURLException {
        File parent = new File(pl.getDataFolder(), "libs");
        return new IsolatedClassLoader(new URL[] { new File(parent, FILE_NAME).toURI().toURL() });
    }

}
