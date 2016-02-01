package com.tubager.utility;

import java.util.UUID;

public class Utility {
	
	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
