package com.cbo.weather.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table( name = "weather_history")
public class Weather {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "main")
	private String main;
	@Column(name = "description")
	private String description;
	@Column(name = "temp")
	private float temp;
	@Column(name = "country")
	private String country;
	@Column(name = "city")
	private String city;
	@Column(name = "lon")
	private int lon;
	@Column(name = "lat")
	private int lat;
	@Column(name = "humidity")
	private int humidity;
	@Column(name = "dateAndTime")
	private String dateAndTime;
	
	public Weather() {
		
	}

	public Weather(String main, String description, float temp, String country, String city, int lon, int lat,
			int humidity, String dateAndTime) {
		this.main = main;
		this.description = description;
		this.temp = temp;
		this.country = country;
		this.city = city;
		this.lon = lon;
		this.lat = lat;
		this.humidity = humidity;
		this.dateAndTime = dateAndTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
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

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	
}
