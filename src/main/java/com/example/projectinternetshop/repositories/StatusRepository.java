package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByStatus(String status);
}
