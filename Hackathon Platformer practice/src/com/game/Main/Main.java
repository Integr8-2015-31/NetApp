package com.game.Main;



import com.engine.Engine;

public class Main {
	public static void main(String[] args) {
		
		Engine.varInit();
		Engine.loadImage("map");	
		Engine.loadImage("B0");
		Engine.loadImage("B1");
		Engine.loadImage("B2");
		Engine.loadImage("B255");
		Engine.loadImage("B254");
		Engine.loadImage("B253");
		Engine.loadImage("B3");

		Engine.setDisplaySize(1000,800, "2D Platform");
		Engine.Engineinit();
		
		Engine.run();
		//1000,800


		
	}
}
