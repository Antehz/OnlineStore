package by.hrychanok.training.shop.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class CartContent extends AbstractModel {

	private Customer customer;
	private Product product;
	private Integer amount;
	private Date dateAdd;
	private Integer price;

	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade =CascadeType.REFRESH)
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	@Column
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CartContent [customer=" + customer + ", product=" + product + ", amount=" + amount + ", dateAdd="
				+ dateAdd + ", price=" + price + "]";
	}

}