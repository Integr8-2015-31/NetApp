package com.engine.physics;

public enum EntityType {

	coin,player;
	
	public static String getCollisionString(EntityType type) {
		
		String data = "";
		
		switch(type) {
		case coin:
			data = "Collect:5";
			break;
		case player:
			data = "Nothing";
			break;
		default:
			break;
		}		
		return data;
	}
	
	public static EntityType getEntityType(int id) {
		
		EntityType t = null;
		
		switch(id) {
		case 80: t = coin; break;
		case 255: t = player; break;
		}
		
		return t;
	}
	
}
