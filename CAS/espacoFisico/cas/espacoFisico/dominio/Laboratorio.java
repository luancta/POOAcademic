package cas.espacoFisico.dominio;

public class Laboratorio extends LocalAula {

	//Identificador
	private int id;
	
	//Nome do Laboratório
	private String nome;

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
	
	
}
