package model;

import processing.core.PApplet;

public class Carro extends Objeto {

	private int direccion;

	public Carro(PApplet app, int posX, int posY, int direccion) {
		super(app, posX, posY);

		this.direccion = direccion;
	}

	@Override
	public void pintar() {

		app.fill(0, 255, 0);
		app.rect(posX, posY, 30, 20);
	}

	@Override
	public void mover() {

		
		if (direccion<0) {
			if (posX > 0) {
				posX -= 5;
			} else if (posX <= 0) {
				direccion = 3;
			}
		} else {
			if (posX < 600) {
				posX += 5;

			} else if (posX >= 600) {
				direccion = -3;
			}
		}
		
		
	

	}

}
