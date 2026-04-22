package teckro.testlibraries.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {
    private static final Logger LOGGER = Logger.getLogger(TestLogger.class.getName());

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
