package cas.espacoFisico.dominio;

public class Projetor {

	//Identificador
	private int id;
	
	private  Marca marca;

	//Tombamento do projetor
	private String tombo;
	
	public Projetor() {
		// TODO Auto-generated constructor stub
	}

	public Projetor(int id) {
		this.id = id;
	}

	public Projetor(String tombo) {
		this.tombo = tombo;
	}

	public Projetor(int id, String tombo) {
		this.id = id;
		this.tombo = tombo;
	}

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

	public String getTombo() {
		return tombo;
	}

	public void setTombo(String tombo) {
		this.tombo = tombo;
	}
	
	
}
