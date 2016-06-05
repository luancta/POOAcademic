package cas.util.util;

import cas.comum.dominio.Turno;
import cas.ensino.dominio.Horario;

public class Validator {
	
	public boolean validarHorario(Horario horario){
		
		if(horario.getTurno() == null || horario.getTurno().getId() == 0){
			System.out.println("Turno inv�lido ou n�o informado.");
			return false;
		}
		
		if(horario.getHoraInicio() == null){
			System.out.println("Data inicial inv�lida ou n�o informado.");
			return false;
		}
		
		if(horario.getHoraFim() == null){
			System.out.println("Data final inv�lida ou n�o informado.");
			return false;
		}
		
		if(horario.getDiaSemana() == null){
			System.out.println("Dia semana inv�lido ou n�o informado.");
			return false;
		}
		
		return true;
	}
	
	public boolean validarTurno(Turno turno){
		
		if(turno.getDescricao().isEmpty()){
			System.out.println("Descri��o n�o informada ou inv�lida.");
			return false;
		}
		
		return true;
	}
}
