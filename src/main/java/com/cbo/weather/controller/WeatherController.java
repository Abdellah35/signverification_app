package com.cbo.weather.controller;

import io.github.pixee.security.BoundedLineReader;
import io.github.pixee.security.HostValidator;
import io.github.pixee.security.Urls;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.net.http.HttpResponse;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cbo.weather.model.Weather;
import com.cbo.weather.repo.WeatherRepository;
@RestController
public class WeatherController {
	

	@Autowired
	WeatherRepository weatherRepository;
	public String content;
    public String inline;
    public static String key ;
    public static int responseCode;
    
 
    
    @GetMapping("weather/history")
	public ModelAndView getWeatherHistory(@RequestParam(required = false) String location) {
		try {
			List<Weather> weatherhis = new ArrayList<Weather>();
			
			if (location == null)
				weatherRepository.findAll().forEach(weatherhis::add);
			else
				weatherRepository.findAll().forEach(weatherhis::add);

			if (weatherhis.isEmpty()) {
				ModelAndView mv = new ModelAndView("weatherhistory");
				 mv.addObject("weathers",weatherhis);
				return mv;
				
			}
			 ModelAndView mv = new ModelAndView("weatherhistory");
			 mv.addObject("weathers",weatherhis);
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("weatherhistory");
			 mv.addObject("weathers",null);
			return mv;
			
		}
	}
    
	@GetMapping("/weather/current")
    @CrossOrigin(origins="http://localhost:8080")
    public ModelAndView current(@RequestParam(value="city" )String location) {

	
            try {
            	key=System.getenv("WEB_APPID");//"32a449dec9d3e884e58f126f4e56d82f";//        
      		  if (key == null){
      			  System.out.print("\nPlease set an environment variable as instructed in the README.md file\n");
      			  System.exit(1);
      	          }
		
		String inputLine;	
            	String url = "http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid="+key; 
            	URL urlobj = Urls.create(url, Urls.HTTP_PROTOCOLS, HostValidator.DENY_COMMON_INFRASTRUCTURE_TARGETS);
		HttpURLConnection connection = (HttpURLConnection) urlobj.openConnection();
		responseCode = connection.getResponseCode();
		BufferedReader inputs = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuffer response = new StringBuffer();
		while ((inputLine = BoundedLineReader.readLine(inputs, 5_000_000)) != null) {
			response.append(inputLine);
				}

		inputs.close();
		content = response.toString();
		
		System.out.println(content);
         
	}
	
	catch (Exception e) {
		System.out.print("ERROR : "+e);
		 ModelAndView mv = new ModelAndView("weatherResult");
		Weather weather = new Weather("Null","Null",0,"Null","City Not Found",0,0,0,"Null");
		mv.addObject("weather",weather);
		return mv;
	}
                          		
       
    //////
	JSONObject root = new JSONObject(content);
	JSONObject main = root.getJSONObject("main");
	//system
    JSONObject sys = root.getJSONObject("sys");
    //someothers
    JSONObject coords = root.getJSONObject("coord");
    //weather
    LocalDateTime now = LocalDateTime.now(); 
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    System.out.println(dtf.format(now)); 
    
    JSONArray wea = root.getJSONArray("weather");
    JSONObject weas = wea.getJSONObject(0);
    ModelAndView mv = new ModelAndView("weatherResult");
    int celcis = main.getInt("temp") - 273;
    Weather weather = new Weather(
            weas.getString("main"),
            weas.getString("description"),
            celcis,
            sys.getString("country"),
            root.getString("name"),
            coords.getInt("lon"),
            coords.getInt("lon"),
            main.getInt("humidity"),
            dtf.format(now)

            );
    mv.addObject("weather",weather);
    try {
		Weather _weather = weatherRepository.save(new Weather(
	            weas.getString("main"),
	            weas.getString("description"),
	            celcis,
	            sys.getString("country"),
	            root.getString("name"),
	            coords.getInt("lon"),
	            coords.getInt("lat"),
	            main.getInt("humidity"),
	            dtf.format(now)

	            ));
		System.out.println(_weather);
		
	} catch (Exception e) {
		System.out.println("Error while saving to the local database.");
	}
    
    return mv;
	
           
}
}
