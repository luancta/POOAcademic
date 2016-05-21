package cas.espacoFisico.dominio;

public abstract class LocalAula {

	//bloco que agrega as salas e laboratórios
	private String bloco;
	//capacidade das salas e/ou laboratórios
	private int capacidade;
	
	//Set e Get
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
