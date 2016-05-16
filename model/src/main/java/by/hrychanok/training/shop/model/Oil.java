package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "oil", schema = "public")
@PrimaryKeyJoinColumn(name = "id")
public class Oil extends Product {

	private OilType oilType;
	private OilComposition oilComposition;
	private String viscosity;
	private OilDestination oilDestination;
	private Integer volume;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "oil_type", nullable = false, unique = false)
	public OilType getOilType() {
		return oilType;
	}

	public void setOilType(OilType oilType) {
		this.oilType = oilType;
	}

	@Column
	public String getViscosity() {
		return viscosity;
	}

	public void setViscosity(String viscosity) {
		this.viscosity = viscosity;
	}

	@Column
	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "oil_composition", nullable = false, unique = false)
	public OilComposition getOilComposition() {
		return oilComposition;
	}

	public void setOilComposition(OilComposition oilComposition) {
		this.oilComposition = oilComposition;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "oil_destination", nullable = false, unique = false)
	public OilDestination getOilDestination() {
		return oilDestination;
	}

	public void setOilDestination(OilDestination oilDestination) {
		this.oilDestination = oilDestination;
	}

}
