package com.zta.zta.controller;

// import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.zta.zta.models.User;
import com.zta.zta.models.UserRequest;
import com.zta.zta.models.UserResponse;
import com.zta.zta.service.UserService;

@RestController
public class UserController {
	@Autowired
    private UserService userService;

	@PostMapping("/validate")
	public ResponseEntity<UserResponse> validation(@RequestBody UserRequest userRequest) {
		return userService.validation(userRequest);
	}

}
