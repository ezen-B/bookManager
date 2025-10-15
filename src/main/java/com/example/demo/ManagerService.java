package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Service
public class ManagerService {
  @Autowired
  private ManagerMapper mapper;

  // 로그인
  public String loginOk(AdminMemberDto mdto, HttpSession session) 
  {
    // 로그인이 성공하면 AdminMemberDto에 정보 담기
    mdto=mapper.loginOk(mdto);
    
    
    if(mdto != null) {  // 로그인 정보가 있는지 체크한 뒤,
      session.setAttribute("userid", mdto.getUserid());  // 아이디 담고,
      session.setAttribute("name", mdto.getName());      // 이름 담고,
      session.setAttribute("level", mdto.getLevel());    // 레벨 담기
    }
    return "redirect:/main";
  }

  // 로그아웃
  public String logout(HttpSession session)
  {
    session.invalidate();    // 세션을 강제종료하는 명령
    return "redirect:/main";
  }

  // 관리자 페이지
  public String memberList(HttpSession session, Model model)
  {
    int level=0;
    if(session.getAttribute("level") != null) {  // 레벨이 있는지 체크
      level=Integer.parseInt(session.getAttribute("level").toString()); // 레벨 값을 문자로 받아서 숫자로 변환
    }
    
    if(level >= 80) {  // 레벨이 80이상이면 권한 있음
      ArrayList<AdminMemberDto> mlist=mapper.memberList();  // AdminMemberDto에서 회원 목록을 조회
      model.addAttribute("mlist",mlist);  // 조회된 목록을 화면에 보여준다.
      return "/memberList";
    }
    else {
      session.invalidate();    // 권한 없으면 세션을 강제종료하기
      return "redirect:/main";
    }
  }

  // 관리자 추가
  public String addMember(HttpSession session)
  {
    int level=0;
    if(session.getAttribute("level") != null) {  // 레벨이 있는지 체크
      level=Integer.parseInt(session.getAttribute("level").toString()); // 레벨 값을 문자로 받아서 숫자로 변환
    }
    
    if(level >= 80) {  // 레벨이 80이상이면
      return "/addMember";
    }
    else {
      return "redirect:/main";
    }
  }

  // 관리자 수정
  public String addMemberOk(HttpSession session, AdminMemberDto mdto) 
  {
    int level=0;
    if(session.getAttribute("level") != null) {  // 레벨이 있는지 체크
      level=Integer.parseInt(session.getAttribute("level").toString()); // 레벨 값을 문자로 받아서 숫자로 변환
    }
    
    if(level >= 80) {  // 레벨이 80이상이면
      mapper.addMemberOk(mdto);  // AdminMemberDto에 정보 담아오기
      
      return "redirect:/memberList";
    }
    else {
      return "redirect:/main";
    }
  }

  // 관리자 삭제
  public String delMember(HttpSession session, int id) 
  {
    int level=0;
    if(session.getAttribute("level") != null) {  // 레벨이 있는지 체크
      level=Integer.parseInt(session.getAttribute("level").toString()); // 레벨 값을 문자로 받아서 숫자로 변환
    }
    
    if(level == 100) {  // 레벨이 100이면
      mapper.delMember(id);  // DB에서 아이디 삭제하기
      
      return "redirect:/memberList";
    }
    else {
      return "redirect:/main";
    } 
  }

  // 관리자 수정
  public String upMember(HttpSession session, int id, Model model)
  {
    int level=0;
    if(session.getAttribute("level") != null) {  // 레벨이 있는지 체크
      level=Integer.parseInt(session.getAttribute("level").toString()); // 레벨 값을 문자로 받아서 숫자로 변환
    }
    
    if(level == 100) {  // 레벨이 100이면
      AdminMemberDto mdto=mapper.getMember(id);  // AdminMemberDto에 id로 조회한 DB 담기
      model.addAttribute("mdto",mdto);
      return "/upMember";
    }
    else {
      return "redirect:/main";
    }
  }
  
  // 관리자 수정 완료
  public String upMemberOk(HttpSession session, AdminMemberDto mdto) 
  {
    int level=0;
    if(session.getAttribute("level") != null) {  // 레벨이 있는지 체크
      level=Integer.parseInt(session.getAttribute("level").toString()); // 레벨 값을 문자로 받아서 숫자로 변환
    }
    
    if(level == 100) {  // 레벨이 100이면
      mapper.upMemberOk(mdto);  // AdminMemberDto에 담은 데이터 수정
      return "redirect:/memberList";
    }
    else {
      return "redirect:/main";
    }
  }

  // 아이디 유무 체크
  public String useridOk(String userid) 
  {
    if(mapper.useridOk(userid)) {  // DB에 동일한 id가 있으면
      return "1";
    }
    else {
      return "0";
    }
  }
}
