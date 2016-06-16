package by.hrychanok.training.shop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PageText extends AbstractModel {

	private String title;
	private String text;
	private Date date;
	
	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Column
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
