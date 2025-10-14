package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ManagerController {
	@Autowired
	private ManagerService service;

  @GetMapping("/")
  public String home() {
      return "redirect:/main";
  }

  @GetMapping("/main")
  public String main() {
      return "/main";
  }
  
  
}
