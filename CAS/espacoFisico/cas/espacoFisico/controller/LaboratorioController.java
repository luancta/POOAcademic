package cas.espacoFisico.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.LaboratorioDao;
import cas.espacoFisico.dominio.Laboratorio;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Laboratorio 
 * 
 * @author Gilley
 *
 */
public class LaboratorioController {

	/**
	 * Selecionar operação a ser realizada
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void selecionarOperacao() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Laboratorio ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um novo laboratorio");
		ViewConsoleUtil.setOpcao(2, "Listar laboratorio(s) existente(s)");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarLaboratorio();
			break;
		case "2":
			listarLaboratorios();
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
	 * Pre cadastrar laboratorio de aula
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preCadastrarLaboratorio() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Laboratorio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome do laboratorio");
		String nome = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!nome.isEmpty())
			cadastrarLaboratorio(nome);
		else {
			ViewConsoleUtil.setMensagemErro("Número: campo obrigatório não informado.");
			preCadastrarLaboratorio();
		}

	}

	/**
	 * Cadastrar laboratorio de aula
	 * 
	 * @param nome
	 * @throws IOException
	 * @throws ParseException
	 */
	private void cadastrarLaboratorio(String nome) throws IOException, ParseException {
		LaboratorioDao dao = new LaboratorioDao();
		dao.salvar(new Laboratorio(nome));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar laboratorio de aula
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void listarLaboratorios() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Laboratorio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome do laboratorio");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar laboratorio de aula
	 * 
	 * @param nome
	 * @throws IOException
	 * @throws ParseException
	 */
	private void buscar(String filtro) throws IOException, ParseException {

		LaboratorioDao dao = new LaboratorioDao();
		List<Laboratorio> resultado = dao.findByNome(filtro);
		
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Nome", "Operação");
			for (Laboratorio laboratorio : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(laboratorio.getId()), laboratorio.getNome(),
						"R - Remover / B - Buscar / V - Voltar");
			}
			
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " laboratorios encontrados.");
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
			listarLaboratorios();
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
	 * Pre remover laboratorio de aula
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preRemover() throws NumberFormatException, IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do laboratorio que você deseja remover");
		String idLaboratorio = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idLaboratorio.matches(regex)){
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um número");
			preRemover();
		}else
			remover(Integer.parseInt(idLaboratorio));
	}

	/**
	 * Remover Laboratorio de aula
	 * 
	 * @param id
	 * @throws IOException
	 * @throws ParseException
	 */
	private void remover(int id) throws IOException, ParseException {

		LaboratorioDao dao = new LaboratorioDao();
		Laboratorio objRemovido = dao.findById(id);
		if(objRemovido == null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" não foi encontrado.");
			preRemover();
		}
		dao.remover(new Laboratorio(id));
		ViewConsoleUtil.setMensagemOperacao("Laboratorio removido com sucesso");
		selecionarOperacao();
	}

	/**
	 * Voltar para aréa administrativa
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void voltar() throws ParseException, IOException {

		MenuController menu = new MenuController();
		menu.getTelaMenu();

	}

}
