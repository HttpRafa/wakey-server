package de.rafael.wakey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.rafael.wakey.classes.AppRoute;
import de.rafael.wakey.configuration.DeviceConfig;
import de.rafael.wakey.configuration.WakeyConfig;
import de.rafael.wakey.utils.ReflectionUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

public class WakeyApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(WakeyApplication.class);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    private static final String[] ROUTES_PACKAGES = {
            "de.rafael.wakey.routes"
    };

    @Getter
    private static WakeyApplication instance;

    public static void main(String[] args) {
        instance = new WakeyApplication();
    }

    @Getter
    private final List<AppRoute> routes = new ArrayList<>();

    @Getter
    private WakeyConfig wakeyConfig;
    @Getter
    private DeviceConfig deviceConfig;

    public WakeyApplication() {
        instance = this;
        LOGGER.info("Initializing application...");
        LOGGER.info("Loading configuration...");
        try {
            this.wakeyConfig = WakeyConfig.load();
        } catch (Throwable throwable) {
            LOGGER.error("Failed to load configuration", throwable);
            System.exit(1);
            return;
        }
        LOGGER.info("Loading devices...");
        try {
            this.deviceConfig = DeviceConfig.load();
        } catch (Throwable throwable) {
            LOGGER.error("Failed to load device configuration", throwable);
            System.exit(1);
            return;
        }
        LOGGER.info("Starting spark...");
        Spark.init();
        loadRoutes();
        LOGGER.info("Wakey is ready");
    }

    @SneakyThrows
    private void loadRoutes() {
        for (Class<?> aClass : ReflectionUtils.classesFromPackages(this.getClass().getClassLoader(), ROUTES_PACKAGES)) {
            if(AppRoute.class.isAssignableFrom(aClass)) {
                AppRoute appRoute = (AppRoute) aClass.getDeclaredConstructor().newInstance();
                appRoute.init();
                this.routes.add(appRoute);
                LOGGER.info("Initialized route " + aClass.getSimpleName());
            }
        }
    }

}
