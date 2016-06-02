package cas.ensino.dominio;

public class Disciplina {
	
	private int id;
	private String nome;
	private String sigla;
	
	public Disciplina() {
		// TODO Auto-generated constructor stub
	}
	
	public Disciplina(int id) {
		this.id = id;
	}
	
	public Disciplina(String nome) {
		this.nome = nome;
	}
	
	public Disciplina(String nome, String sigla) {
		this.nome = nome;
		this.sigla = sigla;
	}

	public Disciplina(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Disciplina(int id, String nome, String sigla) {
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
	}

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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	

}
