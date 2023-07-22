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
public class DeviceConfig {

    private Device[] devices;

    public DeviceConfig() {
        this.devices = new Device[0];
    }

    private static void writeToFile(DeviceConfig deviceConfig) throws IOException {
        try(FileWriter fileWriter = new FileWriter(new File(WakeyApplication.instance().wakeyConfig().dataPath(), "devices.json"))) {
            fileWriter.write(WakeyApplication.GSON.toJson(deviceConfig));
        }
    }

    public static DeviceConfig load() throws IOException {
        File file = new File(WakeyApplication.instance().wakeyConfig().dataPath(), "devices.json");
        if(!file.exists()) {
            writeToFile(new DeviceConfig());
        }
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            var configuration = WakeyApplication.GSON.fromJson(new InputStreamReader(fileInputStream), DeviceConfig.class);
            int generated = 0;
            for (Device device : configuration.devices()) {
                if(device.token().equalsIgnoreCase("~")) {
                    device.token(UUID.randomUUID().toString());
                    generated++;
                }
            }
            if(generated > 0) {
                writeToFile(configuration);
            }
            return configuration;
        }
    }

}
