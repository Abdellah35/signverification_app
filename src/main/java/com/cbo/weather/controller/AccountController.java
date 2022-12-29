package com.cbo.weather.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cbo.weather.model.AccountEnquery;
import com.cbo.weather.repo.AccountRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class AccountController {

	@Autowired
	AccountRepository accountRepository;
	
	
	@GetMapping("account")
    public ModelAndView getAccount(@RequestParam("id") int accountid) {
    	
    	Unirest.setTimeouts(0, 0);
        /////////////////
    	String shortName = "";
    	String statusrec = "Failure";
    	String responceCode = "ESB_BRK_200";
    	//////
                try {
    				HttpResponse<String> response1 = Unirest.post("http://10.1.245.150:7081/v1/cbo/").header("Content-Type", "application/json").body("{\r\n    \"AwachAccountEnquiryRequest\": {\r\n        \"ESBHeader\": {\r\n            \"serviceCode\": \"650000\",\r\n            \"channel\": \"USSD\",\r\n            \"Service_name\": \"AwachAccountEnquiry\",\r\n            \"Message_Id\": \"6255726662\"\r\n        },\r\n        \"AccountEnquiry\": {\r\n            \"accountNumber\": \"" + accountid + "\"\r\n        }\r\n    }\r\n}").asString();
    				
    				JSONObject root1 = new JSONObject(response1.getBody());
    				JSONObject main = root1.getJSONObject("AwachAccountEnquiryResponse");
    				
    				JSONObject ESBStatus = main.getJSONObject("ESBStatus");
    				JSONObject ESBHeader = main.getJSONObject("ESBHeader");
    				System.out.println(ESBStatus);
    				//statusrec = ESBStatus.getString("status");
    				responceCode = ESBStatus.getString("responseCode");
    				System.out.println("me: "+statusrec.equals("Succes"));
    				if (responceCode.equals("ESB_BRK_000")) {
    					JSONObject Status = main.getJSONObject("Status");
        				shortName = Status.getString("shortName");
        				System.out.println(Status);
        				//Saving the data fetched from the API responce to the local database.
        				try {
        					List<AccountEnquery> accounts = new ArrayList<AccountEnquery>();
        					accountRepository.findByNameContaining(shortName).forEach(accounts::add);
        					if(accounts.isEmpty()) {
        						AccountEnquery _account = accountRepository.save(new AccountEnquery(accountid, shortName));
                    			System.out.println(_account);
        					}
                			
                			
                		} catch (Exception e) {
                			System.out.println("Error while saving to the local database.");
                		}
    				}
    				else {
    					shortName = "Account doesn't exist.";
    				}
    				
    				
    				System.out.println(main);
    				
    				System.out.println(ESBStatus);
    				System.out.println(ESBHeader);
    				System.out.println(shortName);
    			} catch (UnirestException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                
                ////////
                
                //////
        	    // create a new `ModelAndView` object
                AccountEnquery accque = new AccountEnquery(accountid, shortName);
        	    ModelAndView mv = new ModelAndView("index");
        	    mv.addObject("accounts",accque);
        
                System.out.println(accque.getName());
                return mv;
    }
    
	
	@GetMapping("local/accounts")
	public ModelAndView getAllTutorials(@RequestParam(required = false) String accountId) {
		try {
			List<AccountEnquery> accounts = new ArrayList<AccountEnquery>();

			if (accountId == null)
				accountRepository.findAll().forEach(accounts::add);
			else
				accountRepository.findAll().forEach(accounts::add);

			if (accounts.isEmpty()) {
				ModelAndView mv = new ModelAndView("localaccounts");
				 mv.addObject("accounts",accounts);
				return mv;
			}

			ModelAndView mv = new ModelAndView("localaccounts");
			 mv.addObject("accounts",accounts);
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("localaccounts");
			 mv.addObject("accounts",null);
			return mv;
		}
	}
	
}
