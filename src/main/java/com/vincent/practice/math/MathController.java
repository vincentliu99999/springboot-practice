package com.vincent.practice.math;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
  public MathController(IMathService mathService, MathService2 mathService2) {
    this.mathService = mathService;
    this.mathService2 = mathService2;
  }

  private final IMathService mathService;
  private final IMathService mathService2;

  @ApiOperation(
      value = "加法",
      notes = "# 小學數學提 \n num1 + num2 = ?\n * 1 + 1 = 2\n * <b>2 + 2 = 4</b>\n")
  @GetMapping("/add")
  public String add(
      @ApiParam(value = "first number", example = "1", required = true) @RequestParam("number1")
          int num1,
      @ApiParam(value = "second number", example = "2", required = true) @RequestParam("number2")
          int num2) {
    return String.format(
        "%d + %d = %d%n%n胡亂加法%n %d + %d = %d",
        num1, num2, mathService.add(num1, num2), num1, num2, mathService2.add(num1, num2));
  }

  @ApiOperation(
      value = "減法",
      notes = "# 國中數學提 \n num1 - num2 = ?\n * 1 - 1 = 0\n * <b>1 - 2 = -1</b>\n")
  @GetMapping("/minus")
  public String minus(
      @ApiParam(value = "first number", example = "1", required = true) @RequestParam("number1")
          int num1,
      @ApiParam(value = "second number", example = "2", required = true) @RequestParam("number2")
          int num2) {
    return String.format(
        "%d - %d = %d%n%n胡亂減法%n %d - %d = %d",
        num1, num2, mathService.minus(num1, num2), num1, num2, mathService2.minus(num1, num2));
  }

  @ApiOperation(
      value = "乘法",
      notes = "# 國中數學提 \n num1 * num2 = ?\n * 1 * 1 = 1\n * <b>1 * 2 = 2</b>\n")
  @GetMapping("/multiply")
  public String multiply(
      @ApiParam(value = "first number", example = "1", required = true) @RequestParam("number1")
          int num1,
      @ApiParam(value = "second number", example = "2", required = true) @RequestParam("number2")
          int num2) {
    return String.format(
        "%d * %d = %d%n胡亂減法%n %d * %d = %d",
        num1,
        num2,
        mathService.multiply(num1, num2),
        num1,
        num2,
        mathService2.multiply(num1, num2));
  }

  @ApiOperation(
      value = "除法",
      notes = "# divide \n num1 / num2 = ?\n * 1 / 1 = 1\n * <b>1 / 2 = 0</b>\n")
  @GetMapping("/divide")
  public String divide(
      @ApiParam(value = "first number", example = "1", required = true) @RequestParam("number1")
          int num1,
      @ApiParam(value = "second number", example = "2", required = true) @RequestParam("number2")
          int num2) {
    return String.format(
        "%d / %d = %d%n胡亂除法%n %d / %d = %d",
        num1, num2, mathService.divide(num1, num2), num1, num2, mathService2.divide(num1, num2));
  }

  @ApiOperation(
      value = "sample note",
      notes =
          "# Head 1 \n"
              + "## Head 2 \n"
              + "### Sorting rules\n"
              + "The data is sorted by priority (from the highest to the lowest).<br/> Unordered"
              + " list \n"
              + " * item 1.\n"
              + " * <b>bold item 2</b>\n",
      nickname = "外號")
  @GetMapping("/sample")
  public String sample(
      @ApiParam(value = "first number", example = "1", required = true) @RequestParam("number1")
          int num1,
      @ApiParam(value = "second number", example = "1", required = true) @RequestParam("number2")
          int num2) {
    int result = num1 + num2;

    return String.format("%d + %d = %d", num1, num2, num1 + num2);
  }
}
