package cas.espacoFisico.dominio;

public class Sala extends LocalAula{

	//Identificador da sala
	private int id;
	
	
	
	//Set e Get
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	//Numero da sala
	private int numero;
}
