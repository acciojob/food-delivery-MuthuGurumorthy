package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto = userService.getUserByUserId(id);
		return dtoConverter(userDto);
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = requestConverter(userDetails);
		UserDto userDtoResponse = userService.createUser(userDto);
		return dtoConverter(userDtoResponse);
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = requestConverter(userDetails);
		UserDto  userDtoResponse = userService.updateUser(id,userDto);
		return dtoConverter(userDtoResponse);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		userService.deleteUser(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete "+id);
		operationStatusModel.setOperationResult(id+" deleted Succesfully");
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserDto> userDtoList = userService.getUsers();
		List<UserResponse> userResponseList = new ArrayList<>();
		for(UserDto userDto : userDtoList){
			userResponseList.add(dtoConverter(userDto));
		}
		return userResponseList;
	}

	public UserResponse dtoConverter(UserDto userDto){
		return UserResponse.builder().
				userId(userDto.getUserId()).
				email(userDto.getEmail()).
				firstName(userDto.getFirstName()).
				lastName(userDto.getLastName()).build();
	}

	public UserDto requestConverter(UserDetailsRequestModel requestModel){
		return UserDto.builder().
				firstName(requestModel.getFirstName()).
				lastName(requestModel.getLastName()).
				email(requestModel.getEmail()).build();
	}
}
