package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "wheel", schema = "public")
@PrimaryKeyJoinColumn(name = "id")
public class Wheel extends Product {

	private WheelType wheelType;
	private Integer rimSize;
	private Integer rimWidth;
	private Integer numberHole;
	private Integer pcd;
	private Integer dia;
	private Integer et;

	@Column
	public Integer getRimSize() {
		return rimSize;
	}

	public void setRimSize(Integer rimSize) {
		this.rimSize = rimSize;
	}

	@Column
	public Integer getRimWidth() {
		return rimWidth;
	}

	public void setRimWidth(Integer rimWidth) {
		this.rimWidth = rimWidth;
	}

	@Column
	public Integer getNumberHole() {
		return numberHole;
	}

	public void setNumberHole(Integer numberHole) {
		this.numberHole = numberHole;
	}

	@Column
	public Integer getPcd() {
		return pcd;
	}

	public void setPcd(Integer pcd) {
		this.pcd = pcd;
	}

	@Column
	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	@Column
	public Integer getEt() {
		return et;
	}

	public void setEt(Integer et) {
		this.et = et;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "wheel_type", nullable = false, unique = false)
	public WheelType getWheelType() {
		return wheelType;
	}

	public void setWheelType(WheelType wheelType) {
		this.wheelType = wheelType;
	}

}
