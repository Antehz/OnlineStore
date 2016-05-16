package by.hrychanok.training.shop.repository.filter;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;

public class CustomerFilter {
	public String userName;
	public String login;
	public String email;
	public Date dateBirth;
	private Date created;
	public String country;
	public String city;
	public String address;
	private SingularAttribute sortProperty;
	private boolean sortOrder;
	private Integer offset;
	private Integer limit;

	private boolean isFetchCredentials;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public SingularAttribute getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(SingularAttribute sortProperty) {
		this.sortProperty = sortProperty;
	}

	public boolean isSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(boolean sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isFetchCredentials() {
		return isFetchCredentials;
	}

	public void setFetchCredentials(boolean isFetchCredentials) {
		this.isFetchCredentials = isFetchCredentials;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	

}
