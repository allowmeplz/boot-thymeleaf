package idu.cs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
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

import idu.cs.domain.User;
import idu.cs.entity.UserEntity;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repositories.UserRepository;
import idu.cs.service.UserService;

@Controller //@Componenet 
// Spring Framework에게 이 클래스로부터 작성된 객체는 Controller 역할을 함을 알려줌
// Spring이 이 클래스로부터 
public class UserController {
	@Autowired UserService userService; // Dependency Injection
	@Autowired UserRepository userRepo;
	// 원래의 사용방법
	// UserRepository userRepo = new UserRepositoryImpl(); <- interface를 구현화 시킨것(직접해야함)
	
	@RequestMapping("/")
	public String home(Model model) {
	
		return "index";
	}
	
	@GetMapping("/user-login-form") 
	public String getLoginForm(Model model) {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@Valid UserEntity user, HttpSession session) {
		//System.out.println("login process : " + user.getUserId() + user.getUserPw());
		User sessionUser = userService.getUserByUserId(user.getUserId());
		if(sessionUser == null) {
			System.out.println("id error.. : ");
			return "redirect:/user-login-form";
		}
		if(!sessionUser.getUserPw().equals(user.getUserPw())) {
			System.out.println("pw error.. : ");
			return "redirect:/user-login-form";
		}
		session.setAttribute("user", sessionUser);
		//userRepo.save(user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		session.removeAttribute("user");
		//session.invalidate(); // 모든 세션정보가 사라짐 (이걸 더 많이 씀)
		return "redirect:/";
	}
	
	@GetMapping("/user-register-form") 
	public String getRegForm(Model model) {
		return "register";
	}
	
	@PostMapping("/regist")
	public String registUser(@Valid UserEntity user, Model model) {
		if(userRepo.save(user) == null)
			return "redirect:/user-register-form";
		model.addAttribute("user", userRepo.findAll());
		return "redirect:/userlist";
	}
	
	@GetMapping("/user-update-form") 
	public String getUpdatForm(HttpSession session) {
		return "update";
	}
	
	@PostMapping("/update")
	public String update(@Valid UserEntity user, HttpSession session) {
		if(userRepo.save(user) == null)
			return "redirect:/user-update-form";
		session.setAttribute("user", user);
		return "redirect:/userlist";
	}
	
	@GetMapping("/userlist")
	public String getAllUser(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "userlist";
	}
	
	@PostMapping("/users")
	public String createUser(@Valid @RequestBody User user, Model model) {
		userService.saveUser(user);
		return "redirect:/users";  // Get 방식으로 redirect
	}	
	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model)
			throws ResourceNotFoundException {
		UserEntity user = userRepo.findById(userId).get(); //.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
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
		List<UserEntity> users = userRepo.findByName(name);
		model.addAttribute("users",users);
		return "userlist";
		//return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/users/{id}")
	public String updateUser(@PathVariable(value = "id") Long userId,@Valid UserEntity userDetails, Model model) {
		UserEntity user = userRepo.findById(userId).get();
		user.setName(userDetails.getName());
		user.setCompany(userDetails.getCompany());
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}	
	
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		UserEntity user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		return "user-deleted";
	}
}
