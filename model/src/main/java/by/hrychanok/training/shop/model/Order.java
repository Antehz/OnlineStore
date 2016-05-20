package by.hrychanok.training.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order", schema = "public")
public class Order extends AbstractModel  {
	private Customer customer;
	private Date startDate;
	private ShippingMethod shippingMethod;
	private Integer totalPrice;
	private StatusOrder status;
	private List<OrderContent> orderContent;
	private String additionalInfo;

    @Column(name = "additional_info")
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "shipping_method", nullable = false, unique = false)
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	@Column(name = "total_price")
	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", unique = false)
	public StatusOrder getStatus() {
		return status;
	}

	public void setStatus(StatusOrder status) {
		this.status = status;
	}

	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<OrderContent> getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(List<OrderContent> orderContent) {
		this.orderContent = orderContent;
	}

	@Override
	public String toString() {
		return "Order [startDate=" + startDate + ", shippingMethod=" + shippingMethod + ", totalPrice=" + totalPrice
				+ ", status=" + status + ", additionalInfo=" + additionalInfo + "]";
	}

	
	
}
