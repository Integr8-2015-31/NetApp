package com.engine.physics;

public class Entity {

	private Vector2f pos, vel, acc;
	private float health, speed, attack;
	private final int EntityID;
	private boolean onScreen;
	private boolean onGround;
	private boolean belowCeiling;
	private boolean onRight;
	private boolean onLeft;
	private boolean hasGravity;
	private int index;
	private String onHit;
	private EntityType type;

	public Entity(Vector2f pos, int EntityID, int index) {
		this.index = index;
		setPos(pos);
		this.EntityID = EntityID;
		onScreen = true;
		onGround = true;
		belowCeiling = true;
		onRight = true;
		onLeft = true;
		hasGravity = false;
		setAcc(new Vector2f(0,0));
		setVel(new Vector2f(0,0));
		speed = 1;
	}
	
	public boolean isHasGravity() {
		return hasGravity;
	}

	public void setHasGravity(boolean hasGravity) {
		this.hasGravity = hasGravity;
	}

	public boolean isOnRight() {
		return onRight;
	}

	public void setOnRight(boolean onRight) {
		this.onRight = onRight;
	}

	public boolean isOnLeft() {
		return onLeft;
	}

	public void setOnLeft(boolean onLeft) {
		this.onLeft = onLeft;
	}

	public boolean isBelowCeiling() {
		return belowCeiling;
	}

	public void setBelowCeiling(boolean belowCeiling) {
		this.belowCeiling = belowCeiling;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	public Vector2f getPos() {
		return pos;
	}


	public void setPos(Vector2f pos) {
		this.pos = pos;
	}


	public Vector2f getVel() {
		return vel;
	}


	public void setVel(Vector2f vel) {
		this.vel = vel;
	}


	public Vector2f getAcc() {
		return acc;
	}


	public void setAcc(Vector2f acc) {
		this.acc = acc;
	}


	public float getHealth() {
		return health;
	}


	public void setHealth(float health) {
		this.health = health;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public float getAttack() {
		return attack;
	}


	public void setAttack(float attack) {
		this.attack = attack;
	}

	public int getEntityID() {
		return EntityID;
	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public String getOnHit() {
		return onHit;
	}

	public void setOnHit(String onHit) {
		this.onHit = onHit;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}


	
}
