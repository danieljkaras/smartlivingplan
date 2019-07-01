package com.danielkaras.smartlivingplan.repository;

import com.danielkaras.smartlivingplan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByIdEquals(Long userId);

    User findByEmail(String email);
}
