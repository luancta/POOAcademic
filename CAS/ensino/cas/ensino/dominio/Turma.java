package cas.ensino.dominio;

public class Turma {
	
	private int id;
	private String codigo;
	
	public Turma() {
		// TODO Auto-generated constructor stub
	}

	public Turma(int id) {
		this.id = id;
	}

	public Turma(String codigo) {
		this.codigo = codigo;
	}

	public Turma(int id, String codigo) {
		this.id = id;
		this.codigo = codigo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	

}
