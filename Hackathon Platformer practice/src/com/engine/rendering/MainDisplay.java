package com.engine.rendering;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class MainDisplay{

	public static void init(int width, int height, String title) {
//		setSize(width,height);
		try {
			Display.create();
			Display.setTitle(title);
			Display.setDisplayMode(new DisplayMode(width, height));
			
			} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(Display.isCreated()==false) {
			System.err.println("Unable to create Display");
		}
//		setTitle(title);
//		setLocationRelativeTo(null);
//		setResizable(false);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}

}
