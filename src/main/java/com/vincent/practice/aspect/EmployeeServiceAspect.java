package com.vincent.practice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * advice <br>
 * <ul>
 * <li>Before</li>
 * <li>After</li>
 * <li>AfterReturning</li>
 * <li>AfterThrowing</li>
 * <li>Around</li>
 * </ul>
 */
@Aspect
@Component
public class EmployeeServiceAspect {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceAspect.class);

  @Before(value = "execution(* com.vincent.practice.service.EmployeeService.*(..)) and args(name,empId)")
  public void beforeAdvice(JoinPoint joinPoint, String name, String empId) {
    logger.info("Before method: {}", joinPoint.getSignature());
    logger.info("Creating Employee with name - {} and id - {}", name, empId);
  }

  @After(value = "execution(* com.vincent.practice.service.EmployeeService.*(..)) and args(name,empId)")
  public void afterAdvice(JoinPoint joinPoint, String name, String empId) {
    logger.info("After method: {}", joinPoint.getSignature());
    logger.info("Successfully created Employee with name - {} and id - {}", name, empId);
  }

  // @AfterReturning(value = "execution(*
  // com.vincent.practice.service.EmployeeService.*(..)) and args(name,empId)")
  // public void afterReturningAdvice(JoinPoint joinPoint, String name, String
  // empId) {
  // logger.info("AfterReturning method: {}", joinPoint.getSignature());
  // }

  // @AfterThrowing(value = "execution(*
  // com.vincent.practice.service.EmployeeService.*(..)) and args(name,empId)")
  // public void afterThrowingAdvice(JoinPoint joinPoint, String name, String
  // empId) {
  // logger.info("AfterReturning method: {}", joinPoint.getSignature());
  // }

  // @Around(value = "execution(*
  // com.vincent.practice.service.EmployeeService.*(..)) and args(name,empId)")
  // public void aroundAdvice(JoinPoint joinPoint, String name, String empId) {
  // logger.info("aroundAdvice method: {}", joinPoint.getSignature());
  // }
}
