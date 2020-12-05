package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Partida implements Comparable<Partida> {

	
	private String fechaYHora;
	private int tiempoDeJuego;
	public Partida(String fechaYHora, int tiempoDeJuego) {

		this.fechaYHora=fechaYHora;
		this.tiempoDeJuego=tiempoDeJuego;
	}
	
	

	public String getFechaYHora() {
		return fechaYHora;
	}



	public void setFechaYHora(String fechaYHora) {
		this.fechaYHora = fechaYHora;
	}



	public int getTiempoDeJuego() {
		return tiempoDeJuego;
	}



	public void setTiempoDeJuego(int tiempoDeJuego) {
		this.tiempoDeJuego = tiempoDeJuego;
	}



	@Override
	public int compareTo(Partida o) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date o1 = null;
		Date o2 = null;
		  try {
			  o1 = formatter.parse(fechaYHora);
			  o2 = formatter.parse(o.getFechaYHora());
	        } catch (ParseException pe) {
	            pe.printStackTrace();
	        }
		  return o1.compareTo(o2);
	}

}
