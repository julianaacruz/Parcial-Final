package model;

import processing.core.PApplet;

public class Jugador extends Objeto {

	private int vida;

	public Jugador(PApplet app, int posX, int posY) {
		super(app, posX, posY);
		vida = 1;
	}

	public int getVida() {
		return vida;
	}

	public void matar() {
		this.vida -= 1;
	}

	@Override
	public void pintar() {

		if (vida >= 1) {
			app.fill(0, 0, 255);
			app.ellipse(posX, posY, 30, 30);
		}
	}

	@Override
	public void mover() {
		if (app.keyCode == PApplet.RIGHT) {
			if (posX < 600) {
				app.smooth();
				posX += 5;

			} else {
				posX = 570;
			}
		}

		if (app.keyCode == PApplet.LEFT) {
			if (posX > 0) {

				app.smooth();
				posX -= 5;

			} else {
				posX = 30;
			}
		}

		if (app.keyCode == PApplet.UP) {
			if (posY > 0) {

				app.smooth();
				posY -= 5;

			} else {
				posY = 30;
			}
		}

		if (app.keyCode == PApplet.DOWN) {
			if (posY < 520) {

				app.smooth();
				posY += 5;

			} else {
				// ponerle para ganar :)
			}
		}
	}

}
