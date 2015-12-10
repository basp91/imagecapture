package com.doralba.core;

import java.io.IOException;
import java.util.UUID;

import com.doralba.ui.MainWindow;
import com.doralba.utils.DAO;

/**
 * Hello world!
 *
 */
public class App 
{
	public String[] args = {};	

	public static void main( String[] args )
	{
		App obj = new App();
		System.out.println("Unique ID : " + obj.generateUniqueKey());
		try {
			new DAO();
		} catch (IOException e) {			
			e.printStackTrace();
		}

		MainWindow.main(args);
	}

	public String generateUniqueKey(){

		String id = UUID.randomUUID().toString();
		return id;

	}
}
