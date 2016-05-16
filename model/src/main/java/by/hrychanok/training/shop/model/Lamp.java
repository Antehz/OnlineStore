package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "lamp", schema = "public")
@PrimaryKeyJoinColumn(name = "id")
public class Lamp extends Product {

	private LampType lampType;
	private String countryManufacturer;
	private String cap;
	private Integer power;
	private Integer temperature;
	private Integer luminousFlux;
	private Integer packageContents;

	@Column
	public String getCountryManufacturer() {
		return countryManufacturer;
	}

	public void setCountryManufacturer(String countryManufacturer) {
		this.countryManufacturer = countryManufacturer;
	}

	@Column
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	@Column
	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	@Column
	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	@Column
	public Integer getLuminousFlux() {
		return luminousFlux;
	}

	public void setLuminousFlux(Integer luminousFlux) {
		this.luminousFlux = luminousFlux;
	}

	@Column
	public Integer getPackageContents() {
		return packageContents;
	}

	public void setPackageContents(Integer packageContents) {
		this.packageContents = packageContents;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "lamp_type", nullable = false, unique = false)
	public LampType getLampType() {
		return lampType;
	}

	public void setLampType(LampType lampType) {
		this.lampType = lampType;
	}

}
