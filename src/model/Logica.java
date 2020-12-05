package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import processing.core.PApplet;

public class Logica extends Thread {

	private PApplet app;
	private int pantalla;
	private File fileDatos;
	private File fileDataJuego;
	private String[] datosObjetos;
	private ArrayList<String> datosJuegoBrutos;
	private boolean choque;

	private Objeto personaje;
	private ArrayList<Objeto> carritos;
	private ArrayList<Partida> partidas;

	private int organizador = 0;
	private final long createdMillis;

	private DateTimeFormatter dtf;

	private int tiempoJuego;

	private boolean ganar = false;

	public Logica(PApplet app) {
		this.createdMillis = System.currentTimeMillis();
		this.app = app;
		pantalla = 0;
		choque = false;
		datosObjetos = new String[30];

		fileDatos = new File("./data/import/baseDeDatos.txt");// Carga el archivo
		fileDataJuego = new File("./data/dataCreada/dataDelJuego.txt");
		personaje = new Jugador(app, app.width / 2, 40);
		carritos = new ArrayList<Objeto>();
		partidas = new ArrayList<Partida>();
		datosJuegoBrutos = new ArrayList<String>();

		cargarInformacionObjetos();

	}

	public void cargarInformacionObjetos() {

		// Leemos el archivo de objetos. Use el FileReader porque pide de una vez una excepcion del sistema, espero que eso valga XD
		try {
			FileReader reader = new FileReader(fileDatos); // Se prepara para la lectura del archivo
			BufferedReader br = new BufferedReader(reader); // Se carga en el buffer para su manipulación
			String line = "";
			int posL1 = 0;
			while ((line = br.readLine()) != null) { // Se leen las lineas hasta el final del documento
				datosObjetos[posL1] = line;
				posL1++;
			}
			br.close(); // Se cierra el buffer

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

		}

		// Anadir los datos de datosUno a mi arreglo
		for (int i = 0; i < datosObjetos.length; i++) {
			String[] objeto = datosObjetos[i].split(","); // separar por comas

			String tipoDato = objeto[0];
			if (tipoDato.equals("carro")) {
				int direccion = Integer.parseInt(objeto[1]);

				if (organizador == 10) {
					organizador = 0;
				}
				if (i >= 0 && i < 5) {
					carritos.add(new Carro(app, 50 + (organizador * 60), 90, direccion));
				} else if (i >= 5 && i < 10) {
					carritos.add(new Carro(app, 50 + (organizador * 60), 160, direccion));
				} else if (i >= 10 && i < 15) {
					carritos.add(new Carro(app, 50 + (organizador * 55), 230, direccion));

				} else if (i >= 15 && i < 20) {
					carritos.add(new Carro(app, 50 + (organizador * 65), 300, direccion));

				} else if (i >= 20 && i < 25) {
					carritos.add(new Carro(app, 50 + (organizador * 58), 370, direccion));

				} else if (i >= 25 && i < 30) {
					carritos.add(new Carro(app, 50 + (organizador * 40), 440, direccion));

				}
				organizador += 1;
			}

		}

	}

	public void cargarInformacionDatosJuego() {
		// Leemos el archivo de objetos
		try {
			FileReader reader = new FileReader(fileDataJuego); // Se prepara para la lectura del archivo
			BufferedReader br = new BufferedReader(reader); // Se carga en el buffer para su manipulación
			String line = "";
			int posL1 = 0;
			while ((line = br.readLine()) != null) { // Se leen las lineas hasta el final del documento
				datosJuegoBrutos.add(new String(line));
				posL1++;
			}
			br.close(); // Se cierra el buffer

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

		}

		// Anadir los datos de datos del juego a mi arreglo
		for (int i = 0; i < datosJuegoBrutos.size(); i++) {
			String[] objeto = datosJuegoBrutos.get(i).split(","); // separar por comas
			String fechaYHora = objeto[0];
			int tiempoDeJuego = Integer.parseInt(objeto[1]);
			partidas.add(new Partida(fechaYHora, tiempoDeJuego));

		}
	}

