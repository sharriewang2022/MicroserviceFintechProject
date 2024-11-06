package com.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.hibernate.service.UserService;

//    @GetMapping
//    public List<User> getAllUsers() {
//   
//        return userService.findAll();
//    }
//
//    @PostMapping
//    public User createUser(@RequestBody User user) {
//   
//        return userService.save(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//   
//        userService.delete(id);
//    }
    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import net.javaguides.springboot.entity.User;
    import net.javaguides.springboot.exception.ResourceNotFoundException;
    import net.javaguides.springboot.repository.UserRepository;

    @RestController
    @RequestMapping("/api/users")
    public class UserController {
       

        @Autowired
        private UserService userService;

    	// get all users
    	@GetMapping
    	public List<User> getAllUsers() {
    		return userService.findAll();
    	}

    	// get user by id
    	@GetMapping("/{id}")
    	public User getUserById(@PathVariable (value = "id") long userId) {
    		return userService.findById(userId)
    				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    	}

    	// create user
    	@PostMapping
    	public User createUser(@RequestBody User user) {
    		return userService.save(user);
    	}
    	
    	// update user
    	@PutMapping("/{id}")
    	public User updateUser(@RequestBody User user, @PathVariable ("id") long userId) {
    		 User existingUser = this.userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    		 existingUser.setFirstName(user.getFirstName());
    		 existingUser.setLastName(user.getLastName());
    		 existingUser.setEmail(user.getEmail());
    		 return userService.save(existingUser);
    	}
    	
    	// delete user by id
    	@DeleteMapping("/{id}")
    	public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
    		 User existingUser = this.userRepository.findById(userId)
    					.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    		 userService.delete(existingUser);
    		 return ResponseEntity.ok().build();
    	}
    }