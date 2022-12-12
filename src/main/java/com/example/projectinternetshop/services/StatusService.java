package com.example.projectinternetshop.services;

import com.example.projectinternetshop.models.Status;
import com.example.projectinternetshop.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatusService {

    private final StatusRepository statusRepository;
    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> findAll(){
        return statusRepository.findAll();
    }
}
