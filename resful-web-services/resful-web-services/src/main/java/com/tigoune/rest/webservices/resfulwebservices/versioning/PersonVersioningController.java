package com.tigoune.rest.webservices.resfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	//URL Versioning TWITTER
	//Ejemplo: http://localhost:8087/v2/person
	@GetMapping("v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Sebastian Ramirez");
		
	}

	@GetMapping("v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Sebastian", "Andrada"));
		
	}
	
	//Request Parameter Versioning AMAZON
	//Ejemplo: http://localhost:8087/person/param?version=2
	@GetMapping(value="person/param",params="version=1")
	public PersonV1 paramV1() {
		return new PersonV1("Sebastian Ramirez");
		
	}

	@GetMapping(value="person/param",params="version=2")
	public PersonV2 paramV2() {
		return new PersonV2(new Name("Sebastian", "Andrada"));
		
	}
	
	//Headers Versioning  MICROSOFT
	//ejemplo: http://localhost:8087/person/header 
	//HEADER X-API-VERSION = 1
	@GetMapping(value="person/header",headers="X-API-VERSION=1")
	public PersonV1 headerV1() {
		return new PersonV1("Sebastian Ramirez");
		
	}

	@GetMapping(value="person/header",headers="X-API-VERSION=2")
	public PersonV2 headerV2() {
		return new PersonV2(new Name("Sebastian", "Andrada"));
		
	}
	
	//Content Negotiation or Accept Versioning or MIMETYPE versioning GITHUB
	@GetMapping(value="person/produces",produces="application/vnd.company.app-v1+json")
	public PersonV1 producesV1() {
		return new PersonV1("Sebastian Ramirez");
		
	}
	
	@GetMapping(value="person/produces",produces="application/vnd.company.app-v2+json")
	public PersonV2 producesV2() {
		return new PersonV2(new Name("Sebastian", "Andrada"));
		
	}
	
}
