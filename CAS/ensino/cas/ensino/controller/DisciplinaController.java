package cas.ensino.controller;

import java.io.IOException;
import java.text.ParseException;
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
	 * Selecionar opera��o a ser realizada
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void selecionarOperacao() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Disciplina ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma op��o desejada:");
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
			System.out.println("Op��o selecionada inexistente");
			selecionarOperacao();
			break;
		}
	}

	/**
	 * Pre cadastrar Disciplina
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preCadastrarDisciplina() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Laboratorio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome do laboratorio");
		String nome = entrada.nextLine();
		String sigla = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!nome.isEmpty())
			cadastrarDisciplina(nome, sigla);
		else {
			ViewConsoleUtil.setMensagemErro("N�mero: campo obrigat�rio n�o informado.");
			preCadastrarDisciplina();
		}

	}

	/**
	 * Cadastrar Disciplina
	 * 
	 * @param nome
	 * @throws IOException
	 * @throws ParseException
	 */
	private void cadastrarDisciplina(String nome, String sigla) throws IOException, ParseException {
		DisciplinaDao dao = new DisciplinaDao();
		dao.salvar(new Disciplina(nome,sigla));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar Disciplina
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void listarDisciplinas() throws IOException, ParseException {

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
	 * @throws IOException
	 * @throws ParseException
	 */
	private void buscar(String filtro) throws IOException, ParseException {

		DisciplinaDao dao = new DisciplinaDao();
		List<Disciplina> resultado = dao.findByNomeSigla(filtro);
		
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Nome", "Opera��o");
			for (Disciplina disciplina : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(disciplina.getId()), disciplina.getNome(),
						"R - Remover / B - Buscar / V - Voltar");
			}
			
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " disciplinas encontradas.");
			System.out.println("");
		}

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setMensagemOpcao("Por favor informe a opera��o desejada");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();
		else{
			ViewConsoleUtil.setMensagemErro("A op��o informada n�o pode ser um n�mero");
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
			ViewConsoleUtil.setMensagemErro("Op��o selecionada inexistente");
			buscar(filtro);
			break;
		}

	}

	/**
	 * Pre remover Disciplina
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preRemover() throws NumberFormatException, IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da disciplina que voc� deseja remover");
		String idDisciplina = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idDisciplina.matches(regex)){
			ViewConsoleUtil.setMensagemErro("A op��o informada deve ser um n�mero");
			preRemover();
		}else
			remover(Integer.parseInt(idDisciplina));
	}

	/**
	 * Remover Disciplina
	 * 
	 * @param id
	 * @throws IOException
	 * @throws ParseException
	 */
	private void remover(int id) throws IOException, ParseException {

		DisciplinaDao dao = new DisciplinaDao();
		Disciplina objRemovido = dao.findById(id);
		if(objRemovido == null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" n�o foi encontrado.");
			preRemover();
		}
		dao.remover(new Disciplina(id));
		ViewConsoleUtil.setMensagemOperacao("Disciplina removida com sucesso");
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
