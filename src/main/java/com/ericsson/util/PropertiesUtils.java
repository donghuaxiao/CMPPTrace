package com.ericsson.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtils {

	public static URL  getResource(String resource) {
		if (resource == null) {
			return null;
		}
		 
		return PropertiesUtils.class.getClassLoader().getResource(resource);
	}
	
	public static String getProperties( String resource, String key) throws IOException {
		
		
		Properties props = PropertiesUtils.getProperties(resource);
		
		if (props == null) {
			return null;
		}
		return props.getProperty(key);
		
	}
	
	public static String getProperties(String resource, String key, String defaultValue) throws IOException {
		String value = PropertiesUtils.getProperties(resource, key);
		return value == null ? defaultValue: value;
	}
	
	public static Properties getProperties( String resource) throws IOException {
        
        String workdir = System.getProperty("user.dir");
		return PropertiesUtils.loadProperties(workdir + File.separator + resource);
	}
	
	public static Properties loadProperties( String filename) throws IOException {
		File file= new File(filename);
		InputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch( FileNotFoundException ex ) {
			throw new IOException(ex.getMessage());
		}
		Properties prop = new Properties();
		prop.load(input);
		return prop;
	}
}
