package cas.acesso.dominio;

import cas.comum.dominio.Pessoa;

public class Usuario {
	
	private int id;
	private String login;
	private String senha;
	private Integer tipoUsuario;
	private Pessoa pessoa;
	
	public Usuario () {
		// TODO Auto-generated constructor stub
	}

	
	public Usuario(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Integer getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	

}
