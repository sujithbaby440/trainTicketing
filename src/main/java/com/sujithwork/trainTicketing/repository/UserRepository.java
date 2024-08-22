package com.sujithwork.trainTicketing.repository;

import com.sujithwork.trainTicketing.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
