package model;

import java.util.Comparator;

public class CompareTiempoDeJuego implements Comparator<Partida> {

	@Override
	public int compare(Partida o1, Partida o2) {
		// TODO Auto-generated method stub
		return o1.getTiempoDeJuego()-o2.getTiempoDeJuego();
	}

}
