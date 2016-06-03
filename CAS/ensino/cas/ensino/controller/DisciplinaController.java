package cas.ensino.controller;

import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.ensino.dao.DisciplinaDao;
import cas.ensino.dominio.Disciplina;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Disciplina 
 * 
 * @author Luan C.
 *
 */
public class DisciplinaController {

	/**
	 * Selecionar operação a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Disciplina ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar uma nova Disciplina");
		ViewConsoleUtil.setOpcao(2, "Listar Disciplina(s) existente(s)");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarDisciplina();
			break;
		case "2":
			listarDisciplinas();
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
	 * Pre cadastrar Disciplina
	 * @throws Exception 
	 */
	private void preCadastrarDisciplina() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Disciplina");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome da Disciplina");
		String nome = entrada.nextLine();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite a SIGLA da disciplina");
		String sigla = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (nome.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nome: campo obrigatório não informado.");
			preCadastrarDisciplina();
		} else if (sigla.isEmpty()){
			ViewConsoleUtil.setMensagemErro("SIGLA: campo obrigatório não informado.");
			preCadastrarDisciplina();
		} else {
			cadastrarDisciplina(nome, sigla);
		}

	}

	/**
	 * Cadastrar Disciplina
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void cadastrarDisciplina(String nome, String sigla) throws Exception {
		DisciplinaDao dao = new DisciplinaDao();
		dao.salvar(new Disciplina(nome,sigla));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar Disciplina
	 * @throws Exception 
	 */
	private void listarDisciplinas() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Disciplina");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome da disciplina");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar Disciplina
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void buscar(String filtro) throws Exception {

		DisciplinaDao dao = new DisciplinaDao();
		List<Disciplina> resultado = dao.findByNomeSigla(filtro);
		
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Nome", "SIGLA", "Operação");
			for (Disciplina disciplina : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(disciplina.getId()), disciplina.getNome(), disciplina.getSigla(),
						"R - Remover / B - Buscar / V - Voltar");
			}
			
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " disciplinas encontradas.");
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
			listarDisciplinas();
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
	 * Pre remover Disciplina
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da disciplina que você deseja remover");
		String idDisciplina = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idDisciplina.matches(regex)){
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um número");
			preRemover();
		}else
			remover(Integer.parseInt(idDisciplina));
	}

	/**
	 * Remover Disciplina
	 * 
	 * @param id
	 * @throws Exception 
	 */
	private void remover(int id) throws Exception {

		DisciplinaDao dao = new DisciplinaDao();
		Disciplina objRemovido = dao.findById(id);
		if(objRemovido == null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" não foi encontrado.");
			preRemover();
		}
		dao.remover(new Disciplina(id));
		ViewConsoleUtil.setMensagemOperacao("Disciplina removida com sucesso");
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
