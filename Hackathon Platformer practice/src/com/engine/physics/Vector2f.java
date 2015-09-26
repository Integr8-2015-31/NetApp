package com.engine.physics;

import java.awt.Dimension;


public class Vector2f {

	private float x;
	private float y;
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2f(float x, float y) {
		
		this.x = x;
		this.y = y;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float dotproduct(Vector2f v) {
		return x * v.getX() + y * v.getY();
	}
	
	public Vector2f normalize() {
		float l = length();
		
		x /= l;
		y /= l;
		
		return this;
	}
	
	public static Vector2f add(Vector2f v1,Vector2f v2) {
		return new Vector2f(v2.getX() + v1.getX(), v2.getY() + v1.getY());
	}
	
	public static Vector2f add(Vector2f v,float r) {
		return new Vector2f(v.getX() + r, v.getY() + r);
	}
	
	public static Vector2f subtratct(Vector2f v1 ,Vector2f v2) {
		return new Vector2f(v1.getX() - v2.getX(), v1.getX() - v2.getY());
	}
	
	public static Vector2f subtratct(Vector2f v, float r) {
		return new Vector2f(v.getX() - r, v.getY() - r);
	}
	
	public static Vector2f multiply(Vector2f v1, Vector2f v2) {
		return new Vector2f(v2.getX() * v1.getX(), v2.getX() * v1.getY());
	}
	
	public static Vector2f multiply(Vector2f v, float r) {
		return new Vector2f(v.getX() * r, v.getY() * r);
	}
	
	public static Vector2f divide(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.getX() / v2.getX(), v1.getY() / v2.getY());
	}
	
	public static Vector2f divide(Vector2f v, float r) {
		return new Vector2f(v.getX() / r, v.getY() / r);
	}
	
	
	
	
	public void add(Vector2f v) {
		x = x + v.getX();
		y = y + v.getY();
	}
	
	public void add(float r) {
		x = x + r;
		y = y + r;
	}
	
	public void subtratct(Vector2f v) {
		x = x - v.getX();
		y = y - v.getY();
	}
	
	public void subtratct(float r) {
		x = x - r;
		y = y - r;
	}
	
	public void multiply(Vector2f v) {
		x = x * v.getX();
		y = y * v.getY();
	}
	
	public void multiply(float r) {
		x = x * r;
		y = y * r;
	}
	
	public void divide(Vector2f v) {
		x = x / v.getX();
		y = y / v.getY();
	}
	
	public void divide(float r) {
		x = x / r;
		y = y / r;
	}
	
	public Vector2f rotate(float angle) {
		double rad = (float) Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f ((float)(x * cos - y * sin), (float)(x * sin + y * cos));
		
	}
	
	public String getString() {
		return "(" + x + "," + y + ")" ;
	}
	
	public void print(){
		System.out.println("(" + x + "," + y + ")" );
	}
	
	public Dimension VectorToDimension() {
		Dimension D =  new Dimension();
		D.setSize(y, y);
		return D;
	}
	public void print(String string) {
		System.out.println(string + " (" + x + "," + y + ")" );
		
	}
	@Override
	public String toString() {
		return "Vector2f [x=" + x + ", y=" + y + "]";
	}
	
	
}
