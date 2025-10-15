package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class ManagerService {
	@Autowired
	private ManagerMapper mapper;

  public String loginOk(AdminMemberDto mdto, HttpSession session) 
  {
    // 실행전 : 아이디 , 비밀번호

    mdto=mapper.loginOk(mdto);
    
    // 실행후 : 아이디 , 이름 , 레벨
    
    if(mdto != null)// 회원이 맞으면 => 세션변수 : 아이디 , 이름 , 레벨
    {
      session.setAttribute("userid", mdto.getUserid());
      session.setAttribute("name", mdto.getName());
      session.setAttribute("level", mdto.getLevel());
    }
      
    return "redirect:/main";
  }

  public String logout(HttpSession session)
  {
    session.invalidate();
    return "redirect:/main";
  }
}
