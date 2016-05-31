package cas.comum.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cas.comum.dao.TurnoDAO;
import cas.comum.dominio.Turno;

public class TurnoController {

	public void selecionarOperacao(){
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Turnos #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma op��o desejada:");
		System.out.println("1 - Cadastrar um novo Turno.;");
		System.out.println("2 - Listar Turno(s) existente(s).;");
		System.out.println("3 - Remover Turno Existente.;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":preCadastrarTurno(); break;
		case "2":listarTurnos(); break;
		case "3":preRemoverTurno(); break;
		case "9":voltar(); break;
		default: System.out.println("Op��o selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar sala de aula
	 */
	private void preCadastrarTurno(){
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Turno #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite a descri��o do turno a inserir (Manh�, Tarde e etc...):");
		String descricao = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		if(!descricao.isEmpty()){
			entrada.close();
			cadastrarTurno(descricao);
		}else{
			System.out.println("\n**Descri��o: campo obrigat�rio n�o informado. \n");
			entrada.close();
			preCadastrarTurno();
		}
		
	}
	
	/**
	 * Cadastrar sala de aula
	 * @param descricao
	 */
	private void cadastrarTurno(String descricao){
		TurnoDAO dao = new TurnoDAO();
		dao.save("INSERT INTO comum.turno "
				+ "(descricao) "
				+ "VALUES (?)", descricao);
		
		System.out.println("<<Cadastro realizado com sucesso.>>");
		selecionarOperacao();
	}
	
	//Lista de Turnos
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
		System.out.println("Por favor digite a descri��o do turno que deseja remover (Manh�, Tarde e etc...):");
		String descricao = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		if(!descricao.isEmpty()){
			entrada.close();
			removerTurno(descricao);
		}else{
			System.out.println("\n**Descri��o: campo obrigat�rio n�o informado. \n");
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
	
	private void voltar(){
		
	}
	
}
