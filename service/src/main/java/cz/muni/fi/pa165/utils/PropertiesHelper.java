package cz.muni.fi.pa165.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Petr Valenta
 */
public class PropertiesHelper {
    InputStream inputStream;

    public Properties getProperties() throws IOException {
        Properties prop = new Properties();
        try {
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (IOException e) {
            throw e; //TODO dirty
        } finally {
            inputStream.close();
        }
        return prop;
    }
}
