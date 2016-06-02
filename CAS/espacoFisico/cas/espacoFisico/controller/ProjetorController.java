package cas.espacoFisico.controller;

import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.ProjetorDao;
import cas.espacoFisico.dominio.Marca;
import cas.espacoFisico.dominio.Projetor;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Projetor de Aula
 * 
 * @author Gilley
 *
 */
public class ProjetorController {

	/**
	 * Selecionar operação a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Projetor de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um novo projetor");
		ViewConsoleUtil.setOpcao(2, "Listar projetores existentes");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarProjetor();
			break;
		case "2":
			listarProjetors();
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
	 * Pre cadastrar projetor de aula
	 * @throws Exception 
	 */
	private void preCadastrarProjetor() throws Exception {

		Scanner entrada = new Scanner(System.in);
		Projetor projetor = new Projetor();

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Projetor de Aula");
		ViewConsoleUtil.setDivisor();

		informarTombo(entrada, projetor);
		informarMarca(entrada, projetor);

		cadastrarProjetor(projetor);
	}

	/**
	 * Informar tombo projetor
	 * 
	 * @param entrada
	 * @param projetor
	 */
	private void informarTombo(Scanner entrada, Projetor projetor) {

		ViewConsoleUtil.setMensagemOpcao("Por favor informe o número da tombo correspondente");
		String tombo = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (tombo.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Número: campo obrigatório não informado.");
			informarTombo(entrada, projetor);
		}
		projetor.setTombo(tombo);
	}

	/**
	 * INformar Marca do projetor
	 * 
	 * @param entrada
	 * @param projetor
	 */
	private void informarMarca(Scanner entrada, Projetor projetor) {

		ViewConsoleUtil.setMensagemOpcao("Por favor informe o número da marca correspondente");
		for (Marca marca : Marca.values())
			ViewConsoleUtil.setOpcao(marca.ordinal(), marca.name());

		String marca = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		String regex = "\\d+";
		if (!marca.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe uma marca válida.");
			informarMarca(entrada, projetor);
		}

		projetor.setMarca(Marca.getMarca(Integer.parseInt(marca)));
	}

	/**
	 * Cadastrar projetor de aula
	 * 
	 * @param tombo
	 * @throws Exception 
	 */
	private void cadastrarProjetor(Projetor projetor) throws Exception {
		ProjetorDao dao = new ProjetorDao();
		dao.salvar(projetor);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar projetor de aula
	 * @throws Exception 
	 */
	private void listarProjetors() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Projetor de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o número da projetor de aula");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar projetor de aula
	 * 
	 * @param tombo
	 * @throws Exception 
	 */
	private void buscar(String filtro) throws Exception {

		ProjetorDao dao = new ProjetorDao();
		List<Projetor> resultado = dao.findByTombo(filtro);

		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Tombo", "Marca", "Operação");
			for (Projetor projetor : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(projetor.getId()), projetor.getTombo(),
						projetor.getMarca().name(), "R - Remover / B - Buscar / V - Voltar");
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " projetores encontrados.");
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
			listarProjetors();
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
	 * Pre remover projetor de aula
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da projetor de aula que você deseja remover");
		String idProjetor = entrada.nextLine();

		String regex = "\\d+";
		if (!idProjetor.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
			preRemover();
		} else
			remover(Integer.parseInt(idProjetor));
	}

	/**
	 * Remover Projetor de aula
	 * 
	 * @param id
	 * @throws Exception 
	 */
	private void remover(int id) throws Exception {

		ProjetorDao dao = new ProjetorDao();
		Projetor objRemovido = dao.findById(id);
		if (objRemovido == null) {
			ViewConsoleUtil.setMensagemErro("O registro informado " + id + " não foi encontrado.");
			preRemover();
		}

		dao.remover(new Projetor(id));
		ViewConsoleUtil.setMensagemOperacao("Projetor de aula removido com sucesso");
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
