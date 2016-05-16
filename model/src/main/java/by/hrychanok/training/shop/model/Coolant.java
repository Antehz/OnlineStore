package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Coolant extends Product {

	private FluidState fluidState;
	private CoolantColor color;
	private Integer volume;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "fluid_state", nullable = false, unique = false)
	public FluidState getFluidState() {
		return fluidState;
	}

	public void setFluidState(FluidState fluidState) {
		this.fluidState = fluidState;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "color", nullable = false, unique = false)
	public CoolantColor getColor() {
		return color;
	}

	public void setColor(CoolantColor color) {
		this.color = color;
	}

	@Column
	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

}
