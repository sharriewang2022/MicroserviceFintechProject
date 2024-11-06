package com.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.model.MongoEmployee;

/**
 
 * @author : wangsy1
 * Date: 2024/11/04 
 * Description: Employee model 
 **/

@Repository
public interface MongoEmployeeDao extends MongoRepository<MongoEmployee, Long> {

    /**
     * query by name
     *
     * @param userName
     * @return
     */
    MongoEmployee findByUserName(String userName);

}
