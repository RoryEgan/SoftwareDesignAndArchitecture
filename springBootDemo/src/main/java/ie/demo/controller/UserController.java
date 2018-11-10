package ie.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ie.demo.domain.User;
import ie.demo.service.UserFactory;
import ie.demo.service.UserService;
import ie.response.MsgResponse;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserFactory userFactory;
	
	@RequestMapping(value= "/user", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public MsgResponse insertUser(@RequestParam(value = "username")String username,
								  @RequestParam(value = "password")String password,
								  @RequestParam(value = "studentCardId") int studentCardId,
								  @RequestParam(value = "email") String email) {
		User u = userFactory.createUser(username, password, studentCardId, email);
		System.out.println(u.getStudentCardId());
		int result = userService.register(u);
		if(result > 0) {
			return MsgResponse.success();
		} else {
			return MsgResponse.fail().add("error", "fail to register");
		}
	}

	@RequestMapping(value= "/user", method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public MsgResponse login(@RequestParam(value = "username")String username,
							 @RequestParam(value = "password")String password) {
		int result = userService.login(username, password);
		if(result > 0) {
			return MsgResponse.success();
		} else {
			return MsgResponse.fail().add("error", "fail to login");
		}
	}
}
