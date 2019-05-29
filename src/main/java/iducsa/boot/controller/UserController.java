package iducsa.boot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import idu.cs.exception.ResourceNotFoundException;
import iducsa.boot.domain.User;
import iducsa.boot.repositories.UserRepository;

@Controller
public class UserController {
	@Autowired UserRepository userRepo; // Dependency Injection 
	// 원래의 사용방법
	// UserRepository userRepo = new UserRepositoryImpl(); <- interface를 구현화 시킨것(직접해야함)
	
	@RequestMapping("/")
	public String home(Model model) {
	
		return "index";
	}
	
	@GetMapping("/user-login") 
	public String getLoginForm(Model model) {
		return "login";
	}
	
	@GetMapping("/user-reg-form") 
	public String getRegForm(Model model) {
		return "form";
	}
	
	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	
	@PostMapping("/users")
	public String createUser(@Valid @RequestBody User user, Model model) {
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}	
	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model)
			throws ResourceNotFoundException {
		User user = userRepo.findById(userId).get(); //.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		// Java Lambda
		/*model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("company", user.getCompany());*/
		model.addAttribute("user",user);
		return "user";
		//return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/users/fn")
	public String getUserByName(@Param(value="name") String name, Model model)
			throws ResourceNotFoundException {
		List<User> users = userRepo.findByName(name);
		model.addAttribute("users",users);
		return "userlist";
		//return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/users/{id}")
	public String updateUser(@PathVariable(value = "id") Long userId,@Valid User userDetails, Model model) {
		User user = userRepo.findById(userId).get();
		user.setName(userDetails.getName());
		user.setCompany(userDetails.getCompany());
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}	
	
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		User user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		return "user-deleted";
	}	
}
