package com.self.education.travelpayouts.service;

import static java.lang.String.format;
import static com.self.education.travelpayouts.domain.SubscribeStatus.SUBSCRIBED;

import java.util.List;
import org.springframework.stereotype.Service;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.domain.SubscriptionsId;
import com.self.education.travelpayouts.domain.Users;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.mapper.PartnershipProgramsMapper;
import com.self.education.travelpayouts.mapper.UserMapper;
import com.self.education.travelpayouts.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PartnershipProgramsMapper programsMapper;
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

    @Override
    public List<ProgramResponse> findAllUserProgramsById(final Long id) {
        //@formatter:off
        return userRepository.findUsersById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", format("id = %d", id)))
                .getSubscriptions().stream().filter(s -> SUBSCRIBED.equals(s.getSubscribeStatus()))
                .map(Subscriptions::getPrimaryKey)
                .map(SubscriptionsId::getProgram)
                .map(programsMapper::transform).toList();
        //@formatter:on
    }
}
