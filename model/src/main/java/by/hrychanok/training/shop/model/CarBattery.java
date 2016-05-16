package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CarBattery extends Product {

	private BatteryType batteryType;
	private Integer voltage;
	private Integer capacity;
	private Integer cca;
	private Polarity polarity;

	@Column
	public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

	@Column
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	@Column
	public Integer getCca() {
		return cca;
	}

	public void setCca(Integer cca) {
		this.cca = cca;
	}

	@Column
	public Polarity getPolarity() {
		return polarity;
	}

	public void setPolarity(Polarity polarity) {
		this.polarity = polarity;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "battery_type", nullable = false, unique = false)
	public BatteryType getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(BatteryType batteryType) {
		this.batteryType = batteryType;
	}

}
