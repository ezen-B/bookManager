package com.example.demo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {

  public AdminMemberDto loginOk(AdminMemberDto mdto);
  public ArrayList<AdminMemberDto> memberList();
  public void addMemberOk(AdminMemberDto mdto);
  public void delMember(int id);
  public AdminMemberDto getMember(int id);
  public void upMemberOk(AdminMemberDto mdto);
  public boolean useridOk(String userid);


	ArrayList<CouponDto> couponList();

    ArrayList<DaeDto> getDae();

    int getCode(String code);

    void couponOk(CouponDto cdto);

    void delCoupon(int id);

    void upCoupon();


}
