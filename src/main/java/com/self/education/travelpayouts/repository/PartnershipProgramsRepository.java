package com.self.education.travelpayouts.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.self.education.travelpayouts.domain.PartnershipPrograms;

@Repository
public interface PartnershipProgramsRepository extends JpaRepository<PartnershipPrograms, Long> {

    Optional<PartnershipPrograms> findByTitle(String title);

    List<PartnershipPrograms> findByTitleContainingIgnoreCaseOrderBySubscriberCountDesc(String title);
}
