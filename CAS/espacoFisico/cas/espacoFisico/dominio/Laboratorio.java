package cas.espacoFisico.dominio;

import java.util.List;

public class Laboratorio extends LocalAula {

	//Identificador
	private int id;
	
	//Nome do Laborat�rio
	private String nome;

	//Lista de aplicativos instalados nas esta��es do laborat�rio
	private List<Aplicativo> aplicativosLab;
	
	
	//Set e Get
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Aplicativo> getAplicativosLab() {
		return aplicativosLab;
	}

	public void setAplicativosLab(List<Aplicativo> aplicativosLab) {
		this.aplicativosLab = aplicativosLab;
	}
	
}
