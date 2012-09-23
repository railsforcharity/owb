package com.owb.playhelp.client.helper;

public class MouseClick {
	private int x1,y1;
	private int x2,y2;
	private int button; //0=left, 1=middle, 2=right
	
	public MouseClick(int x, int y){
		this.x1 = x1;
		this.y1 = y1;
		this.button = 0;
	}
	public MouseClick(int x, int y, int button){
		this(x,y);
		this.button = button;
	}
	public MouseClick(int x1, int y1, int x2, int y2){
		this(x1,y1);
		this.x2 = x2;
		this.y2 = y2;
	}
	public MouseClick(int x1, int y1, int x2, int y2, int button){
		this(x1,y1,x2,y2);
		this.button = button;
	}
	public int getX1(){
		return this.x1;
	}
	public int getY1(){
		return this.y1;
	}
	public int getX2(){
		return this.x2;
	}
	public int getY2(){
		return this.y2;
	}
	public int getButton(){
		return this.button;
	}
	
}
