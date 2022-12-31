package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        UserEntity userEntity = dtoConverter(userDto);
        userRepository.save(userEntity);
        return entityConverter(userEntity);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userRepository.save(userEntity);
        return entityConverter(userEntity);
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity= userRepository.findByEmail(email);
        return entityConverter(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        return entityConverter(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities  = (List<UserEntity>) userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            userDtoList.add(entityConverter(userEntity));
        }
        return userDtoList;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userRepository.delete(userEntity);
    }

    private UserEntity dtoConverter(UserDto user) {
        return UserEntity.builder().firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                userId(user.getUserId()).build();
    }

    private UserDto entityConverter(UserEntity userEntity) {
        return UserDto.builder().id(userEntity.getId()).
                userId(userEntity.getUserId()).
                firstName(userEntity.getFirstName()).
                lastName(userEntity.getLastName()).
                email(userEntity.getEmail()).build();
    }
}