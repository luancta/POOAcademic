package cas.espacoFisico.dominio;

import java.util.ArrayList;
import java.util.List;

public class Sala{

	//Identificador da sala
	private int id;
	
	//Numero da sala
	private String numero;
	
	/**
	 * Lista de salas 
	 * @Transiente
	 */
	private List<Sala> salas = new ArrayList<Sala>();
	
	//Set e Get	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}
	
}
