package cas.espacoFisico.dominio;

public class Sala extends LocalAula{

	//Identificador da sala
	private int id;
	public int getId() {
		return id;
	}
	
	//Set e Get
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
