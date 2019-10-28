package com.server.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.master.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("users")
	public @ResponseBody User getUsers(@RequestParam Integer Id) {
		System.out.println("request accept");
		return null;
	}
	
	@PostMapping("users")
	public String addUser(@RequestBody User userdata) {
		System.out.println(userdata.getUsername());
		userRepository.save(userdata);
		return "saved";
	}
	
	@PostMapping("login")
	public @ResponseBody String login(@RequestBody User loginData) {
		String username = loginData.getUsername();
		String password = loginData.getPassword();
		User u = userRepository.findUser(username, password);
		if(u == null)
			return "false";
		return u.getEmail();
	}
}
