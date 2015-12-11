package com.doralba.core;

public enum TypeEnum {
	INE(1, "INE"),
	PASAPORTE(2, "Pasaporte"), 
	LICENCIA(3, "Licencia de Conducir");

	private int val;
	private String name;
	
	TypeEnum(int val, String name){
		this.val = val;
		this.name = name;
	}
	
	public int getValue(){
		return val;
	}
	
	public String getName(){
		return name;
	}
}
