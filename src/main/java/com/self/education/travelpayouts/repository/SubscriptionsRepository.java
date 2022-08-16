package com.self.education.travelpayouts.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.self.education.travelpayouts.domain.PartnershipPrograms;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.domain.SubscriptionsId;
import com.self.education.travelpayouts.domain.Users;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, SubscriptionsId> {

    Optional<Subscriptions> findByPrimaryKeyUserAndPrimaryKeyProgram(Users user, PartnershipPrograms program);
}
