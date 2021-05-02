package com.vincent.practice.controller;

import com.vincent.practice.model.Employee;
import com.vincent.practice.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @ApiOperation(value = "API 簡單描述", notes = "API 詳細描述", nickname = "API 外號")
  @GetMapping("/add/employee")
  public Employee addEmployee(@RequestParam("name") String name, @RequestParam("empId") String empId) {

    return employeeService.createEmployee(name, empId);

  }

  @RequestMapping(value = "/remove/employee", method = RequestMethod.GET)
  public String removeEmployee(@RequestParam("empId") String empId) {

    employeeService.deleteEmployee(empId);

    return "Employee removed";
  }

}
