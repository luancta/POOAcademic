package cas.espacoFisico.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.SalaDao;
import cas.espacoFisico.dominio.Sala;
import cas.util.util.ViewConsoleUtil;

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
	 * @throws ParseException
	 */
	public void selecionarOperacao() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Sala de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma op��o desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar uma nova sala");
		ViewConsoleUtil.setOpcao(2, "Listar sala(s) existente(s)");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

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
	 * @throws ParseException
	 */
	private void preCadastrarSala() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Sala de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o n�mero da sala");
		String numero = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!numero.isEmpty())
			cadastrarSala(numero);
		else {
			ViewConsoleUtil.setMensagemErro("N�mero: campo obrigat�rio n�o informado.");
			preCadastrarSala();
		}

	}

	/**
	 * Cadastrar sala de aula
	 * 
	 * @param numero
	 * @throws IOException
	 * @throws ParseException
	 */
	private void cadastrarSala(String numero) throws IOException, ParseException {
		SalaDao dao = new SalaDao();
		dao.salvar(new Sala(numero));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
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

		buscar(filtro);
	}

	/**
	 * Buscar e listar sala de aula
	 * 
	 * @param numero
	 * @throws IOException
	 * @throws ParseException
	 */
	private void buscar(String filtro) throws IOException, ParseException {

		SalaDao dao = new SalaDao();
		List<Sala> resultado = dao.findByNumero(filtro);
		
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty())
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
		else {
			ViewConsoleUtil.setTabelaHead("id", "N�mero", "Opera��o");
			for (Sala sala : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(sala.getId()), sala.getNumero(),
						"R - Remover / B - Buscar / V - Voltar");
			}
		}

		System.out.println("");
		System.out.println("Total de " + resultado.size() + " salas encontradas.");
		System.out.println("");

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
			listarSalas();
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
	 * Pre remover sala de aula
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preRemover() throws NumberFormatException, IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da sala de aula que voc� deseja remover");
		String idSala = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idSala.matches(regex)){
			ViewConsoleUtil.setMensagemErro("A op��o informada deve ser um n�mero");
			preRemover();
		}else
			remover(Integer.parseInt(idSala));
	}

	/**
	 * Remover Sala de aula
	 * 
	 * @param id
	 * @throws IOException
	 * @throws ParseException
	 */
	private void remover(int id) throws IOException, ParseException {

		SalaDao dao = new SalaDao();
		Sala objRemovido = dao.findById(id);
		if(objRemovido != null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" n�o foi encontrado.");
			preRemover();
		}
		dao.remover(new Sala(id));
		ViewConsoleUtil.setMensagemOperacao("Sala de aula removida com sucesso");
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
