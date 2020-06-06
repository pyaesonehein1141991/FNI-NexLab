package org.tat.fni.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tat.fni.api.common.SecurityUser;
import org.tat.fni.api.common.emumdata.Role;

@SpringBootApplication
public class FniApiApplication implements CommandLineRunner {
	// @Autowired
	// private UserService userService;

	// private static final Logger LOGGER =
	// LogManager.getLogger(GginlApiApplication.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(FniApiApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FniApiApplication.class);
		Properties properties = new Properties();
		properties.setProperty("spring.main.banner-mode", "log");
		properties.setProperty("logging.file", "D:/APILOG.log");
		properties.setProperty("logging.level.com.microsoft.sqlserver.jdbc", "info");
		properties.setProperty("logging.level.com.microsoft.sqlserver.jdbc.internals", "debug");
		properties.setProperty("logging.level.root", "WARN");
		properties.setProperty("logging.level.org.tat.fni.api", "warn");
		properties.setProperty("logging.level.org.springframework.web", "debug");
		properties.setProperty("logging.pattern.console", "");
		application.setDefaultProperties(properties);
		application.run(args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		SecurityUser admin = new SecurityUser();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@email.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

		// userService.signup(admin);

		SecurityUser client = new SecurityUser();
		client.setUsername("client");
		client.setPassword("client");
		client.setEmail("client@email.com");
		client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

		// userService.signup(client);
	}
}
