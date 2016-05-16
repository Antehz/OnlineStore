package by.hrychanok.training.shop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_credentials", schema = "public")
public class CustomerCredentials extends AbstractModel {

	private Customer customer;
	private String login;
	private String password;
	private UserRole role;

	@OneToOne(mappedBy = "customerCredentials")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role", nullable = false, unique = false)
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "CustomerCredentials [login=" + login + ", password=" + password + ", role=" + role + "]";
	}
	

}