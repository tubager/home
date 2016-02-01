package com.tubager.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tubager.service.LocationService;

@RestController
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@RequestMapping(value="/resource/ullist", method = RequestMethod.POST)
	public void uploadLocationList(@RequestBody LocationUpload data, HttpServletResponse response){
		locationService.saveTrace(data.getTechId(), data.getText());
		response.setStatus(HttpServletResponse.SC_OK);
	}
}

class LocationUpload{
	private String techId;
	private String text;
	
	public String getTechId() {
		return techId;
	}
	public void setTechId(String techId) {
		this.techId = techId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
