package com.atashgah.dto;

import com.atashgah.model.User;

public class UserMapper {

    public static User toEntity(UserRegistrationDTO dto) {
        User user = new User();
        user.setFirstname(dto.getFirstname());
        user.setPin(dto.getPin());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Ensure the password is encoded elsewhere
        return user;
    }

    public static UserRegistrationDTO toRegistrationDto(User user) {
    	UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPin(user.getPin());
        return dto;
    }
    public static UserLoginResponse toLoginResponse(User user) {
    	UserLoginResponse response=new UserLoginResponse();
    	response.setFirstname(user.getFirstname());
    	response.setLastname(user.getLastname());
    	response.setPin(user.getPin());
    	return response;
    }
}