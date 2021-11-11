package com.liujie.crm.utils;

import sun.security.provider.MD4;
import sun.security.provider.MD5;

import java.util.UUID;

public class UUIDUtil {
	
	public static String getUUID(){


		return UUID.randomUUID().toString().replaceAll("-","");
		
	}
	
}
