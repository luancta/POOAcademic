package cas.espacoFisico.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.ensino.dao.TurmaDao;
import cas.ensino.dominio.Turma;
import cas.espacoFisico.dao.LocalAulaDao;
import cas.espacoFisico.dao.ReservaDao;
import cas.espacoFisico.dominio.LocalAula;
import cas.espacoFisico.dominio.Reserva;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Reserva de
 * 
 * @author Gilley
 *
 */
public class ReservaController {

	private Reserva reserva;

	/**
	 * Selecionar operação a ser realizada
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public void selecionarOperacao() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Reserva");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um nova reserva");
		ViewConsoleUtil.setOpcao(2, "Listar reservas existentes");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarReserva();
			break;
		case "2":
			listarReservas();
			break;
		case "9":
			voltar();
			break;
		default:
			System.out.println("Opção selecionada inexistente");
			selecionarOperacao();
			break;
		}
	}

	/**
	 * Pre cadastrar reserva de
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void preCadastrarReserva() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);
		reserva = new Reserva();
		reserva.setAtivo(true);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Reserva");
		ViewConsoleUtil.setDivisor();

		// DO METODOS PARA INFORMAR
		informarUsaProjetor(entrada);
		informarData(entrada);
		informarTurma(entrada);
		informarLocalAula(entrada);

		cadastrarReserva(reserva);
	}

	/**
	 * Informar data de reserva
	 * 
	 * @param entrada
	 */
	private void informarData(Scanner entrada) {
		ViewConsoleUtil.setMensagemOpcao("Por favor digite a Data (DD/MM/YYYY)");
		String data = entrada.nextLine();
		Date dataFormatada = null;
		if (!data.isEmpty()) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			try {
				dataFormatada = new java.sql.Date(formato.parse(data).getTime());
			} catch (ParseException e) {
				ViewConsoleUtil.setMensagemErro("Por favor informe a data de acordo com o informado.");
				informarData(entrada);
			}
		}

