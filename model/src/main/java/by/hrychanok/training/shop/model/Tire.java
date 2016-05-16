package by.hrychanok.training.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "tire", schema = "public")
@PrimaryKeyJoinColumn(name = "id")
public class Tire extends Product {

	private TireDestination tireDestination;
	private Season tireSeason;
	private Integer profileWidth;
	private Integer profileHeight;
	private Integer rimSize;
	private Boolean spikes;

	@Column
	public Integer getProfileWidth() {
		return profileWidth;
	}

	public void setProfileWidth(Integer profileWidth) {
		this.profileWidth = profileWidth;
	}

	@Column
	public Integer getProfileHeight() {
		return profileHeight;
	}

	public void setProfileHeight(Integer profileHeight) {
		this.profileHeight = profileHeight;
	}

	@Column
	public Integer getRimSize() {
		return rimSize;
	}

	public void setRimSize(Integer rimSize) {
		this.rimSize = rimSize;
	}

	@Column
	public Boolean getSpikes() {
		return spikes;
	}

	public void setSpikes(Boolean spikes) {
		this.spikes = spikes;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tire_destination", nullable = false, unique = false)
	public TireDestination getTireDestination() {
		return tireDestination;
	}

	public void setTireDestination(TireDestination tireDestination) {
		this.tireDestination = tireDestination;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tire_season", nullable = false, unique = false)
	public Season getTireSeason() {
		return tireSeason;
	}

	public void setTireSeason(Season tireSeason) {
		this.tireSeason = tireSeason;
	}

	@Override
	public String toString() {
		return "Tire [getName()=" + getName() + ", getManufacturer()=" + getManufacturer() + ", getModel()="
				+ getModel() + ", getPrice()=" + getPrice() + ", getDescription()=" + getDescription()
				+ ", getImageURL()=" + getImageURL() + ", getCountOrder()=" + getCountOrder()
				+ ", getCountRecommended()=" + getCountRecommended() + ", getAvailable()=" + getAvailable()
				+ ", getId()=" + getId() + ", tireDestination=" + tireDestination + ", tireSeason=" + tireSeason
				+ ", profileWidth=" + profileWidth + ", profileHeight=" + profileHeight + ", rimSize=" + rimSize
				+ ", spikes=" + spikes + "]";
	}

}
