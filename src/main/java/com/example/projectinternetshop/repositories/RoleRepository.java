package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, CrudRepository<Role, Integer> {
}
