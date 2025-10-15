package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Service
public class ManagerService {
	@Autowired
	private ManagerMapper mapper;

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

}
