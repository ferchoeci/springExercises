package com.tigoune.rest.webservices.resfulwebservices.user;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	//GET /users
	//retreiveAllUserResource
	@GetMapping("/jpa/users")
	public List<User> retreiveAllUsers(){
		return userRepository.findAll();
	}
	
	
	//GET /users/{id}
	//retreiveUser(int id)
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retreiveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		 if(!user.isPresent())
			throw new UserNotFoundException("id - " + id);
		 
		 //HATEOAS 
		 //"all-users, SERVER_PATH + /users"
		 //retreiveAllUsers
		 
		 Resource<User> resource =  new Resource<User>(user.get());
		 ControllerLinkBuilder linkTo = 
				 linkTo(methodOn(this.getClass()).retreiveAllUsers());
		 resource.add(linkTo.withRel("all-users"));
		return resource;	
	}
	
	//CREATED
	//Details of the users as input
	//Output, CREATED and URI
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createdUser(@Valid @RequestBody User user) {
		User savedUser= userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//DELETE
	//deleteUserById(int id)
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		 userRepository.deleteById(id);
	}
	
	
	//GET /users/{id}/posts
		//retreiveAllPostByUserResource
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUsers(@PathVariable int id) {
			Optional<User> userOptional = userRepository.findById(id);
			
			if(!userOptional.isPresent()) {
				throw new UserNotFoundException("id-" + id);
			}
			
			return userOptional.get().getPosts();
		}
	
	//Create a Post for a User
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		User user = userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

}
