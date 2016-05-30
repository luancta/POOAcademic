package cas.espacoFisico.controller;

import java.util.Scanner;

import cas.espacoFisico.dao.SalaDao;

/**
 * Controlador de Sala de Aula
 * @author Gilley
 *
 */
public class SalaController {
	
	/**
	 * Selecionar operação a ser realizada
	 */
	public void selecionarOperacao(){
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Cadastrar uma nova sala.;");
		System.out.println("2 - Listar sala(s) existente(s).;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":preCadastrarSala(); break;
		case "2":listarSalas(); break;
		case "9":voltar(); break;
		default: System.out.println("Opção selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar sala de aula
	 */
	private void preCadastrarSala(){
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite o número da sala:");
		String numero = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		if(!numero.isEmpty())
			cadastrarSala(numero);
		else{
			System.out.println("\n**Número: campo obrigatório não informado. \n");
			preCadastrarSala();
		}
		
	}
	
	/**
	 * Cadastrar sala de aula
	 * @param numero
	 */
	private void cadastrarSala(String numero){
		SalaDao dao = new SalaDao();
		dao.save("INSERT INTO espaco_fisico.sala "
				+ "(numero) "
				+ "VALUES (?)", numero);
		
		System.out.println("<<Cadastro realizado com sucesso.>>");
		selecionarOperacao();
	}
	
	/**
	 * listar sala de aula
	 */
	private void listarSalas(){
		
	}
	
	/**
	 * Voltar para aréa administrativa
	 */
	private void voltar(){
		//TODO AINDA VAI SER CRIADO
	}
	

}
