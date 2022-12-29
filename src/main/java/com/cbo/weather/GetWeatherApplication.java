package com.cbo.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GetWeatherApplication {
	
	public static String key ;
	
	public static void main(String[] args) {
		SpringApplication.run(GetWeatherApplication.class, args);
		key=System.getenv("WEB_APPID");//"32a449dec9d3e884e58f126f4e56d82f";//System.getenv("WEB_APPID");        
		System.out.println(key); 
		if (key == null){
			  System.out.print("\nPlease set an environment variable as instructed in the README.md file\n");
			  System.exit(1);
	          }
	}
	

}
