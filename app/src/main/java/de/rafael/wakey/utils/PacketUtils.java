package de.rafael.wakey.utils;

import de.rafael.wakey.WakeyApplication;
import de.rafael.wakey.classes.Device;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

public class PacketUtils {

    public static void sendWakePacket(Device.@NotNull NetworkDevice networkDevice) {
        try {
            byte[] macAddress = networkDevice.macAddressAsBytes();
            byte[] bytes = new byte[6 + 16 * macAddress.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macAddress.length) {
                System.arraycopy(macAddress, 0, bytes, i, macAddress.length);
            }

            InetAddress address = InetAddress.getByName(networkDevice.address());
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.send(packet);
            }
            WakeyApplication.LOGGER.info("Wake-over-LAN packet send to " + networkDevice);
        } catch (Throwable throwable) {
            WakeyApplication.LOGGER.error("Failed to send Wake-over-LAN packet to " + networkDevice, throwable);
        }
    }

}
