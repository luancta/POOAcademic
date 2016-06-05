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

import cas.acesso.controller.MenuController;
import cas.comum.dao.TurnoDAO;
import cas.comum.dominio.DiaSemana;
import cas.comum.dominio.Turno;
import cas.ensino.dao.HorarioDAO;
import cas.ensino.dominio.Disciplina;
import cas.ensino.dominio.Horario;
import cas.util.util.ViewConsoleUtil;

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
		
		switch (operacao) {
		case "1":preCadastrarHorario(); break;
		case "2":listarHorarios(); break;
		case "3":preRemoverHorario(); break;
		case "9":voltar(); break;
		default: System.out.println("Opção selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar horario
	 * @throws Exception 
	 */
	private void preCadastrarHorario() throws Exception{
		Horario horario = new Horario();
		Scanner entradaTurno = new Scanner(System.in);
		Scanner entradaHoraInicio = new Scanner(System.in);
		Scanner entradaHoraFim = new Scanner(System.in);
		Scanner entradaDiaSemana = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Horario #######");
		System.out.println("---------------------------------------");
		System.out.println("Um horário é composto por 4 campos, sendo eles: turno, horário inicio, horário fim e dia da semana.");
		System.out.println("Por favor digite os dados do Horario a ser inserido:");	
		
		informaTurno(entradaTurno, horario);
		
		System.out.println("Por favor selecione o inicio do horário (Padrão HH:MM:SS, incluindo os dois pontos)...");
		
		//Tratando entrada da hora inicio
		String horaInicio = entradaHoraInicio.nextLine();
		
		Time horaIni = Time.valueOf("00:00:00");
		if(!horaInicio.isEmpty() && validadorFormatoHora(horaInicio)){
			 horaIni = Time.valueOf(horaInicio);
			 horario.setHoraInicio(horaIni);
		}else{
			System.out.println("Hora inicial inválida, retornando para o incio da operação...");
			selecionarOperacao();
		}
		
		
		System.out.println("Por favor selecione o final do horário (Padrão HH:MM:SS, incluindo os dois pontos)...");
		
		//Tratando entrada da hora fim
		String horaFinal = entradaHoraFim.nextLine();
		
		Time horaFim = Time.valueOf("00:00:00");
		if(!horaInicio.isEmpty() && validadorFormatoHora(horaFinal)){
			horaFim = Time.valueOf(horaFinal);
			horario.setHoraFim(horaFim);
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
		
		horario.setDiaSemana(DiaSemana.get(diaSemana));
		System.out.println("---------------------------------------");
	
		if(horario.validate())
			cadastrarHorario(horario);
		else
			preCadastrarHorario();
		
	}
	
	/**
	 * Cadastrar horario
	 * @param 
	 * @throws Exception 
	 */
	private void cadastrarHorario(Horario horario) throws Exception{
		
		HorarioDAO dao = new HorarioDAO();
		dao.salvar(horario);
		
		System.out.println("<<Cadastro realizado com sucesso.>>");
		selecionarOperacao();
	}
	
	//Lista de Horarios
	private void listarHorarios() throws Exception{
		HorarioDAO dao = new HorarioDAO();
		List<Horario> horarios = new ArrayList<Horario>();
		
		horarios = dao.findHorarios();
		
		if(!horarios.isEmpty()){
			System.out.println("### Lista de Horarios: ###");
			horarios.stream().forEach((horario) -> {
				System.out.println("#"+horario.getId()+" -  Hora Inicio: "+horario.getHoraInicio()+" / Hora Fim: "+horario.getHoraFim()
				+ " | Turno: "+horario.getTurno().getDescricao()+" Dia: "+horario.getDiaSemana());
			});
		}else{
			System.out.println("Nenhum horário encontrado.");
		}
		selecionarOperacao();
	}
	
	private void preRemoverHorario() throws Exception{
		Scanner entrada = new Scanner(System.in);
		HorarioDAO horarioDao = new HorarioDAO();
		
		
		System.out.println("####### Remover Horario #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite o identificador do horario que deseja remover...");
		System.out.println("Segue a lista de horarios");
		List<Horario> horarios = horarioDao.findHorarios();
		if(!horarios.isEmpty()){
			System.out.println("### Lista de Horarios: ###");
			horarios.stream().forEach((horario) -> {
				System.out.println("#"+horario.getId()+" -  Hora Inicio: "+horario.getHoraInicio()+" / Hora Fim: "+horario.getHoraFim()
				+ " | Turno: "+horario.getTurno().getDescricao()+" Dia: "+horario.getDiaSemana());
			});
			System.out.println("---------------------------------------");
		}else{
			System.out.println("Nenhum horário encontrado.");
		}
		
		
		Integer idHorario = entrada.nextInt();
		
		if(idHorario != 0){
			removerTurno(idHorario);
		}else{
			System.out.println("\n**ID Horario: campo obrigatório não informado. \n");
			preRemoverHorario();
		}
		
	}
	
	private void removerTurno(int idHorario) throws Exception{
		HorarioDAO dao = new HorarioDAO();
		Horario objRemovido = dao.findHorarioById(idHorario);
		
		if(objRemovido == null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + idHorario+" não foi encontrado.");
			preRemoverHorario();
		}
		dao.remover(idHorario);
		System.out.println("Horario removido com sucesso.");
		selecionarOperacao();
	}
	
	private void voltar() throws Exception{
		MenuController menu = new MenuController();
		menu.getTelaMenu();
	}
	
	private void informaTurno(Scanner entradaTurno, Horario horario) throws Exception{
		System.out.println("Por favor selecione o turno, cujo horário fará parte...");
		listarTurnos();
		
		String idTurno = entradaTurno.nextLine();
		String regex = "\\d+";
		if(!idTurno.isEmpty() && idTurno.matches(regex)){
			TurnoDAO dao = new TurnoDAO();
			Turno turno = dao.findTurnobyId(Integer.parseInt(idTurno));
			if(turno != null && turno.getId() != 0){
				horario.setTurno(turno);
			}
			
		}else{
			System.out.println("Opção Incorreta, retornando para o incio da operação...");
			selecionarOperacao();
		}
		
	}
	
	private void listarTurnos() throws Exception{
		TurnoDAO dao = new TurnoDAO();
		List<Turno> turnos = dao.findTurnos();
		
		if(!turnos.isEmpty()){
			System.out.println("### Lista de Turnos: ###");
			turnos.stream().forEach((turno) -> {
				System.out.println("#"+turno.getId()+" - "+turno.getDescricao());
			});
		}else{
			System.out.println("Nenhum turno encontrado.");
			System.out.println("Selecione opção 0 - Sem Turno.");
		}
		
	}
	
	private boolean validadorFormatoHora(String hora){
		if(hora.length() == 8){	
			String regex = "\\d\\d\\:\\d\\d\\:\\d\\d";
			if(!hora.matches(regex)){
				return false;
			}else{
				return validadorHora(hora);
			}
		}else{
			return false;
		}
	}
	
	
private boolean validadorHora(String hora){
		
		int indexOfInicioMinuto = 3;
		int indexOfInicioHora = 0;
		int indexOfInicioSegundo = 6;
		
		if(hora.length() == 8){	
			int primeiroDigitoMinuto = Character.getNumericValue(hora.charAt(indexOfInicioMinuto));
			int segundoDigitoMinuto = Character.getNumericValue(hora.charAt(indexOfInicioMinuto+1));
			int primeiroDigitoHora = Character.getNumericValue(hora.charAt(indexOfInicioHora));
			int segundoDigitoHora = Character.getNumericValue(hora.charAt(indexOfInicioHora+1));
			int primeiroDigitoSegundo = Character.getNumericValue(hora.charAt(indexOfInicioSegundo));
			int segundoDigitoSegundo = Character.getNumericValue(hora.charAt(indexOfInicioSegundo+1));
			
			if(primeiroDigitoMinuto < 0 || segundoDigitoMinuto < 0 || primeiroDigitoHora < 0 || segundoDigitoHora < 0 || primeiroDigitoSegundo < 0 || segundoDigitoSegundo < 0)
				return false;
			
			if( primeiroDigitoHora > 2 || (primeiroDigitoHora == 2 && segundoDigitoHora > 3)
					|| primeiroDigitoMinuto > 5 || primeiroDigitoSegundo > 5){
				return false;
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
