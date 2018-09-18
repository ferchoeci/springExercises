package com.tigoune.rest.webservices.resfulwebservices.filtering;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retreiveSomeBean() {
		
		SomeBean someBean= new SomeBean("value1","value2","value3");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		//return someBean;
		return mapping;
		
	}
	
	
	@GetMapping("/filtering-list")
	public List<SomeBean> retreiveListOfSomeBean() {
		SomeBean a = new SomeBean("value1","value2","value3");
		SomeBean b = new SomeBean("value11","value22","value33");
		SomeBean c = new SomeBean("value111","value222","value333");
		List<SomeBean> lista = new ArrayList<SomeBean>();
		lista.add(a);
		lista.add(b);
		lista.add(c);
		return lista;
	}
}
