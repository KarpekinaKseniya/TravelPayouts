package com.self.education.travelpayouts.service;

import java.util.List;
import com.self.education.travelpayouts.api.UserResponse;

public interface UserService {

    List<UserResponse> findAllUsers();
}
