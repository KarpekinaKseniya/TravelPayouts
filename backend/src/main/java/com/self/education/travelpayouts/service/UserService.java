package com.self.education.travelpayouts.service;

import java.util.List;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;

public interface UserService {

    List<UserResponse> findAllUsers();

    Long createNewUser(UserRequest request);

    Users findUserByEmail(String email);

    List<ProgramResponse> findAllUserProgramsById(Long id);
}
