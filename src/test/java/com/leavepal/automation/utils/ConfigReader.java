package com.leavepal.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final String CONFIG_FILE = "config/testdata.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Config file not found on classpath: " + CONFIG_FILE);
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read config file: " + CONFIG_FILE, exception);
        }
    }

    public static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config key: " + key + " in " + CONFIG_FILE);
        }
        return value;
    }

    public static long getLongProperty(String key, long defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Invalid long value for key: " + key + " in " + CONFIG_FILE, exception);
        }
    }
}
