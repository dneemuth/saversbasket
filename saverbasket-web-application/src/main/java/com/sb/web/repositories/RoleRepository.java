package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.web.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByRole(String role);

}
