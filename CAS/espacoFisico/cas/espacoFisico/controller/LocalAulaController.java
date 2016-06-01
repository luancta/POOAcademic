package cas.espacoFisico.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.LaboratorioDao;
import cas.espacoFisico.dao.LocalAulaDao;
import cas.espacoFisico.dao.SalaDao;
import cas.espacoFisico.dominio.Laboratorio;
import cas.espacoFisico.dominio.LocalAula;
import cas.espacoFisico.dominio.Sala;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Local de Aula
 * 
 * @author Gilley
 *
 */
public class LocalAulaController {

	/**
	 * Selecionar opera��o a ser realizada
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void selecionarOperacao() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Local de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma op��o desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um novo local de aula");
		ViewConsoleUtil.setOpcao(2, "Listar locais de aula existentes");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarLocalAula();
			break;
		case "2":
			listarLocalAulas();
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
	 * Pre cadastrar localAula de aula
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preCadastrarLocalAula() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);
		LocalAula localAula = new LocalAula();

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Local de Aula");
		ViewConsoleUtil.setDivisor();

		informarBloco(entrada, localAula);
		informarCapacidade(entrada, localAula);
		ViewConsoleUtil.setMensagemOpcao("Deseja informar uma sala ou laborat�rio?");
		ViewConsoleUtil.setOpcao(1, "Sala");
		ViewConsoleUtil.setOpcao(2, "Laborat�rio");
		String operacao = entrada.nextLine();
		switch (operacao) {
		case "1":
			informarSala(entrada, localAula);
			break;
		case "2":
			informarLaboratorio(entrada, localAula);
			break;
		default:
			System.out.println("Op��o selecionada inexistente");
			selecionarOperacao();
			break;
		}

		cadastrarLocalAula(localAula);
	}

	private void informarBloco(Scanner entrada, LocalAula localAula) {

		ViewConsoleUtil.setMensagemOpcao("Por favor digite o bloco correspondente");
		String bloco = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (bloco.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Bloco: campo obrigat�rio n�o informado.");
			informarBloco(entrada, localAula);
		}
		localAula.setBloco(bloco);
	}

	private void informarCapacidade(Scanner entrada, LocalAula localAula) {

		ViewConsoleUtil.setMensagemOpcao("Por favor digite a capacidade correspondente");
		String capacidade = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (capacidade.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Capacidade: campo obrigat�rio n�o informado.");
			informarCapacidade(entrada, localAula);
		}

		String regex = "\\d+";
		if (!capacidade.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe um valor n�merico v�lido.");
			informarCapacidade(entrada, localAula);
		}
		localAula.setCapacidade(Integer.parseInt(capacidade));
	}

	private void informarSala(Scanner entrada, LocalAula localAula) throws IOException, ParseException {

		listarSalas();

		ViewConsoleUtil.setMensagemOpcao("Por favor digite o n�mero da sala correspondente");
		String sala = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		String regex = "\\d+";
		if (!sala.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe uma sala v�lida.");
			informarSala(entrada, localAula);
		}

		SalaDao salaDao = new SalaDao();
		localAula.setSala(salaDao.findById(Integer.parseInt(sala)));
	}

	/**
	 * Listar sala de aula
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void listarSalas() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Sala de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o n�mero da sala de aula");
		String filtro = entrada.nextLine();

		buscarSala(filtro);
	}

	/**
	 * Buscar e listar sala de aula
	 * 
	 * @param numero
	 * @throws IOException
	 * @throws ParseException
	 */
	private void buscarSala(String filtro) throws IOException, ParseException {

		SalaDao dao = new SalaDao();
		List<Sala> resultado = dao.findByNumero(filtro);
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "N�mero");
			for (Sala sala : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(sala.getId()), sala.getNumero());
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " salas encontradas.");
			System.out.println("");
		}
	}

	private void informarLaboratorio(Scanner entrada, LocalAula localAula) throws IOException, ParseException {

		listarLaboratorio();

		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome do laborat�rio correspondente");
		String laboratorio = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		String regex = "\\d+";
		if (!laboratorio.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe um laborat�rio v�lido.");
			informarLaboratorio(entrada, localAula);
		}

		LaboratorioDao labDao = new LaboratorioDao();
		localAula.setLaboratorio(labDao.findById(Integer.parseInt(laboratorio)));
	}

	/**
	 * Listar sala de aula
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void listarLaboratorio() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Laborat�rio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome do laborat�rio");
		String filtro = entrada.nextLine();

		buscarLaboratorio(filtro);
	}

	/**
	 * Buscar e listar sala de aula
	 * 
	 * @param numero
	 * @throws IOException
	 * @throws ParseException
	 */
	private void buscarLaboratorio(String filtro) throws IOException, ParseException {

		LaboratorioDao labDao = new LaboratorioDao();
		List<Laboratorio> resultado = labDao.findByNome(filtro);
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Nome");
			for (Laboratorio laboratorio : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(laboratorio.getId()), laboratorio.getNome());
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " laboratorios encontrados.");
			System.out.println("");
		}
	}

	/**
	 * Cadastrar localAula de aula
	 * 
	 * @param tombo
	 * @throws IOException
	 * @throws ParseException
	 */
	private void cadastrarLocalAula(LocalAula localAula) throws IOException, ParseException {
		LocalAulaDao dao = new LocalAulaDao();
		dao.salvar(localAula);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar localAula de aula
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void listarLocalAulas() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Local de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o n�mero do local de aula");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar localAula de aula
	 * 
	 * @param tombo
	 * @throws IOException
	 * @throws ParseException
	 */
	private void buscar(String filtro) throws IOException, ParseException {

		LocalAulaDao dao = new LocalAulaDao();
		List<LocalAula> resultado = dao.findByBloco(filtro);

		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "bloco", "capacidade", "sala", "laborat�rio", "Opera��o");
			for (LocalAula localAula : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(localAula.getId()), localAula.getBloco(),
						String.valueOf(localAula.getCapacidade()),
						localAula.getSala() != null ? localAula.getSala().getNumero() : "",
						localAula.getLaboratorio() != null ? localAula.getLaboratorio().getNome() : "",
						"R - Remover / B - Buscar / V - Voltar");
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " local de aulas encontrados.");
			System.out.println("");
		}

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setMensagemOpcao("Por favor informe a opera��o desejada");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();
		else {
			ViewConsoleUtil.setMensagemErro("A op��o informada n�o pode ser um n�mero");
			buscar(filtro);
		}

		switch (operacao) {
		case "R":
			preRemover();
			break;
		case "B":
			listarLocalAulas();
			break;
		case "V":
			selecionarOperacao();
			break;
		default:
			ViewConsoleUtil.setMensagemErro("Op��o selecionada inexistente");
			buscar(filtro);
			break;
		}

	}

	/**
	 * Pre remover localAula de aula
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preRemover() throws NumberFormatException, IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do local de aula que voc� deseja remover");
		String idLocalAula = entrada.nextLine();

		String regex = "\\d+";
		if (!idLocalAula.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("A op��o informada deve ser um n�mero");
			preRemover();
		} else
			remover(Integer.parseInt(idLocalAula));
	}

	/**
	 * Remover LocalAula de aula
	 * 
	 * @param id
	 * @throws IOException
	 * @throws ParseException
	 */
	private void remover(int id) throws IOException, ParseException {

		LocalAulaDao dao = new LocalAulaDao();
		LocalAula objRemovido = dao.findById(id);
		if (objRemovido == null) {
			ViewConsoleUtil.setMensagemErro("O registro informado " + id + " n�o foi encontrado.");
			preRemover();
		}

		dao.remover(new LocalAula(id));
		ViewConsoleUtil.setMensagemOperacao("Local de aula removido com sucesso");
		selecionarOperacao();
	}

	/**
	 * Voltar para ar�a administrativa
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void voltar() throws ParseException, IOException {

		MenuController menu = new MenuController();
		menu.getTelaMenu();

	}

}
