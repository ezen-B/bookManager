package com.example.demo;

import lombok.Data;

@Data
public class AdminMemberDto {
  private int id,level;
  private String name,userid,pwd;
}
