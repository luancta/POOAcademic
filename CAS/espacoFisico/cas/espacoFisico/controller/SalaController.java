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
	 * Selecionar operação a ser realizada
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void selecionarOperacao() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Sala de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma opção desejada:");
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
			System.out.println("Opção selecionada inexistente");
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
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Sala de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o número da sala");
		String numero = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!numero.isEmpty())
			cadastrarSala(numero);
		else {
			ViewConsoleUtil.setMensagemErro("Número: Campo obrigatório não informado.");
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
		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Sala de Aula");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o número da sala de aula para realizar a busca");
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
		
		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Número", "Operação");
			for (Sala sala : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(sala.getId()), sala.getNumero(),
						"R - Remover / B - Buscar / V - Voltar");
			}
			
			System.out.println("");
			System.out.println("Total de " + resultado.size() + " salas encontradas.");
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
			listarSalas();
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
	 * Pre remover sala de aula
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preRemover() throws NumberFormatException, IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id da sala de aula que você deseja remover");
		String idSala = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idSala.matches(regex)){
			ViewConsoleUtil.setDivisor();
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
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
		if(objRemovido.getId() == 0){
			ViewConsoleUtil.setDivisor();
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" não foi encontrado.");
			preRemover();
		}
		dao.remover(new Sala(id));
		ViewConsoleUtil.setMensagemOperacao("Sala de aula removida com sucesso");
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
