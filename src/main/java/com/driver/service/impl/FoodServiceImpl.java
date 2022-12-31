package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;


public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto foodDto){
        FoodEntity foodEntity = dtoConverter(foodDto);
        foodRepository.save(foodEntity);
        return entityConverter(foodEntity);
    }

    @Override
    public FoodDto getFoodById(String foodId){
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        return entityConverter(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> foodDtoList = new ArrayList<>();
        List<FoodEntity> foodEntityList = (List<FoodEntity>) foodRepository.findAll();
        for (FoodEntity foodEntity : foodEntityList) {
            foodDtoList.add(entityConverter(foodEntity));
        }
        return foodDtoList;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        foodEntity.setFoodPrice(foodEntity.getFoodPrice());
        foodEntity.setFoodCategory(foodEntity.getFoodCategory());
        foodEntity.setFoodName(foodEntity.getFoodName());
        foodRepository.save(foodEntity);
        return entityConverter(foodEntity);
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        foodRepository.delete(foodEntity);
    }

    private FoodDto entityConverter(FoodEntity foodEntity) {
        return FoodDto.builder().id(foodEntity.getId()).
                foodId(foodEntity.getFoodId()).
                foodName(foodEntity.getFoodName()).
                foodCategory(foodEntity.getFoodCategory()).
                foodPrice(foodEntity.getFoodPrice()).build();
    }

    private FoodEntity dtoConverter(FoodDto foodDto){
        return FoodEntity.builder().
                foodId(foodDto.getFoodId()).
                foodCategory(foodDto.getFoodCategory()).
                foodName(foodDto.getFoodName()).
                foodPrice(foodDto.getFoodPrice()).build();
    }
}