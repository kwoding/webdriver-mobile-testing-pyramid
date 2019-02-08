package it.ding.webdriver.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

/**
 * This is the single thread-safe source of retrieving properties.
 *
 * Usage example:
 * <pre>
 * GlobalProperties globalProperties = GlobalProperties.getInstance();
 * String aUrl = globalProperties.getString("url");
 * </pre>
 *
 * note: GlobalProperties is a thread-safe singleton - it maintains a single instance within execution
 *
 * You can also use it as a property of a class;
 * <pre>
 *  private GlobalProperties globalProperties = GlobalProperties.getInstance();
 * </pre>
 */
public class GlobalProperties {

    private static GlobalProperties instance;
    private CompositeConfiguration configuration;
    private static final String TEST_PROPERTIES = "test.properties";

    private GlobalProperties() {
        configuration = new CompositeConfiguration();
        configuration.addConfiguration(new SystemConfiguration());
        configuration.addConfiguration(new EnvironmentConfiguration());
        try {
            configuration.addConfiguration(new PropertiesConfiguration(TEST_PROPERTIES));
        } catch (ConfigurationException ignored) {
        }
    }

    public synchronized static GlobalProperties getInstance() {
        if (instance == null) {
            instance = new GlobalProperties();
        }
        return instance;
    }

    public String getString(String key) {
        return configuration.getString(key);
    }

    public int getInt(String key) {
        return configuration.getInt(key);
    }

    public long getLong(String key) {
        return configuration.getLong(key);
    }

    public boolean getBoolean(String key) {
        return configuration.getBoolean(key);
    }

    public synchronized String syncGet(String key) {
        return configuration.getString(key);
    }

    public <T> void setProperty(String key, T value) {
        configuration.setProperty(key, value);
    }

}
