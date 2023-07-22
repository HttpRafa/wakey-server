package de.rafael.wakey.configuration;

import de.rafael.wakey.WakeyApplication;
import de.rafael.wakey.classes.Device;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.UUID;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

@Getter
@Setter
public class WakeyConfig {

    private static final File CONFIG_FILE = new File("wakey.json");

    private String dataPath = ".";

    private static void writeToFile(WakeyConfig deviceConfig) throws IOException {
        try(FileWriter fileWriter = new FileWriter(CONFIG_FILE)) {
            fileWriter.write(WakeyApplication.GSON.toJson(deviceConfig));
        }
    }

    public static WakeyConfig load() throws IOException {
        if(!CONFIG_FILE.exists()) {
            writeToFile(new WakeyConfig());
        }
        try(FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE)) {
            return WakeyApplication.GSON.fromJson(new InputStreamReader(fileInputStream), WakeyConfig.class);
        }
    }

}