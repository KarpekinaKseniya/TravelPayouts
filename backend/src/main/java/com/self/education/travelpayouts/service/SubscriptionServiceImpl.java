package com.self.education.travelpayouts.service;

import static com.self.education.travelpayouts.domain.SubscribeStatus.SUBSCRIBED;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.self.education.travelpayouts.domain.PartnershipPrograms;
import com.self.education.travelpayouts.domain.SubscribeStatus;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.domain.SubscriptionsId;
import com.self.education.travelpayouts.domain.Users;
import com.self.education.travelpayouts.exception.ChangeSubscriptionStatusException;
import com.self.education.travelpayouts.repository.SubscriptionsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionsRepository subscriptionsRepository;
    private final UserService userService;
    private final PartnershipProgramsService programsService;

    @Override
    public void changeSubscribeStatus(final String userEmail, final String programTitle,
            final SubscribeStatus subscribeStatus) {
        final Users user = userService.findUserByEmail(userEmail);
        final PartnershipPrograms program = programsService.findPartnershipProgramByTitle(programTitle);
        final Optional<Subscriptions> subscription =
                subscriptionsRepository.findByPrimaryKeyUserAndPrimaryKeyProgram(user, program);

        if (subscription.isEmpty() && !subscribeStatus.equals(SUBSCRIBED)) {
            throw new ChangeSubscriptionStatusException(userEmail, programTitle, subscribeStatus.name());
        } else {
            final Subscriptions subscriptionForSave =
                    new Subscriptions(new SubscriptionsId(user, program), subscribeStatus);

            subscriptionsRepository.save(subscriptionForSave);
        }
    }
}
