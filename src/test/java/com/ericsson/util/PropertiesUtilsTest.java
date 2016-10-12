package com.ericsson.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import junit.framework.Assert;

public class PropertiesUtilsTest {

	static final String CONF_FILE = "conf/cmpp.conf";
	
	@Test
	public void testGetPropertiesString() {
		try {
			Properties props = PropertiesUtils.getProperties(CONF_FILE);
			String devs = props.getProperty("devs");
			System.out.println( devs );
			Assert.assertEquals("eth0, eth1", devs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
