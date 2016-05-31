package cas.espacoFisico.dominio;

public class Aplicativo {

	//Identificador
	private int id;
	
	//Nome do Aplicativo
	private String nome;
	
	public Aplicativo () {
		// TODO Auto-generated constructor stub
	}

	public Aplicativo(int id) {
		this.id = id;
	}

	public Aplicativo(String nome) {
		this.nome = nome;
	}

	public Aplicativo(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	//Set e Get
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
