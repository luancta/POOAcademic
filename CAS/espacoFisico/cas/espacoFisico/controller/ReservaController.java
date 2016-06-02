package cas.espacoFisico.controller;

import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.ReservaDao;
import cas.espacoFisico.dominio.Reserva;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Reserva de 
 * 
 * @author Gilley
 *
 */
public class ReservaController {

	/**
	 * Selecionar operação a ser realizada
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
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void preCadastrarReserva() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);
		Reserva reserva = new Reserva();

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Reserva");
		ViewConsoleUtil.setDivisor();

		//DO METODOS PARA INFORMAR

		cadastrarReserva(reserva);
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
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void listarReservas() throws NumberFormatException, Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Reserva de ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o número do reserva de ");
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
		Reserva resultado = dao.findById( Integer.parseInt(filtro) );

		ViewConsoleUtil.setDivisor();

/*		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "bloco", "capacidade", "sala", "laboratório", "Operação");
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(resultado.getId()), resultado.getBloco(),
						String.valueOf(resultado.getCapacidade()),
						resultado.getSala() != null ? resultado.getSala().getNumero() : "",
								resultado.getLaboratorio() != null ? resultado.getLaboratorio().getNome() : "",
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
		}*/

	}

	/**
	 * Pre remover reserva de 
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do reserva de  que você deseja remover");
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
		ViewConsoleUtil.setMensagemOperacao("Reserva de  removido com sucesso");
		selecionarOperacao();
	}

	/**
	 * Voltar para aréa administrativa
	 * @throws Exception 
	 */
	private void voltar() throws Exception {

		MenuController menu = new MenuController();
		menu.getTelaMenu();

	}

}