		reserva.setData(dataFormatada);

	}

	/**
	 * Informar caso reserva utiliza projetor
	 * 
	 * @param entrada
	 */
	private void informarUsaProjetor(Scanner entrada) {

		ViewConsoleUtil.setMensagemOpcao("Por favor informe se a reserva usa projetor");
		ViewConsoleUtil.setOpcao(1, "Sim");
		ViewConsoleUtil.setOpcao(2, "Não");
		String usaProjetor = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (usaProjetor.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Usa projetor: campo obrigatório não informado.");
			informarUsaProjetor(entrada);
		}

		switch (usaProjetor) {
		case "1":
			reserva.setUsaProjetor(true);
			break;
		case "2":
			reserva.setUsaProjetor(false);
			break;
		default:
			System.out.println("Opção informada inexistente");
			informarUsaProjetor(entrada);
			break;
		}

	}

	/**
	 * Informar Turma
	 * 
	 * @param entrada
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void informarTurma(Scanner entrada) throws NumberFormatException, Exception {

		listarTurmas();

		ViewConsoleUtil.setMensagemOpcao("Por favor informe o código da turma correspondente");
		String turma = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		String regex = "\\d+";
		if (!turma.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe uma Turma válida.");
			informarTurma(entrada);
		}

		TurmaDao turmaDao = new TurmaDao();
		reserva.setTurma(turmaDao.findById(Integer.parseInt(turma)));
	}

	/**
	 * Listar Turmas
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void listarTurmas() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Turmas");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o código da turma de aula");
		String filtro = entrada.nextLine();

		buscarTurma(filtro);
	}

	/**
	 * Buscar e listar Turma
	 * 
	 * @param filtro
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void buscarTurma(String filtro) throws NumberFormatException, Exception {

		TurmaDao dao = new TurmaDao();
		List<Turma> resultado = dao.findByCodigo(filtro);
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Código");
			for (Turma turma : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(turma.getId()), turma.getCodigo());
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " turmas encontradas.");
			System.out.println("");
		}
	}

	/**
	 * Informar LocalAula
	 * 
	 * @param entrada
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void informarLocalAula(Scanner entrada) throws NumberFormatException, Exception {

		listarLocalAulas();

		ViewConsoleUtil.setMensagemOpcao("Por favor informe o bloco do local Aula correspondente");
		String bloco = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		String regex = "\\d+";
		if (!bloco.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe um Local Aula válido.");
			informarLocalAula(entrada);
		}

		LocalAulaDao localAulaDao = new LocalAulaDao();
		reserva.setLocalAula(localAulaDao.findById(Integer.parseInt(bloco)));
	}

	/**
	 * Listar LocalAulas
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void listarLocalAulas() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Local Aulas");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o bloco do local Aula de aula");
		String filtro = entrada.nextLine();

		buscarLocalAula(filtro);
	}

	/**
	 * Buscar e listar LocalAula
	 * 
	 * @param filtro
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void buscarLocalAula(String filtro) throws NumberFormatException, Exception {

		LocalAulaDao dao = new LocalAulaDao();
		List<LocalAula> resultado = dao.findByBloco(filtro);
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Bloco");
			for (LocalAula localAula : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(localAula.getId()), localAula.getBloco());
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " local Aula encontrados.");
			System.out.println("");
		}
	}

	/**
	 * Cadastrar reserva de
	 * 
	 * @param tombo
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void cadastrarReserva(Reserva reserva) throws NumberFormatException, Exception {
		ReservaDao dao = new ReservaDao();
		dao.salvar(reserva);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar reserva de
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void listarReservas() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Reserva");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o código da turma");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar reserva de
	 * 
	 * @param filtro
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void buscar(String filtro) throws NumberFormatException, Exception {

		ReservaDao dao = new ReservaDao();
		List<Reserva> resultado = dao.findByTurma(filtro);

		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Usa Projetor", "Data", "Turma", "Local Aula", "Operação");
			System.out.println("");
			for (Reserva reserva : resultado) {
				ViewConsoleUtil.setTabelaItem(String.valueOf(reserva.getId()), reserva.isUsaProjetor() ? "Sim" : "Não",
						String.valueOf(reserva.getData()),
						reserva.getTurma() != null ? reserva.getTurma().getCodigo() : "",
						reserva.getLocalAula() != null ? reserva.getLocalAula().getBloco() : "",
						"R - Remover / B - Buscar / V - Voltar");
			}
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " reserva de s encontrados.");
			System.out.println("");

		}

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setMensagemOpcao("Por favor informe a operação desejada");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();
		else {
			ViewConsoleUtil.setMensagemErro("A opção informada não pode ser um número");
			buscar(filtro);
		}

		switch (operacao) {
		case "R":
			preRemover();
			break;
		case "B":
			listarReservas();
			break;
		case "V":
			selecionarOperacao();
			break;
		default:
			ViewConsoleUtil.setMensagemErro("Opção selecionada inexistente");
			buscar(filtro);
			break;
		}
	}

	/**
	 * Pre remover reserva de
	 * 
	 * @throws Exception
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da reserva a qual você deseja remover");
		String idReserva = entrada.nextLine();

		String regex = "\\d+";
		if (!idReserva.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
			preRemover();
		} else
			remover(Integer.parseInt(idReserva));
	}

	/**
	 * Remover Reserva de
	 * 
	 * @param id
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void remover(int id) throws NumberFormatException, Exception {

		ReservaDao dao = new ReservaDao();
		Reserva objRemovido = dao.findById(id);
		if (objRemovido == null) {
			ViewConsoleUtil.setMensagemErro("O registro informado " + id + " não foi encontrado.");
			preRemover();
		}

		dao.remover(new Reserva(id));
		ViewConsoleUtil.setMensagemOperacao("Reserva removida com sucesso");
		selecionarOperacao();
	}

	/**
	 * Voltar para aréa administrativa
	 * 
	 * @throws Exception
	 */
	private void voltar() throws Exception {

		MenuController menu = new MenuController();
		menu.getTelaMenu();

	}

}
