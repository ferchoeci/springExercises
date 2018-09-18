package com.tigoune.rest.webservices.resfulwebservices.user;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tigoune.rest.webservices.resfulwebservices.exception.EmptyUsersException;
import com.tigoune.rest.webservices.resfulwebservices.exception.UserNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;

	//GET /users
	//retreiveAllUserResource
	@GetMapping("/users")
	public List<User> retreiveAllUsers(){
		List<User> users = service.findAll();
		if (users == null || users.size()==0)
				throw new EmptyUsersException("no users to show - " + users.size());
		
	 return users;
	}
	
	
	//GET /users/{id}
	//retreiveUser(int id)
	@GetMapping("/users/{id}")
	public Resource<User> retreiveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		 if(user==null)
			throw new UserNotFoundException("id - " + id);
		 
		 //HATEOAS 
		 //"all-users, SERVER_PATH + /users"
		 //retreiveAllUsers
		 
		 Resource<User> resource =  new Resource<User>(user);
		 ControllerLinkBuilder linkTo = 
				 linkTo(methodOn(this.getClass()).retreiveAllUsers());
		 resource.add(linkTo.withRel("all-users"));
		return resource;	
	}
	
	//CREATED
	//Details of the users as input
	//Output, CREATED and URI
	@PostMapping("/users")
	public ResponseEntity<Object> createdUser(@Valid @RequestBody User user) {
		User savedUser= service.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//DELETE
	//deleteUserById(int id)
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		 if (user == null) {
			 throw new UserNotFoundException("id - " + id);
		 }
	}

}
