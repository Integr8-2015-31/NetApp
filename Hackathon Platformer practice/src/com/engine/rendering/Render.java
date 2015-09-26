package com.engine.rendering;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RasterFormatException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.engine.Engine;
import com.engine.physics.Entity;
import com.engine.physics.EntityType;
import com.engine.physics.Physics;
import com.engine.physics.Vector2f;
import com.kevin.tools.Logger;

import static org.lwjgl.opengl.GL11.*;

public class Render {
	
	private static boolean initSuccess;

	private static byte[] parts;
	private static final int TileSize = 32;
	private static final int MapSize = 256;
	private static TileEntity[][] entities;

	private static Vector2f DisplaySize;
	private static String DisplayTitle;
	
	private static BufferedImage Map;
	private static BufferedImage CroppedMap;
	
	private static int TextureLocation;
	
	private static final int screenTileX = 9;
	private static final int screenTileY = 9;
	
	
	// NOTE: for BGR val >= 128, value is (val u want) - 256
	
	public static TileEntity[][] mapToEntityArray(int map) {
		
		TileEntity[][] Map = new TileEntity[MapSize][MapSize];
		entities =  Map;
		
		parts = ((DataBufferByte)(Engine.getImage(map).getRaster().getDataBuffer())).getData();
		
		
		for(int x=0;x<MapSize;x++) {
			for(int y=0;y<MapSize;y++){
				int index = 3 * (y * MapSize + x);
					try {
						TileEntity e = new TileEntity(new Vector2f(x,y), parts[index], parts[index + 1], parts[index + 2]);
					Map[x][y] = e;
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
						System.err.println( "\n" + "At (" + x + "," + y +")");
					}
					
			}
		}
		return Map;
	}
	
	public static BufferedImage EntityArrayToImage(TileEntity[][] entities) {
		
		BufferedImage screen = new BufferedImage(MapSize * TileSize, MapSize * TileSize, BufferedImage.TYPE_3BYTE_BGR);
		Graphics drawer = screen.createGraphics();
		
		drawer.setColor(Color.LIGHT_GRAY);
		drawer.fillRect(0, 0, MapSize * TileSize, MapSize * TileSize);
		
		for(int x=0;x<MapSize;x++) {
			for(int y=0;y<MapSize;y++) {
//				drawer.drawImage(getImage(entities[x][y].getType()), x * TileSize, y * TileSize, null);
				if(entities[x][y].getType() == -1) {
					Physics.setCamera(new Vector2f(x,y));
					Physics.setCharaID(Physics.addEntity(new Entity(new Vector2f(x * TileSize,y * TileSize),-1,Physics.getEntityCount())) + 1);
					Physics.getEntity(Physics.getCharaID()).setHasGravity(true);
					Physics.getEntity(Physics.getCharaID()).setOnHit("Nothing");
				} else if(Physics.hasEntityID(entities[x][y].getType())){ 
					Logger.Log("Found a non-Player entity: " + entities[x][y].getType()  + ", " + Physics.getEntityCount());
					Physics.addEntity(new Entity(new Vector2f(x * TileSize,y * TileSize),entities[x][y].getType(), Physics.getEntityCount()));
					Physics.getEntity(Physics.getEntityCount()).setType(EntityType.getEntityType(entities[x][y].getType()));
					Physics.getEntity(Physics.getEntityCount()).setOnHit(EntityType.getCollisionString(Physics.getEntity(Physics.getEntityCount()).getType()));
				} else {
					drawer.drawImage(getImage(entities[x][y].getType()), x * TileSize, y * TileSize, null);
				}
			}
		}
		drawer.dispose();
		return screen;
	}
	
	
	public static BufferedImage getImage(int entityId) {
		
		
		
		switch(entityId) {
		case 40: return Engine.getImage(1);
		case 1: return Engine.getImage(2);
		case 20: return Engine.getImage(3);
		case -1: return Engine.getImage(4);
		case 80: return Engine.getImage(5);
		case 90: return Engine.getImage(6);
		case 4: return Engine.getImage(7);
		default: return Engine.getImage(2);
		}
	}
	
