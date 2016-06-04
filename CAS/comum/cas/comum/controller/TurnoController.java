package cas.comum.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.comum.dao.TurnoDAO;
import cas.comum.dominio.Turno;

public class TurnoController {

	public void selecionarOperacao() throws Exception{
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Turnos #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
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
		default: System.out.println("Opção selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar sala de aula
	 * @throws Exception 
	 */
	private void preCadastrarTurno() throws Exception{
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Turno #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite a descrição do turno a inserir (Manhã, Tarde e etc...):");
		String descricao = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		if(!descricao.isEmpty()){
			entrada.close();
			cadastrarTurno(descricao);
		}else{
			System.out.println("\n**Descrição: campo obrigatório não informado. \n");
			entrada.close();
			preCadastrarTurno();
		}
		
	}
	
	/**
	 * Cadastrar sala de aula
	 * @param descricao
	 * @throws Exception 
	 */
	private void cadastrarTurno(String descricao) throws Exception{
		TurnoDAO dao = new TurnoDAO();
		dao.save("INSERT INTO comum.turno "
				+ "(descricao) "
				+ "VALUES (?)", descricao);
		
		System.out.println("<<Cadastro realizado com sucesso.>>");
		selecionarOperacao();
	}
	
	//Lista de Turnos
	private void listarTurnos() throws Exception{
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
		selecionarOperacao();
	}
	
	private void preRemoverTurno() throws Exception{
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
	
	private void removerTurno(String desc) throws Exception{
		TurnoDAO dao = new TurnoDAO();
		
		String sql = "DELETE FROM comum.turno WHERE descricao = ?";

		dao.delete(sql, desc);
		
		System.out.println("Turno removido com sucesso.");
		selecionarOperacao();
	}
	
	private void voltar() throws Exception{
		MenuController menu = new MenuController();
		menu.getTelaMenu();
	}
	
}
