package com.engine.physics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;



import com.engine.Engine;
import com.engine.input.Input;
import com.engine.rendering.Render;
import com.kevin.tools.Logger;


public class Physics {
	
	private static Vector2f camera;
	private static final float Speed = 3f;
	private static int EntityCount;
	private static int charaID;
	

	private static ArrayList<Entity> characters;
	private static byte[] collision;
	
	private static boolean initSuccsess;
	private static boolean update;
	
	private static final Vector2f EC_right = new Vector2f(16,24);
	private static final Vector2f EC_bot_mid = new Vector2f(13,31);
	private static final Vector2f EC_left = new Vector2f(10,24);
	private static final Vector2f EC_top_mid = new Vector2f(13,16);
	
	private static ArrayList<Integer> allEntityIDs;
	
	public static void init() {
		
		allEntityIDs = new ArrayList<Integer>();
//		allEntityIDs.add();
		allEntityIDs.add(80);
		
		int MapSize = Render.getMapsize();
		int TileSize = Render.getTilesize();
		
		characters = new ArrayList<Entity>();
		collision = new byte[MapSize * TileSize * MapSize * TileSize * 3];
		EntityCount = -1;
		
		BufferedImage collisionMap = new BufferedImage(MapSize * TileSize,MapSize * TileSize,BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D drawer = collisionMap.createGraphics();
		
		byte[] parts = ((DataBufferByte)(Engine.getImage(0).getRaster().getDataBuffer())).getData();
		
	
		
		for(int x=0;x<MapSize;x++) {
			for(int y=0;y<MapSize;y++) {
				drawer.drawImage(getCollisionImage((int)parts[(y * MapSize + x) * 3 + 2]), x * TileSize, y * TileSize, null);
			}
		}
		
		
		 collision =  ((DataBufferByte)(collisionMap.getRaster().getDataBuffer())).getData();
		
		//System.out.println(collision[10] + "," + collision[11] + "," + collision[12]);
		
		
		initSuccsess = true;
	}
	
	public static void updateChar() {
		Entity chara = characters.get(charaID);
		
		
		update = false;
		
		if(Input.getKey(Input.KEY_W) && chara.isOnGround()) {
//			chara.getPos().setY(chara.getPos().getY() - Speed);
			chara.setVel(new Vector2f(0,-100));
			chara.setPos(new Vector2f(chara.getPos().getX(),chara.getPos().getY() - 5));
			update = true;
		}
//		if(Input.getKey(Input.KEY_S)) {
//			chara.getPos().setY(chara.getPos().getY() + Speed);
//			update = true;
//		}
		if(Input.getKey(Input.KEY_A)) {
			chara.setVel(new Vector2f(chara.getVel().getX() - Speed, chara.getVel().getY()));
			//chara.getVel().setX(chara.getVel().getX() - Speed);
			update = true;
		}
		if(Input.getKey(Input.KEY_D)) {
			chara.setVel(new Vector2f(chara.getVel().getX() + Speed, chara.getVel().getY()));
			update = true;
		}
		
		camera = Vector2f.divide(chara.getPos(),Render.getTilesize());
		//camera.multiply(Render.getTilesize());
		
	}
	
	public static BufferedImage getCollisionImage(int entityId) {
		switch(entityId) {
		case 40: return Engine.getCollision(1);
		case 1:  return Engine.getCollision(2);
		case 20: return Engine.getCollision(3);
		case -1: return Engine.getCollision(4);
		case 80: return Engine.getCollision(5);
		case 90: return Engine.getCollision(6);
		case 4: return Engine.getCollision(7);
		default: return Engine.getCollision(2);
		}
	}
	
	public static void updateEntities() {
		for(Entity e:characters) {
			
			e.setOnGround(true);
			e.setBelowCeiling(false);
			e.setOnRight(false);
			e.setOnLeft(false);
			
			int BotCenterX = (int) (e.getPos().getX() + EC_bot_mid.getX());
			int BotCenterY = (int) (e.getPos().getY() + EC_bot_mid.getY());			
			int TopCenterX = (int) (e.getPos().getX() + EC_top_mid.getX());
			int TopCenterY = (int) (e.getPos().getY() + EC_top_mid.getY());	
			int RightX = (int) (e.getPos().getX() + EC_right.getX());
			int RightY = (int) (e.getPos().getY() + EC_right.getY());	
			int LeftX = (int) (e.getPos().getX() + EC_left.getX());
			int LeftY = (int) (e.getPos().getY() + EC_left.getY());
			
			
			int bot_mid_index = (BotCenterY * Render.getMapsize() * Render.getTilesize() + BotCenterX) * 3;
			int DistanceFromGround = 0;
			while(collision[bot_mid_index] == -1) {
				DistanceFromGround++;
				BotCenterY++;
				bot_mid_index = (BotCenterY * Render.getMapsize() * Render.getTilesize() + BotCenterX) * 3;
			}

			if(DistanceFromGround > 1) {
				e.setOnGround(false);
			}
			
			
			int top_mid_index = (TopCenterY * Render.getMapsize() * Render.getTilesize() + TopCenterX) * 3;
			int DistanceFromCeiling = 0;
			while(collision[top_mid_index] == -1) {
				DistanceFromCeiling++;
				BotCenterY--;
				top_mid_index = (BotCenterY * Render.getMapsize() * Render.getTilesize() + BotCenterX) * 3;
			}

			if(DistanceFromCeiling > 1) {
				e.setBelowCeiling(true);
			}

			int right_index = (RightY * Render.getMapsize() * Render.getTilesize() + RightX) * 3;
			int DistanceFromRight = 0;
			while(collision[right_index] == -1) {
				DistanceFromRight++;
				RightX++;
				right_index = (RightY * Render.getMapsize() * Render.getTilesize() + RightX) * 3;
			}

			if(DistanceFromRight > 1) {
				e.setOnRight(true);
			}		
			
			int left_index = (LeftY * Render.getMapsize() * Render.getTilesize() + LeftX) * 3;
			int DistanceFromLeft = 0;
			while(collision[left_index] == -1) {
				DistanceFromLeft++;
				LeftX--;
				 left_index = (LeftY * Render.getMapsize() * Render.getTilesize() + LeftX) * 3;
			}

			if(DistanceFromLeft > 1) {
				e.setOnLeft(true);
			}		
			
			if(!e.isOnLeft()) {
				e.setVel(new Vector2f(1f,e.getVel().getY()));
			}			
			
			if(!e.isOnRight()) {
				e.setVel(new Vector2f(-1f,e.getVel().getY()));
			}
			
			if(e.isOnGround()) {
				e.setAcc(new Vector2f(-2 * e.getVel().getX()/2,0));
				e.setVel(new Vector2f(e.getVel().getX(),0));
			} else {
				if(e.isHasGravity()) {
				e.setAcc(new Vector2f(-2 * e.getVel().getX()/2,25));
				} else {
					e.setAcc(new Vector2f(-2 * e.getVel().getX()/2,0));					
				}
				//e.getPos().print();
			}
			
			if(!e.isBelowCeiling()) {
				e.setVel(new Vector2f(e.getVel().getX(),0));
			
			}
			e.setVel(new Vector2f((e.getAcc().getX() * (1/(float)Engine.getFPS())) + e.getVel().getX(),
					(e.getAcc().getY() * (1/(float)Engine.getFPS())) + e.getVel().getY() ));
			
			if(Math.abs(e.getAcc().getX()) <= .5f && e.getAcc().getX() != 0) {
				e.setAcc(new Vector2f(0,e.getAcc().getY()));
				e.setVel(new Vector2f(0,e.getVel().getY()));
			}
			
			if(e.getVel().getY() >= 50) {
				e.setVel(new Vector2f(e.getVel().getX(),75));
			}
			
			e.setPos(new Vector2f((e.getVel().getX() * (1/(float)Engine.getFPS())) + e.getPos().getX(),
					(e.getVel().getY() * (1/(float)Engine.getFPS())) + e.getPos().getY() ));
			
//			e.getAcc().print("Acc:");			
//			e.getVel().print("Vel:");
//			e.getPos().print("Pos:");
		}
	}
	
	public static void EntityCollision() {
		Entity player = characters.get(charaID);

		for(int i=0;i<characters.size();i++) {
			
			
			Entity e = characters.get(i);
			if(e == player) {
				continue;
			} else {
				if(Math.sqrt(Math.pow(e.getPos().getX() - player.getPos().getX(), 2) + 
						Math.pow(e.getPos().getY() - player.getPos().getY(), 2)) < 15) {
					
					String[] action = e.getOnHit().split(":");
					//switch(action[0]) 
					System.out.println(action[0]);
					switch(action[0]) {
					case "Collect":
					characters.remove(i);
					if(e.getIndex() < charaID) {
						charaID--;
					}
					Engine.score = Engine.score + Integer.valueOf(action[1]);
					break;
					
					}
				}
			}
		}
		
	}
	
	public static void drawEntities(Graphics2D g) {
		for(Entity e:characters) {
			if(!e.isOnScreen()) {continue;};
			
			//(camera.getX()-screenTileX/2) * Render.getTilesize() Top left corner
			
			int drawX = (int) ((e.getPos().getX() - (camera.getX()-Render.getScreentilex()/2) * Render.getTilesize()) *
					(Render.getDisplaySize().getX() / (Render.getScreentilex() * Render.getTilesize())));
			
			
			int drawY = (int) ((e.getPos().getY() - (camera.getY()-Render.getScreentiley()/2) * Render.getTilesize()) *
					(Render.getDisplaySize().getY() / (Render.getScreentiley() * Render.getTilesize())));
			
			BufferedImage image = Render.getImage(e.getEntityID());
			
			image = Render.resize(image,(int)Render.getDisplaySize().getX()/Render.getScreentilex(), 
						(int) (Render.getDisplaySize().getY()/Render.getScreentiley()));
			
			g.drawImage(image, drawX, drawY, null);
			
			
//			Logger.cord(drawX, drawY);
//			camera.print();
			
		}
	}
	
	public static boolean hasEntityID(int id) {
		boolean ans = false;
		
		for(Integer i:allEntityIDs) {
			if(i.equals(new Integer(id))) {
				ans = true;
				break;
			}
		}
		
		return ans;
	}
	

	public static Vector2f getCamera() {
		return camera;
	}

	public static void setCamera(Vector2f camera) {
		Physics.camera = camera;
		camera.print();
		Physics.camera = new Vector2f(58,36);
	} 

	public static boolean isInitSuccsess() {
		return initSuccsess;
	}
	
	public static int addEntity(Entity e) {
		characters.add(e);
		return EntityCount++;
	}

	public static boolean isUpdate() {
		return update;
	}

	public static void changeEntity(Entity newEntity, int id) { 
		characters.set(id, newEntity);
	}
	
	public static Entity getEntity(int id) {
		return characters.get(id);
	}
	
	public static int getCharaID() {
		return charaID;
	}

	public static void setCharaID(int charaID) {
		Physics.charaID = charaID;
	}

	public static int getEntityCount() {
		return EntityCount;
	}

	public static ArrayList<Integer> getAllEntityIDs() {
		return allEntityIDs;
	}

	public static void setAllEntityIDs(ArrayList<Integer> allEntityIDs) {
		Physics.allEntityIDs = allEntityIDs;
	}


	
}
