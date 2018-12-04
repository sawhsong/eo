package zebra.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropUtil {
	private static Logger logger = LogManager.getLogger(PropUtil.class);
	private static PropertiesConfiguration config;

	public static void load(String fileName) {
		config = new PropertiesConfiguration();
		try {
			config.load(fileName);
		} catch (ConfigurationException ex) {
			logger.error(ex);
		}
	}

	public static String getProperty(String fileName, String key) {
		load(fileName);
		return (config.containsKey(key)) ? config.getString(key) : "";
	}

	public static String getProperty(String fileName, String key, String defaultValue) {
		load(fileName);
		return (config.containsKey(key)) ? config.getString(key) : defaultValue;
	}
}