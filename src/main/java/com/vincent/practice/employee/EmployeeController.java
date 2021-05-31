package com.vincent.practice.employee;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  // ! this will cause Null Pointer Exception
  // @Autowired
  // private EmployeeService employeeService;

  private final EmployeeService employeeService;
  private final MathService mathService;

  @Autowired
  public EmployeeController(EmployeeService employeeService, MathService mathService) {
    this.employeeService = employeeService;
    this.mathService = mathService;
  }

  @ApiOperation(value = "簡單描述", notes = "詳細描述", nickname = "外號")
  @GetMapping("/add/employee")
  public Employee addEmployee(
      @ApiParam(value = "name", example = "Vincent", required = true) @RequestParam("name")
          String name,
      @ApiParam(value = "員工編號", example = "2377", required = true) @RequestParam("empId")
          String empId) {
    // ! NullPointerException found: employeeService = null
    System.out.println(mathService.add(1, 2));
    return employeeService.createEmployee(name, empId);
  }

  // @RequestMapping(value = "/remove/employee", method = RequestMethod.GET)
  @DeleteMapping("/remove/employee")
  public String removeEmployee(
      @ApiParam(value = "員工編號", example = "2377", required = true) @RequestParam("empId")
          String empId) {

    employeeService.deleteEmployee(empId);

    return "Employee removed";
  }
}
