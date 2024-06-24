package com.shopme.admin;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	
	@GetMapping("")
	public String viewHomePage(HttpServletRequest request) {
		
		Locale currentLocale = request.getLocale();
		String countryCode = currentLocale.getCountry();
		String countryName = currentLocale.getDisplayCountry();
		
		String langCode = currentLocale.getLanguage();
		String langName = currentLocale.getDisplayCountry();
		
		System.out.println(countryCode + " : " + countryName);
		System.out.println(langCode + " : " + langName);
		
		
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}
}
