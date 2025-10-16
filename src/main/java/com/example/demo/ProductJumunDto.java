package com.example.demo;

import lombok.Data;

@Data
public class ProductJumunDto {
    private int id,su,jsu,pansu,state;
	private int pass,fail;
	private String pimg,title,pcode;
	private String writeday,reWriteday;

}
