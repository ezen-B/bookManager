package com.example.demo;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
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

  public String couponList(Model model,HttpSession session)
  {
    ArrayList<CouponDto> clist = mapper.couponList();

    model.addAttribute("clist", clist);
    

    // 대분류값 
    ArrayList<DaeDto> dlist=mapper.getDae();
    model.addAttribute("dlist", dlist);

    return "/couponList";
  }

  public String couponOk(CouponDto cdto, HttpSession session) 
  {
    // level별 구분
      int level=0;
      if(session.getAttribute("level") != null)
    {
      level=Integer.parseInt(session.getAttribute("level").toString());
    }

    if(level==0)  // 임시 최고관리자 == 100 / 중간 == 50
    {
      LocalDate today=LocalDate.now();

      String month=String.format("%02d", today.getMonthValue());
      String day=String.format("%02d", today.getMonthValue());
      String code=today.getYear()+month+day;

      int num=mapper.getCode(code);
      code=code+String.format("%03d",num);

      cdto.setCode(code);

      if(cdto.getGigan().equals(""))
        cdto.setGigan(null);

      mapper.couponOk(cdto);

      return "redirect:/couponList";
    }
      else
    {
      return "redirect:/main";
    }
  }

  public String delCoupon(int id) 
  {
      mapper.delCoupon(id);
      
    return "redirect:/couponList";
  }

  public String upCoupon(CouponDto cdto)
  {
      if(cdto.getGigan().equals(""))
      cdto.setGigan(null);

    mapper.upCoupon();

    return "redirect:/couponList";
  }

  public String jumunOk(HttpSession session, String pcode, int su, String dae, int page, int rec) 
  {
    // jumun테이블에 저장
    String userid=session.getAttribute("userid").toString();
    mapper.jumunOk(userid,pcode,su);

    return "redirect:/stockView?dae="+dae+"&page="+page+"&rec="+rec;
  }

  public ArrayList<HashMap> jumunList(String dae) 
  {
    //String daeCode=String.format("%02d",dae);
    ArrayList<HashMap> mapAll=mapper.jumunList(dae);
    return mapAll;
  }

  public String stockView(HttpServletRequest request, Model model, int page, int rec) 
  {
    int index=(page-1)*rec;  // 페이지 처리 => 1페이지당 레코드 10개 , 20개 , 30개 , 50개
    
    String dae="A1";
    if(request.getParameter("dae") != null) {
      dae = request.getParameter("dae");
    }

    model.addAttribute("dae", dae);

    String daeName = "";
    String imsi = dae;
    switch (dae) {
      case "A1": daeName = "소설/문학"; break;
      case "B1": daeName = "비소설/교양"; break;
      case "C1": daeName = "경제/경영"; break;
      case "D1": daeName = "과학/기술"; break;
      default: daeName = "기타";
    }
    model.addAttribute("daeName", daeName);

    //String imsi=String.format("%02d", dae);

    ArrayList<ProductJumunDto> plist=mapper.stockView(imsi,index,rec);
    model.addAttribute("plist", plist);

    for(int i=0;i<plist.size();i++)
    {
      System.out.println(plist.get(i).getState());
    }

    // 총페이지를 구해서 전달하기
    int chong=mapper.getChong(imsi,rec);
    model.addAttribute("chong", chong);
    model.addAttribute("page", page);
    model.addAttribute("rec", rec);

    return "/stockView";
  }

  public String jumunCheck(String dae, Model model) 
  {
    // String pcode="p"+String.format("%02d", dae);
    
    ArrayList<ProductJumunDto> plist=mapper.jumunCheck(dae);
    model.addAttribute("plist",plist);
    
    return "/jumunCheck";
  }

  public String receiptOk(JumunDto jdto, HttpSession session) 
  {
    String userid=session.getAttribute("userid").toString();
    jdto.setAuditMan(userid);
    
    mapper.receiptOk(jdto);
    
    return "redirect:/jumunCheck";
  }
}
