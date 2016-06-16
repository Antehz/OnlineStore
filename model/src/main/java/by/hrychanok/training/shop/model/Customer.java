package by.hrychanok.training.shop.model;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import by.hrychanok.training.shop.model.Order;

@Entity
public class Customer extends AbstractModel{

	private String firstName;
	private String lastName;
	private String email;
	private Date created;
	private Date dateBirth;
	private Gender gender;
	private String country;
	private String city;
	private String address;
	private String zipCode;
	private List<CartContent> cartContent;
	private List<Order> order;
	private List<ProductComment> productComment;
	private CustomerCredentials customerCredentials;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, updatable = false, name = "id")
	public CustomerCredentials getCustomerCredentials() {
		return customerCredentials;
	}

	public void setCustomerCredentials(CustomerCredentials customerCredentials) {
		this.customerCredentials = customerCredentials;
	}

	@Column
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(updatable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	@Column
	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender", nullable = false, unique = false)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Column
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<ProductComment> getProductComment() {
		return productComment;
	}

	public void setProduct(List<ProductComment> productComment) {
		this.productComment = productComment;
	}

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<CartContent> getCartContent() {
		return cartContent;
	}

	public void setCartContent(List<CartContent> cartContent) {
		this.cartContent = cartContent;
	}

	public void setProductComment(List<ProductComment> productComment) {
		this.productComment = productComment;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", created="
				+ created + ", dateBirth=" + dateBirth + ", gender=" + gender + ", country=" + country + ", city="
				+ city + ", address=" + address + ", zipCode=" + zipCode + ", customerCredentials="
				+ customerCredentials + "]";
	}

}
