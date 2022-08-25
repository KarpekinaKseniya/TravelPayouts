package com.self.education.travelpayouts.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface UserMapper {

    UserResponse transformEntityToResponse(Users user);

    Users transformRequestToEntity(UserRequest request);
}
