package com.hc.listing.models;

import javax.validation.constraints.NotNull;

public class VehicleEntry {	
	
	@NotNull
	private String code;
	private String make;
	private String model;
	private Integer kw ;
	private Integer year;
	private String color;
	private Double price;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
}
