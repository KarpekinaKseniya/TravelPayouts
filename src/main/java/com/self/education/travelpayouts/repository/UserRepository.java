package com.self.education.travelpayouts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.self.education.travelpayouts.domain.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
