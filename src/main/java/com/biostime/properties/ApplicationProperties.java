package com.biostime.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
	
	public static String profile;
	
	public static String O2O_BASE_URL;

	@Value("${spring.profiles.active}")
	public void setProfile(String profile) {
		ApplicationProperties.profile = profile;
	}

	@Value("${o2o.base.url}")
	public void setO2O_BASE_URL(String c) {
		ApplicationProperties.O2O_BASE_URL = c;
	}

}
