package view;

import controller.BaseDeDatosPeludos;
import model.Logica;
import processing.core.PApplet;

public class Main extends PApplet{

	
	Logica log;
	public void settings() {
		size(600, 600);
	}
	
	public void setup() {
		log=new Logica(this);
		log.start();
	}
	
	public void draw() {
		background(255);
		log.pintar();
	}
	
	public void keyPressed() {
		log.eventosTeclado();
		log.volverAJugar();
		log.guardarArchivosDeJuego();
		log.mostrarDatosDeJuegos();
		log.ordenarParcialmente();
		log.ordenarNaturalmente();
	}
	
	public void mousePressed() {
	
	}
	public static void main(String[] args) {
		PApplet.main("view.Main");
	}

}
