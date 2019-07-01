package com.danielkaras.smartlivingplan.repository;

import com.danielkaras.smartlivingplan.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);
}