	public void ordenarNaturalmente() {

		if (app.keyCode == 78) {
			Collections.sort(partidas);

			File file = new File("./data/dataCreada/ordenPartidasFormaNatural.txt");// Creación del archivo
			try {
				FileWriter fw = new FileWriter(file); // Lo cargamos para su escritura
				BufferedWriter bw = new BufferedWriter(fw); // Lo pasamos por buffer para su manipulación

				for (int i = 0; i < partidas.size(); i++) {
					bw.write(partidas.get(i).getFechaYHora() + "," + partidas.get(i).getTiempoDeJuego());
					bw.newLine();
				}

				bw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void ordenarParcialmente() {

		if (app.keyCode == 80) {
			Collections.sort(partidas, new CompareTiempoDeJuego());

			File file = new File("./data/dataCreada/ordenPorTiempoDeJuego.txt");// Creación del archivo
			try {
				FileWriter fw = new FileWriter(file); // Lo cargamos para su escritura
				BufferedWriter bw = new BufferedWriter(fw); // Lo pasamos por buffer para su manipulación

				for (int i = 0; i < partidas.size(); i++) {
					bw.write(partidas.get(i).getFechaYHora() + "," + partidas.get(i).getTiempoDeJuego());
					bw.newLine();

				}

				bw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void detectarChoque() {
		for (int i = 0; i < carritos.size(); i++) {
			if (PApplet.dist(personaje.getPosX(), personaje.getPosY(), carritos.get(i).getPosX(),
					carritos.get(i).getPosY()) < 20) {
				System.out.println("mechoque");
				choque = true;

			}
		}
	}

	public void perderException() throws PerderException {
		if (choque == true) {

			pantalla = 1;
			throw new PerderException();
		}
	}

	public void volverAJugar() {
		if (app.keyCode == 82) {
			app.setup();
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				// System.out.println("hola");
				for (int i = 0; i < carritos.size(); i++) {
					carritos.get(i).mover();
				}
				sleep(100);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}
	}

	public void pintarCarros() {

		for (int i = 0; i < carritos.size(); i++) {
			carritos.get(i).pintar();
		}
	}

	public void pintarCaminos() {

		// camino1
		app.fill(255, 0, 0);
		app.rect(0, 80, 600, 40);

		// camino2
		app.fill(255, 0, 0);
		app.rect(0, 150, 600, 40);

		// camino3
		app.fill(255, 0, 0);
		app.rect(0, 220, 600, 40);

		// camino4
		app.fill(255, 0, 0);
		app.rect(0, 290, 600, 40);

		// camino5
		app.fill(255, 0, 0);
		app.rect(0, 360, 600, 40);

		// camino6
		app.fill(255, 0, 0);
		app.rect(0, 430, 600, 40);
	}

	public void eventosTeclado() {
		personaje.mover();
	}

	public void contarTiempoDeJuego() {
		long nowMillis = System.currentTimeMillis();
		tiempoJuego = (int) ((nowMillis - this.createdMillis) / 1000);

	}

	public void pintarTiempoDeJuego() {
		app.fill(0);

		app.textSize(16);
		app.text("Tiempo de juego: " + tiempoJuego, 20, 20);
	}

	public String obtenerFechaDeJuego() {
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void guardarArchivosDeJuego() {
		if (app.keyCode == 71) {
			System.out.println("Se guardo la info");
			File file = new File("./data/dataCreada/dataDelJuego.txt");// Creación del archivo
			try {
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); // Lo cargamos para su escritura. AbsoluteFile para que no sobreescriba el txt sino que escriba en la linea siguiente
				BufferedWriter bw = new BufferedWriter(fw); // Lo pasamos por buffer para su manipulación
				bw.write(obtenerFechaDeJuego() + "," + tiempoJuego);
				bw.newLine();
				bw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			cargarInformacionDatosJuego();
		}
	}

	public void ganar() {
		if (personaje.getPosY() >= 500) {
			pantalla = 1;
			ganar = true;
		}
	}

	public void mostrarDatosDeJuegos() {
		if (app.keyCode == 32) {
			pantalla = 2;
		}
	}

	public void pintarBaseDeDatos() {

		for (int i = 0; i < partidas.size(); i++) {
			app.fill(0);
			app.text(partidas.get(i).getFechaYHora() + "  |  " + partidas.get(i).getTiempoDeJuego(), 100,
					100 + (i * 20));
		}

	}

	public void pintar() {
		switch (pantalla) {

		case 0:


			contarTiempoDeJuego();
			pintarTiempoDeJuego();
			pintarCaminos();
			pintarCarros();

			personaje.pintar();
			ganar();
			detectarChoque();

			try {
				perderException();
			} catch (PerderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// todo nuestro juego
			break;
		case 1:
			app.fill(0);
			app.text("Para guardar los datos presiona G", app.width / 2, app.height / 2 + 30);
			app.fill(0);
			app.text("PARA VER LOS DATOS PRESIONA BARRA ESPACIADORA", 20, 550);

			if (choque == true) {
				app.fill(0);
				app.text("Has PERDIDO, presiona R para reiniciar", app.width / 2, app.height / 2);
			} else if (ganar == true) {
				app.fill(0);
				app.text("Has GANADO, presiona R para reiniciar", app.width / 2, app.height / 2);
			}
			// pantalla perder o ganar
			break;
		case 2:

			// ordenarNaturalmente();
			app.fill(0);
			app.text("Presiona P para ordenar por tiempo de juego :)", 20, 550);
			app.text("Presiona N para ordenar por naturalmente :D", 20, 570);

			pintarBaseDeDatos();
			// pantalla organizar los datos
			break;

		}

	}

}
