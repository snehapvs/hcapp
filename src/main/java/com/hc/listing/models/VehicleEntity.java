package com.hc.listing.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "vehicle_listing", schema = "heycar_demo")
public class VehicleEntity {

	
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getKw() {
		return kw;
	}

	public void setKw(Integer kw) {
		this.kw = kw;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public VehicleEntityKey getKey() {
		return key;
	}

	public void setKey(VehicleEntityKey key) {
		this.key = key;
	}

	@Column(name="make")
	private String make;
	@Column(name="model")
	private String model;
	@Column(name="kw")
	private Integer kw ;
	@Column(name="year")
	private Integer year;
	@Column(name="color")
	private String color;
	@Column(name="price")
	private Double price;
	@EmbeddedId
    private VehicleEntityKey key;

	
	public VehicleEntity(VehicleEntry entry,String dealerId) {
		VehicleEntityKey key=new VehicleEntityKey();
		key.setCode(entry.getCode());
		key.setDealerId(dealerId);
		this.key = key ;
		this.make = entry.getMake();
		this.model = entry.getModel();
		this.kw = entry.getKw();
		this.year = entry.getYear();
		this.color = entry.getColor();
		this.price = entry.getPrice();
	}
	
	public VehicleEntity() {
		
	}
}
