package cas.ensino.controller;

import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.ensino.dao.TurmaDao;
import cas.ensino.dominio.Turma;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Turma 
 * 
 * @author Gilley
 *
 */
public class TurmaController {

	/**
	 * Selecionar operação a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Turma ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar uma nova turma");
		ViewConsoleUtil.setOpcao(2, "Listar turma(s) existente(s)");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarTurma();
			break;
		case "2":
			listarTurmas();
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
	 * Pre cadastrar turma 
	 * @throws Exception 
	 */
	private void preCadastrarTurma() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Turma ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o código da turma");
		String codigo = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!codigo.isEmpty())
			cadastrarTurma(codigo);
		else {
			ViewConsoleUtil.setMensagemErro("Código: Campo obrigatório não informado.");
			preCadastrarTurma();
		}

	}

	/**
	 * Cadastrar turma 
	 * 
	 * @param codigo
	 * @throws Exception 
	 */
	private void cadastrarTurma(String codigo) throws Exception {
		TurmaDao dao = new TurmaDao();
		dao.salvar(new Turma(codigo));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar turma 
	 * @throws Exception 
	 */
	private void listarTurmas() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Turma ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o código da turma  para realizar a busca");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar turma 
	 * 
	 * @param codigo
	 * @throws Exception 
	 */
	private void buscar(String filtro) throws Exception {

		TurmaDao dao = new TurmaDao();
		List<Turma> resultado = dao.findByCodigo(filtro);
		
		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Código", "Operação");
			for (Turma turma : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(turma.getId()), turma.getCodigo(),
						"R - Remover / B - Buscar / V - Voltar");
			}
			
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " turmas encontradas.");
			System.out.println("");
		}

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setMensagemOpcao("Por favor informe a operação desejada");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();
		else{
			ViewConsoleUtil.setMensagemErro("Opção selecionada inexistente");
			buscar(filtro);
		}

		switch (operacao) {
		case "R":
			preRemover();
			break;
		case "B":
			listarTurmas();
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
	 * Pre remover turma 
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da turma que você deseja remover");
		String idTurma = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idTurma.matches(regex)){
			ViewConsoleUtil.setDivisor();
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
			preRemover();
		}else
			remover(Integer.parseInt(idTurma));
	}

	/**
	 * Remover Turma 
	 * 
	 * @param id
	 * @throws Exception 
	 */
	private void remover(int id) throws Exception {

		TurmaDao dao = new TurmaDao();
		Turma objRemovido = dao.findById(id);
		if(objRemovido.getId() == 0){
			ViewConsoleUtil.setDivisor();
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" não foi encontrado.");
			preRemover();
		}
		dao.remover(new Turma(id));
		ViewConsoleUtil.setMensagemOperacao("Turma  removida com sucesso");
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
