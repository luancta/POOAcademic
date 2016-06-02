package cas.ensino.dominio;

import cas.comum.dominio.Pessoa;

public class Estudante {
	
	private int id;
	private String matricula;
	private String curso;
	private Pessoa pessoa;
	
	public Estudante () {
		// TODO Auto-generated constructor stub
	}

	
	public Estudante(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
