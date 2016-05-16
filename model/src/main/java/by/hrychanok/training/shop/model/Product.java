package by.hrychanok.training.shop.model;

import java.net.URL;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
