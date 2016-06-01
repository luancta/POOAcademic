package cas.espacoFisico.dominio;

public class LaboratorioAplicativo {

	//Identificador
	private int id;
	
	//Laboratório
	private Laboratorio laboratorio;
	
	//Aplicativo
	private Aplicativo aplicativo;

	public LaboratorioAplicativo () {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Aplicativo getAplicativo() {
		return aplicativo;
	}

	public void setAplicativo(Aplicativo aplicativo) {
		this.aplicativo = aplicativo;
	}

	
}
