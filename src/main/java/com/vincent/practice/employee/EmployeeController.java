package com.vincent.practice.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class EmployeeController {

  // @Autowired
  // private EmployeeService employeeService; // employeeService = null;

  private final EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @ApiOperation(value = "簡單描述", notes = "詳細描述", nickname = "外號")
  @GetMapping("/add/employee")
  public Employee addEmployee(
      @ApiParam(value = "name", example = "Vincent",
          required = true) @RequestParam("name") String name,
      @ApiParam(value = "員工編號", example = "2377",
          required = true) @RequestParam("empId") String empId) {
    // employeeService = null;
    return employeeService.createEmployee(name, empId);

  }

  // @RequestMapping(value = "/remove/employee", method = RequestMethod.GET)
  @DeleteMapping("/remove/employee")
  public String removeEmployee(@ApiParam(value = "員工編號", example = "2377",
      required = true) @RequestParam("empId") String empId) {

    employeeService.deleteEmployee(empId);

    return "Employee removed";
  }

}
