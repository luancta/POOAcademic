package cas.ensino.controller;

import java.rmi.registry.LocateRegistry;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sun.jmx.snmp.Timestamp;

import cas.comum.dao.TurnoDAO;
import cas.comum.dominio.DiaSemana;
import cas.comum.dominio.Turno;
import cas.ensino.dao.HorarioDAO;
import cas.ensino.dominio.Disciplina;
import cas.ensino.dominio.Horario;

public class HorarioController {

public void selecionarOperacao() throws Exception {
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Horarios #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Cadastrar um novo Horário.;");
		System.out.println("2 - Listar Horário(s) existente(s).;");
		System.out.println("3 - Remover Horário Existente.;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");
		
		String operacao = entrada.nextLine();
		
		entrada.close();
		
		switch (operacao) {
		case "1":preCadastrarHorario(); break;
		//case "2":listarHorario(); break;
		//case "3":preRemoverHorario(); break;
		case "9":voltar(); break;
		default: System.out.println("Opção selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar sala de aula
	 * @throws Exception 
	 */
	private void preCadastrarHorario() throws Exception{
		List<Turno> turnos = new ArrayList<Turno>();
		TurnoDAO turnoDAO = new TurnoDAO();
		Scanner entradaTurno = new Scanner(System.in);
		Scanner entradaHoraInicio = new Scanner(System.in);
		Scanner entradaHoraFim = new Scanner(System.in);
		Scanner entradaDiaSemana = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Horario #######");
		System.out.println("---------------------------------------");
		System.out.println("Um horário é composto por 4 campos, sendo eles: turno, horário inicio, horário fim e dia da semana.");
		System.out.println("Por favor digite os dados do Horario a ser inserido:");	
		System.out.println("Por favor selecione o turno, cujo horário fará parte...");
		
		//Listando os turnos
		int count = 0;
		turnos = turnoDAO.findTurnos();
		
		if(!turnos.isEmpty()){
			System.out.println("### Lista de Turnos: ###");
			for(Turno turno : turnos){
				count++;			
				System.out.println("#"+count+" - "+turno.getDescricao());
			}
		}else{
			System.out.println("Nenhum turno encontrado.");
			System.out.println("Selecione opção 0 - Sem Turno.");
		}
		
		int turnoOp = entradaTurno.nextInt();
		entradaTurno.close();
		
		Turno turno = new Turno();
		if(turnoOp < 0){
			System.out.println("Opção Incorreta, retornando para o incio da operação...");
			selecionarOperacao();
		}else{
			turno.setId(turnoOp);
		}
		
		System.out.println("Por favor selecione o inicio do horário (Padrão HH:MM:SS, incluindo os dois pontos)...");
		
		//Tratando entrada da hora inicio
		String horaInicio = entradaHoraInicio.nextLine();
		entradaHoraInicio.close();
		
		Time horaIni = Time.valueOf("99:99:00");
		if(!horaInicio.isEmpty() && validadorHora(horaInicio)){
			 horaIni = Time.valueOf(horaInicio);
		}else{
			System.out.println("Hora inicial inválida, retornando para o incio da operação...");
			selecionarOperacao();
		}
		
		
		System.out.println("Por favor selecione o fim do horário (Padrão DD-MM-AAAA)...");
		
		//Tratando entrada da data fim
		String horaFinal = entradaHoraFim.nextLine();
		entradaHoraInicio.close();
		
		Time horaFim = Time.valueOf("99:99:00");
		if(!horaInicio.isEmpty() && validadorHora(horaFinal)){
			horaFim = Time.valueOf(horaFinal);
		}else{
			System.out.println("Hora Final inválida, retornando para o incio da operação...");
			selecionarOperacao();
		}
		
		System.out.println("Por favor selecione o dia da semana...");
		System.out.println("0 - Domingo");
		System.out.println("1 - Segunda");
		System.out.println("2 - Terça");
		System.out.println("3 - Quarta");
		System.out.println("4 - Quinta");
		System.out.println("5 - Sexta");
		System.out.println("6 - Sabado");
		
		int diaSemana = entradaDiaSemana.nextInt();
		entradaDiaSemana.close();
		
		if(diaSemana < 0 || diaSemana > 6){
			System.out.println("Opção Incorreta, retornando para o incio da operação...");
			selecionarOperacao();
		}
		System.out.println("---------------------------------------");
	
		cadastrarHorario(turno, horaIni, horaFim, diaSemana);
		
	}
	
	/**
	 * Cadastrar horario
	 * @param 
	 * @throws Exception 
	 */
	private void cadastrarHorario(Turno turno, Time horaInicio, Time horaFim, int dia) throws Exception{
		
		Horario horario = new Horario();

		horario.setTurno(turno);
		horario.setHoraInicio(horaInicio);
		horario.setHoraFim(horaFim);
		horario.setDiaSemana(DiaSemana.get(dia));
		HorarioDAO dao = new HorarioDAO();
		dao.salvar(horario);
		
		System.out.println("<<Cadastro realizado com sucesso.>>");
		selecionarOperacao();
	}
	
/*	//Lista de Turnos
	private void listarTurnos(){
		TurnoDAO dao = new TurnoDAO();
		List<Turno> turnos = new ArrayList<Turno>();
		int count = 0;
		
		turnos = dao.findTurnos();
		
		if(!turnos.isEmpty()){
			System.out.println("### Lista de Turnos: ###");
			for(Turno turno : turnos){
				count++;			
				System.out.println("#"+count+" - "+turno.getDescricao());
			}
		}else{
			System.out.println("Nenhum turno encontrado.");
		}
	}
	
	private void preRemoverTurno(){
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Remover Turno #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite a descrição do turno que deseja remover (Manhã, Tarde e etc...):");
		String descricao = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		if(!descricao.isEmpty()){
			entrada.close();
			removerTurno(descricao);
		}else{
			System.out.println("\n**Descrição: campo obrigatório não informado. \n");
			entrada.close();
			preCadastrarTurno();
		}
	}
	
	private void removerTurno(String desc){
		TurnoDAO dao = new TurnoDAO();
		
		String sql = "DELETE FROM comum.turno WHERE descricao = ?";

		dao.delete(sql, desc);
		
		System.out.println("Turno removido com sucesso.");
		selecionarOperacao();
	}
	*/
	private void voltar(){
		
	}
	
private boolean validadorHora(String hora){
		
		hora = hora.replace(':', '*');
		String sub = "";
		int indexOfMarcoSegundo = 5;
		int indexOfMarcoMinuto = 2;
		int indexOfInicioMinuto = 3;
		int indexOfInicioHora = 0;
		int indexOfInicioSegundo = 6;
		
		if(hora.length() == 8){	
			int primeiroDigitoMinuto = Integer.parseInt(hora, indexOfInicioMinuto);
			int segundoDigitoMinuto = Integer.parseInt(hora, indexOfInicioMinuto+1);
			int primeiroDigitoHora = Integer.parseInt(hora, indexOfInicioHora);
			int segundoDigitoHora = Integer.parseInt(hora, indexOfInicioHora+1);
			int primeiroDigitoSegundo = Integer.parseInt(hora, indexOfInicioSegundo);
			int segundoDigitoSegundo = Integer.parseInt(hora, indexOfInicioSegundo+1);
			
			for (int i = hora.length() - 1 ; i <= 0 ; i--){
				sub = hora.substring(i-1, i);
				if(sub.equals(":")){
					return false;
				}
				if((sub.equals("*") && i != indexOfMarcoMinuto) || (sub.equals("*") && i != indexOfMarcoSegundo)){
					return false;
				}
				if(!isNumeric(sub)){
					return false;
				}
				if((i == indexOfInicioHora && primeiroDigitoHora > 2) || (i == indexOfInicioHora && primeiroDigitoHora == 2 && segundoDigitoHora > 3)
						|| (i == indexOfInicioMinuto && primeiroDigitoMinuto > 6) || (i == primeiroDigitoMinuto && primeiroDigitoMinuto == 6 && segundoDigitoMinuto > 0)
						|| (i == indexOfInicioSegundo && primeiroDigitoSegundo > 6) || (i == indexOfInicioSegundo && primeiroDigitoSegundo == 6 && segundoDigitoSegundo > 0)){
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
	
	
	
	
	private boolean validadorData(String data){
		
		data = data.replace('-', '*');
		String sub = "";
		int indexOfMarcoAno = 5;
		int indexOfMarcoMes = 2;
		int indexOfInicioMes = 3;
		int indexOfInicioDia = 0;
		
		if(data.length() == 10){	
			int primeiroDigitoMes = Integer.parseInt(data, indexOfInicioMes);
			int segundoDigitoMes = Integer.parseInt(data, indexOfInicioMes+1);
			int primeiroDigitoDia = Integer.parseInt(data, indexOfInicioDia);
			int segundoDigitoDia = Integer.parseInt(data, indexOfInicioDia+1);
			
			for (int i = data.length() - 1 ; i <= 0 ; i--){
				sub = data.substring(i-1, i);
				if(sub.equals("-")){
					return false;
				}
				if((sub.equals("*") && i != indexOfMarcoAno) || (sub.equals("*") && i != indexOfMarcoMes)){
					return false;
				}
				if(!isNumeric(sub)){
					return false;
				}
				if((i == indexOfInicioMes && primeiroDigitoMes > 1) || (i == indexOfInicioMes && primeiroDigitoMes == 1 && segundoDigitoMes > 2)
						|| (i == indexOfInicioDia && primeiroDigitoDia > 3) || (i == indexOfInicioDia && primeiroDigitoDia == 3 && segundoDigitoDia > 0)
						|| (i == indexOfInicioMes && primeiroDigitoMes == 0 && segundoDigitoMes == 0)
						|| (i == indexOfInicioDia && primeiroDigitoDia == 0 && segundoDigitoDia == 0)){
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
	private boolean isNumeric (String str){
		String sub = "";
		for (int i = 0; i < str.length() - 1 ; i++){
			sub = str.substring(i, i);
			if(sub.equals("0") && sub.equals("1") && sub.equals("2") && sub.equals("3") && sub.equals("4")
					&& sub.equals("5") && sub.equals("6") && sub.equals("7") && sub.equals("8") && sub.equals("9")){
				return false;
			}
		}
		return true;
	}
}
