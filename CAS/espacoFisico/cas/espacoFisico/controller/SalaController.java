package cas.espacoFisico.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cas.espacoFisico.dao.SalaDao;
import cas.espacoFisico.dominio.Sala;

/**
 * Controlador de Sala de Aula
 * 
 * @author Gilley
 *
 */
public class SalaController {

	/**
	 * Selecionar opera��o a ser realizada
	 * 
	 * @throws IOException
	 */
	public void selecionarOperacao() throws IOException {

		Scanner entrada = new Scanner(System.in);

		System.out.println("####### Gerenciar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma op��o desejada:");
		System.out.println("1 - Cadastrar uma nova sala.;");
		System.out.println("2 - Listar sala(s) existente(s).;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarSala();
			break;
		case "2":
			listarSalas();
			break;
		case "9":
			voltar();
			break;
		default:
			System.out.println("Op��o selecionada inexistente");
			selecionarOperacao();
			break;
		}
	}

	/**
	 * Pre cadastrar sala de aula
	 * 
	 * @throws IOException
	 */
	private void preCadastrarSala() throws IOException {
		
		Scanner entrada = new Scanner(System.in);

		System.out.println("####### Cadastrar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite o n�mero da sala:");
		String numero = entrada.nextLine();
		System.out.println("---------------------------------------");

		if (!numero.isEmpty())
			cadastrarSala(numero);
		else {
			System.out.println("\n**N�mero: campo obrigat�rio n�o informado. \n");
			preCadastrarSala();
		}

	}

	private void preListar() throws IOException {
		Scanner entrada = new Scanner(System.in);

		System.out.println("####### Buscar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite um n�mero da sala:");
		String numero = entrada.nextLine();
		System.out.println("---------------------------------------");

		if (!numero.isEmpty())
			cadastrarSala(numero);
		else {
			System.out.println("\n**N�mero: campo obrigat�rio n�o informado. \n");
			preCadastrarSala();
		}

	}

	/**
	 * Cadastrar sala de aula
	 * 
	 * @param numero
	 * @throws IOException
	 */
	private void cadastrarSala(String numero) throws IOException {
		SalaDao dao = new SalaDao();
		dao.salvar(new Sala(numero));
		System.out.println("<<Cadastro realizado com sucesso.>>");
		selecionarOperacao();
	}
	
	/**
	 * Listar sala de aula
	 * 
	 * @throws IOException
	 */
	private void listarSalas() throws IOException {
		Scanner entrada = new Scanner(System.in);

		System.out.println("####### Listar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite o n�mero da sala de aula:");
		String filtro = entrada.nextLine();
		System.out.println("---------------------------------------");

		buscar(filtro);
	}

	/**
	 * Buscar e listar sala de aula
	 * 
	 * @param numero
	 * @throws IOException
	 */
	private void buscar(String filtro) throws IOException {
		SalaDao dao = new SalaDao();
		List<Sala> resultado = dao.findByNumero(filtro);

		if (resultado.isEmpty())
			System.out.println("\n**Nenhum registro foi encontrado.\n");
		else {
			System.out.println(" id | Numero | Opera��o ");
			for (Sala sala : resultado) {
				System.out.println(
						" " + sala.getId() + " | " + sala.getNumero() + " | R - Remover / B - Buscar / V - Voltar");
			}
		}

		System.out.println("");
		System.out.println("Total de " + resultado.size() + " salas encontradas.");

		Scanner entrada = new Scanner(System.in);

		System.out.println("####### Listar Sala de Aula #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor informe a opera��o desejada:");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();

		switch (operacao) {
		case "R":
			preRemover();
			break;
		case "B":
			listarSalas();
			break;
		case "V":
			selecionarOperacao();
			break;
		default:
			System.out.println("Op��o selecionada inexistente");
			buscar(filtro);
			break;
		}

	}

	/**
	 * Pre remover sala de aula
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private void preRemover() throws NumberFormatException, IOException {
		Scanner entrada = new Scanner(System.in);

		System.out.println("---------------------------------------");
		System.out.println("Por favor informe o id da sala de aula que voc� deseja remover:");
		String idSala = entrada.nextLine();
		if (idSala.contains("[a-zA-Z]+") == false && idSala.length() > 3) {
			System.out.println("Por favor informe um n�mero v�lido");
			preRemover();
		} else
			remover(Integer.parseInt(idSala));
	}

	/**
	 * Remover Sala de aula
	 * @param id
	 * @throws IOException
	 */
	private void remover(int id) throws IOException {
		SalaDao dao = new SalaDao();
		dao.remover(new Sala(id));
		System.out.println("<<Sala de aula removida com sucesso.>>");
		selecionarOperacao();
	}
	
	/**
	 * Voltar para ar�a administrativa
	 */
	private void voltar() {
		// TODO AINDA VAI SER CRIADO

	}

}
