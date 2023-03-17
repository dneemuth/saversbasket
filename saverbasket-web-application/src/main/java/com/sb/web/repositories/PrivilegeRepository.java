package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.web.entities.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
