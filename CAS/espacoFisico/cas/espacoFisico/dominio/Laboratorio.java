package cas.espacoFisico.dominio;

import java.util.ArrayList;
import java.util.List;

public class Laboratorio {

	//Identificador
	private int id;
	
	//Nome do Laboratório
	private String nome;

	//Lista de aplicativos instalados nas estações do laboratório
	private List<Aplicativo> aplicativosLab = new ArrayList <Aplicativo>();
	
	public Laboratorio () {
		// TODO Auto-generated constructor stub
	}

	public Laboratorio(int id) {
		this.id = id;
	}

	public Laboratorio(String nome) {
		this.nome = nome;
	}

	public Laboratorio(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
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
