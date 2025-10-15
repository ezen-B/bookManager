package com.example.demo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {

	ArrayList<CouponDto> couponList();

    ArrayList<DaeDto> getDae();

    int getCode(String code);

    void couponOk(CouponDto cdto);

    void delCoupon(int id);

    void upCoupon();

}
