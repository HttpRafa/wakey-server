package de.rafael.wakey.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

@Getter
@Setter
@AllArgsConstructor
public class Device {

    private String name;
    private String token;

    private NetworkDevice network;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class NetworkDevice {

        private String address;
        private String macAddress;

        public byte[] macAddressAsBytes() {
            byte[] bytes = new byte[6];
            String[] hex = macAddress.split("[:-]");
            if(hex.length != 6) {
                throw new IllegalArgumentException("Invalid mac address");
            }
            try {
                for (int i = 0; i < 6; i++) {
                    bytes[i] = (byte)Integer.parseInt(hex[i], 16);
                }
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Invalid hex digit in MAC address.");
            }
            return bytes;
        }

        @Override
        public String toString() {
            return address;
        }

    }

}
