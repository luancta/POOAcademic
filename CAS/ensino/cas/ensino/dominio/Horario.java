package cas.ensino.dominio;

import java.sql.Time;

import cas.comum.dominio.Turno;
import cas.comum.dominio.DiaSemana;

public class Horario {
	
	private int id;
	private Turno turno;
	private Time horaInicio;
	private Time horaFim;
	private DiaSemana diaSemana;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Time getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Time horaFim) {
		this.horaFim = horaFim;
	}
	public DiaSemana getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}
	
		

}
