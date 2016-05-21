package cas.ensino.dominio;

import java.util.Date;

import cas.espacoFisico.dominio.LocalAula;

public class Reserva {
	/** Identificador da reserva */
	private int id;
	/** Indica se a reserva do LABORAT�RIO vai utilizar o projetor */
	private boolean usaProjetor;
	/** Indica se a reserva da sala est� ativa */
	private boolean ativo;
	/** Indica a data que foi feita a reserva da sala e ou laborat�rio */
	private Date data;
	/** Indica qual � a turma que vai utilizar o laborat�rio e ou sala reservada */
	private Turma turma;
	/** Indica o local do laborat�rio e ou sala que foi reservado */
	private LocalAula localAula;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isUsaProjetor() {
		return usaProjetor;
	}
	public void setUsaProjetor(boolean usaProjetor) {
		this.usaProjetor = usaProjetor;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public LocalAula getLocalAula() {
		return localAula;
	}
	public void setLocalAula(LocalAula localAula) {
		this.localAula = localAula;
	}
}