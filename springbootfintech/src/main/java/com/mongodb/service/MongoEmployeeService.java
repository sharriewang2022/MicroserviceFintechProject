package com.mongodb.service;



import java.util.List;

import com.mongodb.model.MongoEmployee;

/**
 * @author wangsy1
 * 
 */

public interface MongoEmployeeService {

    /**
     *
     * @return
     */
    List<MongoEmployee> findAll();

    /**
     *
     * @param id
     * @return
     */
    MongoEmployee findById(Long id);

    /**
     *
     * @param userName
     * @return
     */
    MongoEmployee findByName(String employeeName);

    /**
     *
     * @param MongoEmployee
     * @return
     */
    MongoEmployee add(MongoEmployee mongoEmployee);

    /**
     *
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @param MongoEmployee
     * @return
     */
    MongoEmployee update(MongoEmployee mongoEmployee);

}