	public static void update(Vector2f camera) {
			
		
			float Xsize = Render.getTilesize() * screenTileX;
			float Ysize = Render.getTilesize() * screenTileY;
	
			CroppedMap = Render.CullImage(Map, (camera.getX()-screenTileX/2) * Render.getTilesize(),
					(camera.getY()-screenTileY/2) * Render.getTilesize(),(int) Xsize, (int) Ysize);
			
//			CroppedMap = Render.CullImage(Map, (camera.getX() - ()) * TileSize,
//							 camera.getY() * TileSize, 
//							 Render.getTilesize() * screenTileX, Render.getTilesize() * screenTileY);
			
			Graphics2D g = CroppedMap.createGraphics();
			
			g.setFont(new Font("Jokerman", Font.PLAIN, 35));
			g.setColor(Color.BLUE);
			g.drawString("FPS: " + String.valueOf(Engine.getFPS()), 850, 60);
			g.drawString("Score: " + Engine.score, 10, 60);
			
			//BufferedImage light = new BufferedImage(CroppedMap.getWidth(), CroppedMap.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			//byte[] lighting = ((DataBufferByte)(CroppedMap.getRaster().getDataBuffer())).getData();
			
//			for(int x=0;x<CroppedMap.getWidth();x++) {
//				for(int y=0;y<CroppedMap.getHeight();y++){
//					int index = (y * CroppedMap.getWidth() + x) * 4;
//					lighting[index] = 0;
//					lighting[index + 1] = (byte) .5;
//					lighting[index + 2] = 0;
//					lighting[index + 3] = 0;
//				}
//			}
			
			
			
			//Stroke oldStroke = g.getStroke();
//			g.setStroke(new BasicStroke(20));
//			g.setColor(Color.BLACK);
//			g.drawRect(0, 0, CroppedMap.getWidth(), CroppedMap.getHeight());
//			g.setStroke(oldStroke);
			
			Physics.drawEntities(g);
			
			//applyMask(CroppedMap, light);
			
			//Render.AlphaBlend(CroppedMap, (byte)1,(byte) (byte).5,(byte) .5,(byte) .5);
			
			ImageToTexture(CroppedMap);
			
			int transCorrectX = screenTileX * TileSize / 2;
			int transCorrectY = 215;//screenTileY * TileSize / 2;
			
		       glClear(GL_COLOR_BUFFER_BIT);
		       glPushMatrix();
		       glTranslatef(0, transCorrectX, 0);
		         glBindTexture(GL_TEXTURE_2D, TextureLocation);
		         glBegin(GL_QUADS);
		         {
		            glTexCoord2f(0, 0);
		            glVertex2f(0, 0);
		            
		            glTexCoord2f(1, 0);
		            glVertex2f(DisplaySize.getX() - transCorrectY,0);
		            
		            glTexCoord2f(1, 1);
		            glVertex2f(DisplaySize.getX() - transCorrectY, DisplaySize.getY() - transCorrectX);
		            
		            glTexCoord2f(0, 1);
		            glVertex2f(0, DisplaySize.getY() - transCorrectX);
		         }
		         glEnd();
		         glPopMatrix();
		         
		         Display.update();
	}
	
	
	public static int getScreentilex() {
		return screenTileX;
	}

	public static int getScreentiley() {
		return screenTileY;
	}

	public static BufferedImage CullImage(BufferedImage img, float x, float y, int width, int height) {
		
		try {
		BufferedImage sub = img.getSubimage((int)x, (int)y, width, height);
		sub = resize(sub,(int)DisplaySize.getX(),(int)DisplaySize.getY());
		
		return sub;
		
		} catch (RasterFormatException e) {
			return new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		}
	
		
//		return resize(img.getSubimage((int)(x - 1/2 * width),  
//				//(int) (y - 1/2 * height), width, height),120,120);
//				(int) (y - 1/2 * height), width, height),(int)DisplaySize.getX(),(int)DisplaySize.getY());
		
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp,0,0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	public static BufferedImage translate(BufferedImage img, int x, int y) {
		BufferedImage Timg = new BufferedImage(img.getWidth() + x, img.getHeight() + y, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = Timg.createGraphics();
		g.drawImage(img, x, y, null);
		return Timg;
	}
	
	
	public static BufferedImage MaptoImg(int mapId) {
		TileEntity[][] map = mapToEntityArray(mapId);
		return EntityArrayToImage(map);
	}
	
	public static void init() {
		
		initSuccess = true;
		
		Map = Render.MaptoImg(0);

		MainDisplay.init((int)DisplaySize.getX(),(int) DisplaySize.getY(), DisplayTitle);
		
	    glMatrixMode(GL_PROJECTION);
	    glOrtho(0, (int)DisplaySize.getX(),(int) DisplaySize.getY(), 0, -1, 1); //2D projection matrix
	    glMatrixMode(GL_MODELVIEW);
	    glClearColor(0, 1, 0, 0);
	    //screen = Map.createGraphics();
	    glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        TextureLocation = glGenTextures();

	}
	
	public static void ImageToTexture(BufferedImage img) {
		
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4);
		
	     for(int y = 0; y < img.getHeight(); y++){
	            for(int x = 0; x < img.getWidth(); x++){
	                int pixel = pixels[y * img.getWidth() + x];
	                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
	                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
	                buffer.put((byte) (pixel & 0xFF));               // Blue component
	                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
	            }
	        }
	     
	     buffer.flip();
		
	     
	   
	    GL11.glBindTexture(GL_TEXTURE_2D, TextureLocation);
	    
	    //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        //Return the texture ID so we can bind it later again

	}
	 

	public static int getTilesize() {
		return TileSize;
	}

	public static int getMapsize() {
		return MapSize;
	}

	public static BufferedImage getMap() {
		return Map;
	}

	public static void setMap(BufferedImage map) {
		Map = map;
	}

	public static TileEntity[][] getEntities() {
		return entities;
	}

	public static void setEntities(TileEntity[][] entities) {
		Render.entities = entities;
	}

	public static Vector2f getDisplaySize() {
		return DisplaySize;
	}

	public static void setDisplaySize(Vector2f displaySize) {
		DisplaySize = displaySize;
	}

	public static String getDisplayTitle() {
		return DisplayTitle;
	}

	public static void setDisplayTitle(String displayTitle) {
		DisplayTitle = displayTitle;
	}

	public static boolean isInitSuccess() {
		return initSuccess;
	}

	
}
