package de.rafael.wakey.routes;

import de.rafael.wakey.WakeyApplication;
import de.rafael.wakey.classes.AppRoute;
import org.jetbrains.annotations.NotNull;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Arrays;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

public class NameRoute implements AppRoute {

    @Override
    public void init() {
        Spark.get("/v1/name/:token", this);
    }

    @Override
    public Object handle(@NotNull Request request, @NotNull Response response) {
        String token = request.params(":token");
        var device = Arrays.stream(WakeyApplication.instance().deviceConfig().devices()).filter(item -> item.token().equals(token)).findFirst();
        if(device.isPresent()) {
            return device.get().name();
        } else {
            response.status(404);
            return "Token does not exist";
        }
    }

}
