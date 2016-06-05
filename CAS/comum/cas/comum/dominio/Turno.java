package cas.comum.dominio;

import cas.util.util.Validator;

public class Turno {
	
	private int id;
	private String descricao;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean validate(){
		Validator val =  new Validator();
		return val.validarTurno(this);
	}

}
