package cas.util.util;

import cas.comum.dominio.Turno;
import cas.ensino.dominio.Horario;

public class Validator {
	
	public boolean validarHorario(Horario horario){
		
		if(horario.getTurno() == null || horario.getTurno().getId() == 0){
			System.out.println("Turno inválido ou não informado.");
			return false;
		}
		
		if(horario.getHoraInicio() == null){
			System.out.println("Data inicial inválida ou não informado.");
			return false;
		}
		
		if(horario.getHoraFim() == null){
			System.out.println("Data final inválida ou não informado.");
			return false;
		}
		
		if(horario.getDiaSemana() == null){
			System.out.println("Dia semana inválido ou não informado.");
			return false;
		}
		
		return true;
	}
	
	public boolean validarTurno(Turno turno){
		
		if(turno.getDescricao().isEmpty()){
			System.out.println("Descrição não informada ou inválida.");
			return false;
		}
		
		return true;
	}
}
