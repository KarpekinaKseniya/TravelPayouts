package com.self.education.travelpayouts.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.mapper.UserMapper;
import com.self.education.travelpayouts.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::transformEntityToResponse).toList();
    }

    @Override
    public Long createNewUser(final UserRequest request) {
        return userRepository.save(userMapper.transformRequestToEntity(request)).getId();
    }

    @Override
    public Users findUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User", email));
    }
}
