
package by.hrychanok.training.shop.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Category parent;
	private List<Category> children = new ArrayList<Category>();
	private String name;
	private String description;

	public Category(Category parent, String name, String description) {
		this.description = description;
		this.parent = parent;
		this.name = name;
	}

	public Category() {
	}

	public Category addCategory(String name, String description) {
		Category child = new Category(this, name, description);
		children.add(child);
		return child;
	}

	public static Category createCategories() {
		return new Category(null, "root", "root");
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return name;
	}

}