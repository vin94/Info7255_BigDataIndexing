package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.JedisBean;
import com.example.beans.MyJsonValidator;

@RestController
public class SchemaController {
	
	@Autowired
	private MyJsonValidator validator;
	
	@Autowired
	private JedisBean jedisBean;
	
	@PostMapping("/Plan/Schema")
	public ResponseEntity<String> insertSchema(@RequestBody(required=true) String body) {
		if(body == null) {
			return new ResponseEntity("No Schema received", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		// receive token and validate
		
		// set json schema in redis
		if(!jedisBean.insertSchema(body))
			return new ResponseEntity("Schema insertion failed", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		validator.refreshSchema();
		return new ResponseEntity("Schema posted successfully", new HttpHeaders(), HttpStatus.ACCEPTED);
	}
	

}
