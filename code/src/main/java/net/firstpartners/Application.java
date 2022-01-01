package net.firstpartners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import net.firstpartners.data.Config;


/**
 * Main class called by Spring on Command line to start Application
 * 
 * @author paulf
 *
 */
@SpringBootApplication
public class Application {
	
	@Autowired
	Config myConfig;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			
			log.info("Configuration:"+myConfig);

			/**
			log.debug("Inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.debug("    "+beanName);
			}**/

		};
	}



}
