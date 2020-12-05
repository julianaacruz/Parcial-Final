package model;

import processing.core.PApplet;

public abstract class Objeto {
	
	
	protected int posX;
	protected int posY;
	protected PApplet app;

	public Objeto(PApplet app, int posX, int posY) {
		this.app=app;
		this.posX=posX;
		this.posY=posY;
	}
	
	
	 public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}


	public abstract void pintar();
	 
	 public abstract void mover();


}
