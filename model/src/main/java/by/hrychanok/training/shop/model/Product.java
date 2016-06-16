package by.hrychanok.training.shop.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends AbstractModel {

	private String name;
	private String manufacturer;
	private String model;
	private Integer price;
	private String description;
	private String imageURL;
	private Integer countOrder;
	private Integer countRecommended;
	private Category category;
	private Integer available;
	private List<OrderContent> orderContent;
	private List<ProductComment> productComment;
	
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<ProductComment> getProductComment() {
		return productComment;
	}

	public void setProductComment(List<ProductComment> productComment) {
		this.productComment = productComment;
	}

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<OrderContent> getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(List<OrderContent> orderContent) {
		this.orderContent = orderContent;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "image")
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Column(name = "count_order")
	public Integer getCountOrder() {
		return countOrder;
	}

	public void setCountOrder(Integer countOrder) {
		this.countOrder = countOrder;
	}

	@Column(name = "count_recommended")
	public Integer getCountRecommended() {
		return countRecommended;
	}

	public void setCountRecommended(Integer countRecommended) {
		this.countRecommended = countRecommended;
	}

	@Column
	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", manufacturer=" + manufacturer + ", model=" + model + ", price=" + price
				+ ", description=" + description + ", imageURL=" + imageURL + ", countOrder=" + countOrder
				+ ", countRecommended=" + countRecommended + ", available=" + available + "]";
	}

}
