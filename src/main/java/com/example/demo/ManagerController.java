package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;


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
  
  @PostMapping("/loginOk")
  public String loginOk(AdminMemberDto mdto,
      HttpSession session)
  {
    return service.loginOk(mdto,session);
  }

  @GetMapping("/logout")
  public String logout(HttpSession session)
  {
    return service.logout(session);
  }
}
