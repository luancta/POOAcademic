package cas.espacoFisico.controller;

import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.AplicativoDao;
import cas.espacoFisico.dominio.Aplicativo;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Aplicativo de Aula
 * 
 * @author Gilley
 *
 */
public class AplicativoController {

	/**
	 * Selecionar operação a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Aplicativo de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um novo aplicativo");
		ViewConsoleUtil.setOpcao(2, "Listar aplicativo(s) existente(s)");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarAplicativo();
			break;
		case "2":
			listarAplicativos();
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
	 * Pre cadastrar aplicativo de aula
	 * @throws Exception 
	 */
	private void preCadastrarAplicativo() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Aplicativo");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o nome do aplicativo");
		String nome = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!nome.isEmpty())
			cadastrarAplicativo(nome);
		else {
			ViewConsoleUtil.setMensagemErro("Número: Campo obrigatório não informado.");
			preCadastrarAplicativo();
		}

	}

	/**
	 * Cadastrar aplicativo de aula
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void cadastrarAplicativo(String nome) throws Exception {
		AplicativoDao dao = new AplicativoDao();
		dao.salvar(new Aplicativo(nome));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar aplicativo de aula
	 * @throws Exception 
	 */
	private void listarAplicativos() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Aplicativo");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o nome do aplicativo");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar aplicativo de aula
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void buscar(String filtro) throws Exception {

		AplicativoDao dao = new AplicativoDao();
		List<Aplicativo> resultado = dao.findByNome(filtro);
		
		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Nome", "Operação");
			for (Aplicativo aplicativo : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(aplicativo.getId()), aplicativo.getNome(),
						"R - Remover / B - Buscar / V - Voltar");
			}
			
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " aplicativos encontrados.");
			System.out.println("");
		}

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setMensagemOpcao("Por favor informe a operação desejada");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();
		else{
			ViewConsoleUtil.setMensagemErro("A opção informada não pode ser um número");
			buscar(filtro);
		}

		switch (operacao) {
		case "R":
			preRemover();
			break;
		case "B":
			listarAplicativos();
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
	 * Pre remover aplicativo de aula
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do aplicativo que você deseja remover");
		String idAplicativo = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idAplicativo.matches(regex)){
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
			preRemover();
		}else
			remover(Integer.parseInt(idAplicativo));
	}

	/**
	 * Remover Aplicativo de aula
	 * 
	 * @param id
	 * @throws Exception 
	 */
	private void remover(int id) throws Exception {

		AplicativoDao dao = new AplicativoDao();
		Aplicativo objRemovido = dao.findById(id);
		if(objRemovido == null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" não foi encontrado.");
			preRemover();
		}
		dao.remover(new Aplicativo(id));
		ViewConsoleUtil.setMensagemOperacao("Aplicativo removido com sucesso");
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
