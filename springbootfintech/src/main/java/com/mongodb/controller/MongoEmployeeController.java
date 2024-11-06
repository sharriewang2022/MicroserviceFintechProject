package com.mongodb.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mongodb.model.MongoEmployee;
import com.mongodb.service.MongoEmployeeService;

/**
 * @author : wangsy1
 * github:
 * email:  
 * <p>
 * Date: 2024/11/4
 * Copyright(©) 2024 by wangsy1
 **/

@RestController
@RequestMapping("mongodbEmployee")
@Api("mongodb Employee")
public class MongoEmployeeController {

    private final MongoEmployeeService service;

    @Autowired
    public MongoEmployeeController(MongoEmployeeService service) {
        this.service = service;
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable("id") Long id) {
        MongoEmployee mongoEmployee = service.findById(id);
        return new Result<>(mongoEmployee);
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {
        return new Result<>(service.findAll());
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result add(@RequestBody MongoEmployee employee) {
        return new Result<>(service.add(employee));
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public Result delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new Result<>(CodeConst.SUCCESS.getResultCode(), CodeConst.SUCCESS.getMessage());
    }

}
