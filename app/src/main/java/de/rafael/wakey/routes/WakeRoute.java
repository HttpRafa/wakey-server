package de.rafael.wakey.routes;

import de.rafael.wakey.WakeyApplication;
import de.rafael.wakey.classes.AppRoute;
import de.rafael.wakey.utils.PacketUtils;
import org.jetbrains.annotations.NotNull;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Arrays;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

public class WakeRoute implements AppRoute {

    @Override
    public void init() {
        Spark.post("/v1/wake/:token", this);
    }

    @Override
    public Object handle(@NotNull Request request, Response response) {
        String token = request.params(":token");
        var device = Arrays.stream(WakeyApplication.instance().deviceConfig().devices()).filter(item -> item.token().equals(token)).findFirst();
        if(device.isPresent()) {
            PacketUtils.sendWakePacket(device.get().network());
            return "OK";
        } else {
            response.status(404);
            return "Token does not exist";
        }
    }

}
