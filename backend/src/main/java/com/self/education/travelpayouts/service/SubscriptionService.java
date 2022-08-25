package com.self.education.travelpayouts.service;

import com.self.education.travelpayouts.domain.SubscribeStatus;

public interface SubscriptionService {

    void changeSubscribeStatus(String userEmail, String programTitle, SubscribeStatus subscribeStatus);
}
