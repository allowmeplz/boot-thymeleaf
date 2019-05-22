package iducsa.boot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import idu.cs.exception.ResourceNotFoundException;
import iducsa.boot.domain.User;
import iducsa.boot.repositories.UserRepository;

@Controller
public class WebThymeleafController {
	@Autowired UserRepository userRepo; // Dependency Injection 
	// 원래의 사용방법
	// UserRepository userRepo = new UserRepositoryImpl(); <- interface를 구현화 시킨것(직접해야함)
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("test", "인덕컴소");
		model.addAttribute("ljh", "이지훈");
		model.addAttribute("users", userRepo.findAll());
		return "index";
	}
	
	@GetMapping("/register") 
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
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		// Java Lambda
		
		model.addAttribute("name", user.getName());
		model.addAttribute("company", user.getCompany());
		return "user";
		//return ResponseEntity.ok().body(user);
	}
}
