package by.hrychanok.training.shop.service.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mail sender configuration for sending emails. Relies on the JavaMail API and
 * Spring's JavaMail support. Used by the Spring Integration "Signup Pipeline"
 * and the "Invite" module.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("by.hrychanok.training.shop")
@PropertySource("classpath:mail.properties")
public class MailConfig {

	@Autowired
	private Environment env;

	/**
	 * The Java Mail sender. It's not generally expected for mail sending to
	 * work in embedded mode. Since this mail sender is always invoked
	 * asynchronously, this won't cause problems for the developer.
	 */

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost(env.getProperty("mail.smtp.host"));
		mailSender.setPort(env.getProperty("mail.smtp.port", Integer.class, 465));
		mailSender.setUsername(env.getProperty("login"));
		mailSender.setPassword(env.getProperty("password"));
		Properties properties = new Properties();
		properties.put("mail.smtp.socketFactory.port", env.getProperty("mail.smtp.socketFactory.port"));
		properties.put("mail.smtp.socketFactory.class", env.getProperty("mail.smtp.socketFactory.class"));
		properties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}

	@Bean
	public SimpleMailMessage registrationNotify() {
		String from = env.getProperty("login");
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		return simpleMailMessage;
	}

}