package cas.espacoFisico.dominio;

public class Projetor {

	//Identificador
	private int id;
	
	private  Marca marca;

	//Tombamento do projetor
	private int tombo;

	//Set e Get
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public int getTombo() {
		return tombo;
	}

	public void setTombo(int tombo) {
		this.tombo = tombo;
	}
	
	
}
