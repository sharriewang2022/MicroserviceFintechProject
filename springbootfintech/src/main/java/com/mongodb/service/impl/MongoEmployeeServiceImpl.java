package com.mongodb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.dao.MongoEmployeeDao;
import com.mongodb.model.MongoEmployee;
import com.mongodb.service.MongoEmployeeService;

import java.util.List;
import java.util.Optional;


@Service
public class MongoEmployeeServiceImpl implements MongoEmployeeService {
    private final MongoEmployeeDao dao;

    @Autowired
    public MongoEmployeeServiceImpl(MongoEmployeeDao dao) {
        this.dao = dao;
    }

    @Override
    public List<MongoEmployee> findAll() {
        return dao.findAll();
    }

    @Override
    public MongoEmployee findById(Long id) {
        Optional<MongoEmployee> optionalUser = dao.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public MongoEmployee findByName(String userName) {
        return dao.findByUserName(userName);
    }

    @Override
    public MongoEmployee add(MongoEmployee MongoEmployee) {
        return dao.save(MongoEmployee);
    }

    @Override
    public void delete(Long id) {
        Optional<MongoEmployee> optional = dao.findById(id);
        if (!optional.isPresent()) {
            return;
        }
        dao.delete(optional.get());
    }

    @Override
    public MongoEmployee update(MongoEmployee MongoEmployee) {
        return dao.save(MongoEmployee);
    }
    
    
}
