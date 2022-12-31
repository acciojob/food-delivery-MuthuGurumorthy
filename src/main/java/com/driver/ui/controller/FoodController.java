package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.FoodService;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {
	@Autowired
	FoodServiceImpl foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse = dtoConverter(foodDto);
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto = requestConverter(foodDetails);
		FoodDto foodDtoResponse = foodService.createFood(foodDto);
		return dtoConverter(foodDtoResponse);
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto = requestConverter(foodDetails);
		FoodDto foodDtoResponse = foodService.updateFoodDetails(id,foodDto);
		return dtoConverter(foodDtoResponse);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		foodService.deleteFoodItem(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete "+id);
		operationStatusModel.setOperationResult(id+" deleted Succesfully");
		return operationStatusModel;
	}

	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();
		List<FoodDto> foodDtoList = foodService.getFoods();
		for(FoodDto foodDto : foodDtoList){
			foodDetailsResponseList.add(dtoConverter(foodDto));
		}
		return foodDetailsResponseList;
	}

	public FoodDetailsResponse dtoConverter(FoodDto foodDto){
		return FoodDetailsResponse.builder().foodId(foodDto.getFoodId()).
				foodName(foodDto.getFoodName()).
				foodCategory(foodDto.getFoodCategory()).
				foodPrice(foodDto.getFoodPrice()).build();
	}

	public FoodDto requestConverter(FoodDetailsRequestModel foodDetailsRequestModel){
		return FoodDto.builder().foodName(foodDetailsRequestModel.getFoodName()).
				foodCategory(foodDetailsRequestModel.getFoodCategory()).
				foodPrice(foodDetailsRequestModel.getFoodPrice()).build();
	}
}