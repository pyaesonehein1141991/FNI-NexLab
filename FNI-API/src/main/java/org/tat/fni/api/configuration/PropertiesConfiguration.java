package org.tat.fni.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesConfiguration {
	@Autowired
	private Environment env;
	
	public String getProperty(String key) {
		return env.getProperty(key);
	}

}
