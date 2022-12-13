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

    public Status getStatusById(int id) {
        return statusRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveStatus(Status status) {
        statusRepository.save(status);
    }

    @Transactional
    public void updateStatus(int id, Status status) {
        status.setId(id);
        statusRepository.save(status);
    }
}
