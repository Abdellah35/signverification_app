package com.cbo.weather.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbo.weather.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
