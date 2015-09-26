package com.engine.rendering;

import com.engine.physics.Vector2f;

public class TileEntity {
	
	private Vector2f pos;
	private byte mem;
	private byte flag;
	private byte type;
	
	public TileEntity(Vector2f pos, byte memory, byte flag, byte type) {
		setPos(pos);
		setMem(memory);
		setFlag(flag);
		setType(type);
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	public byte getMem() {
		return mem;
	}

	public void setMem(byte mem) {
		this.mem = mem;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
}
