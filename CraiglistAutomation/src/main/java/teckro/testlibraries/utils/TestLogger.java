package teckro.testlibraries.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {
    private static final Logger LOGGER = Logger.getLogger(TestLogger.class.getName());
    
    static {
        // Disable console output during tests to prevent xunit-viewer parsing issues with Surefire XML
        if (Boolean.parseBoolean(System.getenv().getOrDefault("CI", "true"))
                || Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            LOGGER.setLevel(Level.OFF);
        }
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void error(String message, Throwable t) {
        LOGGER.log(Level.SEVERE, message, t);
    }

    public static void warn(String message) {
        LOGGER.warning(message);
    }
}
