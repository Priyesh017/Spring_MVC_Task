package in.guvi.task.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main entry point for the Employee Vault Spring Boot application.
 *
 * This class contains the main method which bootstraps the application,
 * starts the embedded web server (Tomcat), and initializes the Spring application context.
 */
@SpringBootApplication
// @SpringBootApplication is a convenience annotation that combines three powerful features:
// 1. @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
// 2. @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'in.guvi.task.mvc' package and its sub-packages.
// 3. @Configuration: Tags the class as a source of bean definitions for the application context.

@EnableMongoRepositories("in.guvi.task.mvc.repository")
// Explicitly tells Spring Data MongoDB exactly which package to scan for Repository interfaces.
// While Spring Boot often auto-detects this, explicitly declaring it is a best practice
// for larger projects to prevent startup issues and improve scan times.
public class MvcApplication {

	/**
	 * The standard Java main method.
	 * This is the very first piece of code executed when the application starts.
	 *
	 * @param args Command-line arguments passed to the application during startup.
	 */
	public static void main(String[] args) {
		// SpringApplication.run() sets up the default configuration, starts the Spring application context,
		// performs the classpath scan, and starts the embedded web server.
		SpringApplication.run(MvcApplication.class, args);
	}

}