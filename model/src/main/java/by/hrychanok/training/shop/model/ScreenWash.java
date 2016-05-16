package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "screen_wash", schema = "public")
@PrimaryKeyJoinColumn(name = "id")
public class ScreenWash extends Product {

	private FluidState fluidState;
	private Season washSeason;
	private Integer temperature;
	private String countryManufacturer;
	private Integer volume;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "wash_season", nullable = false, unique = false)
	public Season getWashSeason() {
		return washSeason;
	}

	public void setWashSeason(Season washSeason) {
		this.washSeason = washSeason;
	}

	@Column
	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	@Column
	public String getCountryManufacturer() {
		return countryManufacturer;
	}

	public void setCountryManufacturer(String countryManufacturer) {
		this.countryManufacturer = countryManufacturer;
	}

	@Column
	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "fluid_state", nullable = false, unique = false)
	public FluidState getFluidState() {
		return fluidState;
	}

	public void setFluidState(FluidState fluidState) {
		this.fluidState = fluidState;
	}

}
