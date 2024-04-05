package com.itp.climate;

import com.itp.climate.data.Collector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClimateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClimateApplication.class, args);
		Collector co = new Collector();
		co.getForecast()
				.subscribe(response -> {
					System.out.println("Response: " + response);
					// Optionally parse the JSON using parseForecastData
				}, error -> {
					System.err.println("Error: " + error.getMessage());
				});
	//	System.out.println(co.getForecast().subscribe());
	}



}
