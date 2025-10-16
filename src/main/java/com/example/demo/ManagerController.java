package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
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

  // 로그인
  @PostMapping("/loginOk")
  public String loginOk(AdminMemberDto mdto,
      HttpSession session)
  {
    return service.loginOk(mdto,session);
  }
  // 로그아웃
  @GetMapping("/logout")
  public String logout(HttpSession session)
  {
    return service.logout(session);
  }

  // 관리자 페이지
  @GetMapping("/memberList")
  public String memberList(HttpSession session, Model model)
  {
    return service.memberList(session, model);
  }

  // 관리자 추가
  @GetMapping("/addMember")
  public String addMember(HttpSession session)
  {
    return service.addMember(session);
  }

  // 관리자 추가 완료
  @PostMapping("/addMemberOk")
  public String addMemberOk(HttpSession session,
    AdminMemberDto mdto)
  {
    return service.addMemberOk(session,mdto);
  }

  // 관리자 삭제
  @GetMapping("/delMember")
  public String delMember(HttpSession session,
    @RequestParam("id") int id)
  {
    return service.delMember(session,id);
  }

  // 관리자 수정
  @GetMapping("/upMember")
  public String upMember(HttpSession session,
    @RequestParam("id") int id,
    Model model)
  {
    return service.upMember(session,id,model);
  }

  // 관리자 수정 완료
  @PostMapping("/upMemberOk")
  public String upMemberOk(HttpSession session,
    AdminMemberDto mdto)
  {
    return service.upMemberOk(session,mdto);
  }

  // 아이디 유무 체크
  @GetMapping("/useridOk")
  public @ResponseBody String useridOk(@RequestParam("userid") String userid)
  {
    return service.useridOk(userid);
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

  @GetMapping("/jumunOk")
  public String juumunOk(HttpSession session,
  @RequestParam("pcode") String pcode,
  @RequestParam("su") int su,
  @RequestParam("dae") String dae,
  @RequestParam(defaultValue = "1") int page,
  @RequestParam(defaultValue = "10") int rec)
  {
      return service.jumunOk(session,pcode,su,dae,page,rec);
  }

  @GetMapping("/jumunList")
  public @ResponseBody ArrayList<HashMap> jumunList(@RequestParam("dae") int dae)
  {
      return service.jumunList(dae);
  }
  
  @GetMapping("/stockView")
  public String stockView(HttpServletRequest request,
  Model model,
  @RequestParam(defaultValue = "1")int page,
  @RequestParam(defaultValue = "10")int rec)
  {
    return service.stockView(request,model,page,rec);
  }

}
