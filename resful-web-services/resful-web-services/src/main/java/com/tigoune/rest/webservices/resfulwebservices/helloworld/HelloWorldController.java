package com.tigoune.rest.webservices.resfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	
	//GET
	//URI  -/hello-world
	//method "Hello World"
	//@RequestMapping (method=RequestMethod.GET,path= "/hello-world")
	//EasyWay to avoid the last line
	@GetMapping(path= "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping(path= "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Fer Parra");
	}
	
	///hello-world/path-variable/fer
	@GetMapping(path= "/hello-world/path-variable/{name}")
	
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s",name));
	}
	
	
	
	@GetMapping(path= "/hello-world-internationalized")
	//public String helloWorldinternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
    //return messageSource.getMessage("good.morning.message",null, locale);

	//Lo anterior no es necesario, tan solo se puede declarar que trabaje en el contexto adecuado, ver abajo
	public String helloWorldinternationalized() {
			
		return messageSource.getMessage("good.morning.message",null, "Whoops!", LocaleContextHolder.getLocale());
	
	}
	
	
}
