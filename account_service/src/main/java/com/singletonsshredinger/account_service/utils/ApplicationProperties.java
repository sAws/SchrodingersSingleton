package com.singletonsshredinger.account_service.utils;

import org.jboss.logging.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final Logger LOG = Logger.getLogger(ApplicationProperties.class);
    private static volatile ApplicationProperties instance;
    protected Properties properties;

    private ApplicationProperties(Properties properties) {
        this.properties = properties;
    }

    public static ApplicationProperties getInstance() {
        ApplicationProperties localInstance = instance;
        if (localInstance == null) {
            synchronized (ApplicationProperties.class) {
                localInstance = instance;
                if (localInstance == null) {
                    Properties properties = null;
                    try {
                        properties = readProperties();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    instance = localInstance = new ApplicationProperties(properties);
                }
            }
        }
        return localInstance;
    }

    private static Properties readProperties() throws IOException {
        Properties prop = new Properties();
        String propFileName = "application.properties";

        try (InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream == null) {
                LOG.error("property file was not found");
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            prop.load(inputStream);
            return prop;
        }
    }

    public String getDatabase() {
        return this.properties.getProperty("database");
    }

    public String getCollection() {
        return this.properties.getProperty("collection");
    }
}
