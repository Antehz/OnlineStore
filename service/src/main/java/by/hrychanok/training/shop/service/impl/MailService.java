package by.hrychanok.training.shop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Order;

@Service
public class MailService {
	private static Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private JavaMailSender javaMailService;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		javaMailService.send(message);
		LOGGER.info(String.format("Message has been sent to %s", to));
	}

	/**
	 * This method will send a pre-configured message for new registred users
	 */
	public void sendRegistrationNotificationMail(Customer customer) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String to = customer.getEmail();
		String text = String.format(
				" Dear %s %n" + " You have been registred on our shop %n" + " Your login: %s %n"
						+ " Your password: %s %n" + " Best regards! %n",
				customer.getFirstName(), customer.getCustomerCredentials().getLogin(),
				customer.getCustomerCredentials().getPassword());
		mailMessage.setTo(to);
		mailMessage.setSubject("Succesfull Registration");
		mailMessage.setText(text);
		javaMailService.send(mailMessage);
		LOGGER.info(String.format("Registration notification has been sent to %s", to));
	}

	/**
	 * This method will send a pre-configured message for new order
	 */
	public void sendOrderConfirmationMail(Order order) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String to = order.getCustomer().getEmail();
		String text = String.format(
				" Dear %s %n" + "Order id %s was created and waiting confirmation %n" + "     -Order status: %s %n "
						+ "     -Date of order: %s %n" + "     -Total price : %s %n" + "     -Shipping method: %s %n",
				order.getCustomer().getFirstName(), order.getId(), order.getStatus(), order.getStartDate(),
				order.getTotalPrice(), order.getShippingMethod());
		mailMessage.setTo(to);
		mailMessage.setSubject("Order confirmation");
		mailMessage.setText(text);
		javaMailService.send(mailMessage);
		LOGGER.info(String.format("Order confirmation has been sent to %s", to));
	}
}
