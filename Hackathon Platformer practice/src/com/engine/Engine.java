package com.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.engine.input.Input;
import com.engine.physics.Entity;
import com.engine.physics.Physics;
import com.engine.physics.Vector2f;
import com.engine.rendering.Render;


public class Engine {

	private static ArrayList<BufferedImage> images;
	private static ArrayList<BufferedImage> collision;	
	private static int FPS;
	
	private static int[] TileMap;
	
	public static int score;
	
	public static int getFPS() {
		return FPS;
	}

	public static void setFPS(int fPS) {
		FPS = fPS;
	}

	private static final double FRAMECAP = 50;
	
	public static void varInit() {
		score = 0;
		FPS = 15;
		images =  new ArrayList<BufferedImage>();
		collision =  new ArrayList<BufferedImage>();
		TileMap = new int[256];
	}
	
	public static void Engineinit() {
		Physics.init();
		if(Physics.isInitSuccsess()) { System.out.println("Initialized Physics"); } else {System.err.println("Unable to Initialize Physics");}
		Render.init();
		if(Render.isInitSuccess()) { System.out.println("Initialized Rendering"); } else {System.err.println("Unable to Initialize Rendering");}
		Input.init();
		if(Input.isInitSuccess()) { System.out.println("Initialized Input"); } else {System.err.println("Unable to Initialize Input");}

		
		
		
	}
	
	public static void loadImage(String File) {
		 String TextureFile = "res\\" + File + "\\texture.png";
		 String CollisionFile = "res\\" + File + "\\collision.png";
		try { 
			BufferedImage img = ImageIO.read(new File(TextureFile));
			images.add(img);
		} catch (IOException e) {
			System.err.println("Error Loading texture file: " + File);
			File f = new File(TextureFile);
			System.err.println("Searched for file at: " + f.getAbsolutePath());
		}
		
		try { 
			BufferedImage img = ImageIO.read(new File(CollisionFile));
			collision.add(img);
		} catch (IOException e) {
			System.err.println("Error Loading collision file: " + File);
			File f = new File(CollisionFile);
			System.err.println("Searched for file at: " + f.getAbsolutePath());
		}
	}
	
	public static BufferedImage getImage(int id) {
		return images.get(id);
	}
	
	public static BufferedImage getCollision(int id) {
		try {
		return collision.get(id);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println(id);
			return null;
		}
	}
	
	public static void run() {
		

		
		double frameTime = 1 / FRAMECAP;
		int frame = 1;
		long frameCounter = 1;
		
		long lastTime = System.nanoTime();
		double waitTime = 1;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(!Display.isCloseRequested()) {
			
//			if(Runtime.getRuntime().freeMemory() < 50000000) {
//				System.err.println("Memory too low");
//				System.exit(1);
//			}
			
			boolean render = false;
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			
			lastTime = startTime;
			waitTime += (double) passedTime / 1000000000l;
			frameCounter += passedTime;
			//System.out.println(frame);
			while(waitTime > frameTime) {
				render = true;	
				waitTime -= frameTime;
				
				if(frameCounter > 1000000000l) {
					//System.out.println("FPS: " + frame);
					FPS = frame;
					float total = Runtime.getRuntime().totalMemory();
					float used = total -  Runtime.getRuntime().freeMemory();;
					//System.out.println("Memory Usage: " + used/total * 100 + "%");
					frame = 0;
					frameCounter = 0;
				}
				
				
				Input.update();
				Physics.updateChar();
				Physics.updateEntities();
				Physics.EntityCollision();
			}
			
			if(render) {
			Render.update(Physics.getCamera());
			frame++;
			} else {
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		Display.destroy();
		
	}
	
	public static void setDisplaySize(int width, int height, String title) {
		Render.setDisplaySize(new Vector2f(width, height));
		Render.setDisplayTitle(title);
	}

	public static int[] getTileMap() {
		return TileMap;
	}

	public static void setTileMap(int[] tileMap) {
		TileMap = tileMap;
	}
	
	public static int getMapVal(int id) {
		return TileMap[id];
	}
	
	
		

	}


