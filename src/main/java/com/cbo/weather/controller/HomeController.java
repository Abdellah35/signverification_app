package com.cbo.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cbo.weather.model.AccountEnquery;
import com.cbo.weather.model.Weather;

@RestController
public class HomeController {

	   @GetMapping("/")
	    public ModelAndView home() {
	    	    // creating modelview for home page of weather and account query
	    	    ModelAndView mv = new ModelAndView("accountform");
	    	    mv.addObject("account",new AccountEnquery());
	    	    mv.addObject("weather",new Weather());
	    	return mv;
	    }
}
