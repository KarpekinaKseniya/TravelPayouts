package com.self.education.travelpayouts.mapper;

import org.mapstruct.Mapper;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse transformEntityToResponse(Users user);
}
