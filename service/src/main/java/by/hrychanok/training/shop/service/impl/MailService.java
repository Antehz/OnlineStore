package by.hrychanok.training.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Order;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailService;
	
	@Autowired
	private SimpleMailMessage simpleMailMessage;
	
	 public void sendMail(String to, String subject, String body) 
	    {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        javaMailService.send(message);
	    }
	 
	    /**
	     * This method will send a pre-configured message for new registred users
	     * */
	    public void sendRegistrationNotificationMail(Customer customer) 
	    {
	        SimpleMailMessage mailMessage=new SimpleMailMessage(simpleMailMessage);
	        String to = customer.getEmail();
	        String text=String.format(" Dear %s %n"
	        		+ " You have been registred on our shop %n"
	        		+ " Your login: %s %n"
	        		+ " Your password: %s %n"
	        		+ " Best regards! %n", customer.getFirstName(), customer.getCustomerCredentials().getLogin(), customer.getCustomerCredentials().getPassword());
	        mailMessage.setTo(to);
	        mailMessage.setText(text);
	        javaMailService.send(mailMessage);
	    }
	    /**
	     * This method will send a pre-configured message for new order
	     * */
	    public void sendOrderConfirmationMail(Order order) 
	    {
	        SimpleMailMessage mailMessage=new SimpleMailMessage(simpleMailMessage);
	        String to = order.getCustomer().getEmail();
	        String text = String.format(" Dear %s %n"
	        		+ "Order id %s was created and waiting confirmation %n"
	        		+ "     -Order status: %s %n "
	        		+ "     -Date of order: %s %n"
	        		+ "     -Total price : %s %n"
	        		+ "     -Shipping method: %s %n", order.getCustomer().getFirstName(),order.getId(), order.getStatus(), order.getStartDate(), order.getTotalPrice(),order.getShippingMethod());
	        mailMessage.setTo(to);
	        mailMessage.setText(text);
	        javaMailService.send(mailMessage);
	    }
}
