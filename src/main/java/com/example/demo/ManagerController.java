package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  
  @GetMapping("/couponList")
  public String couponList(Model  model, HttpSession session)
  {
	  return service.couponList(model,session);
  }

  @PostMapping("couponOk")
  public String couponOk(CouponDto cdto, HttpSession session)
  {
	  return service.couponOk(cdto,session);
  }

  @GetMapping("/delCoupon")
  public String delCoupon(@RequestParam("id")int id)
  {
	  return service.delCoupon(id);
  }

  @PostMapping("/upCoupon")
  public String upCoupon(CouponDto cdto)
  {
	  return service.upCoupon(cdto);
  }

}
