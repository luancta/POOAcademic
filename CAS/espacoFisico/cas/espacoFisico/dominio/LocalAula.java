package cas.espacoFisico.dominio;

public class LocalAula {
	
	private int id;
	private Laboratorio laboratorio;
	private Sala sala;
	//bloco que agrega as salas e laboratórios
	private String bloco;
	//capacidade das salas e/ou laboratórios
	private int capacidade;

	//Set e Get
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
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public String getBloco() {
		return bloco;
	}
	public void setBloco(String bloco) {
		
		this.bloco = bloco;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
}
