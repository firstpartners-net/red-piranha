package net.firstpartners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import net.firstpartners.core.Config;


/**
 * Main class called by Spring on Command line to start Application
 *
 * @author paulf
 * @version $Id: $Id
 */
@SpringBootApplication
public class Application {
	
	@Autowired
	Config myConfig;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * <p>commandLineRunner.</p>
	 *
	 * @param ctx a {@link org.springframework.context.ApplicationContext} object
	 * @return a {@link org.springframework.boot.CommandLineRunner} object
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			
			log.debug("Configuration:\n"+myConfig);

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
