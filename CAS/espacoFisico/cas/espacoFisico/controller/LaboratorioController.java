package cas.espacoFisico.controller;

import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.espacoFisico.dao.AplicativoDao;
import cas.espacoFisico.dao.LaboratorioAplicativoDao;
import cas.espacoFisico.dao.LaboratorioDao;
import cas.espacoFisico.dominio.Aplicativo;
import cas.espacoFisico.dominio.Laboratorio;
import cas.espacoFisico.dominio.LaboratorioAplicativo;
import cas.util.util.ViewConsoleUtil;

/**
 * Controlador de Laboratorio
 * 
 * @author Gilley
 *
 */
public class LaboratorioController {

	private LaboratorioAplicativo laboratorioAplicativo;

	/**
	 * Selecionar operação a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Laboratorio ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um novo laboratorio");
		ViewConsoleUtil.setOpcao(2, "Listar laboratorio(s) existente(s)");
		ViewConsoleUtil.setOpcao(3, "Adicionar Aplicativos a laboratorio existente");
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
		case "3":
			informarAplicativosLaboratorio();
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
	 * @throws Exception 
	 */
	private void preCadastrarLaboratorio() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Laboratorio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o nome do laboratorio");
		String nome = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!nome.isEmpty())
			cadastrarLaboratorio(nome);
		else {
			ViewConsoleUtil.setMensagemErro("Número: Campo obrigatório não informado.");
			preCadastrarLaboratorio();
		}

	}

	/**
	 * Cadastrar laboratorio de aula
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void cadastrarLaboratorio(String nome) throws Exception {
		LaboratorioDao dao = new LaboratorioDao();
		dao.salvar(new Laboratorio(nome));
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar laboratorio de aula
	 * @throws Exception 
	 */
	private void listarLaboratorios() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Laboratorio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o nome do laboratorio");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar laboratorio de aula
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void buscar(String filtro) throws Exception {

		LaboratorioDao dao = new LaboratorioDao();
		List<Laboratorio> resultado = dao.findByNome(filtro);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Nome", "Operação");
			for (Laboratorio laboratorio : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(laboratorio.getId()), laboratorio.getNome(),
						"R - Remover / A - Associar Aplicativo /  B - Buscar / V - Voltar");
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
		else {
			ViewConsoleUtil.setMensagemErro("A opção informada não pode ser um número");
			buscar(filtro);
		}

		switch (operacao) {
		case "R":
			preRemover();
			break;
		case "A":
			listarAplicativos();

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
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do laboratorio que você deseja remover");
		String idLaboratorio = entrada.nextLine();

		String regex = "\\d+";
		if (!idLaboratorio.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
			preRemover();
		} else
			remover(Integer.parseInt(idLaboratorio));
	}

	/**
	 * Remover Laboratorio de aula
	 * 
	 * @param id
	 * @throws Exception 
	 */
	private void remover(int id) throws Exception {

		LaboratorioDao dao = new LaboratorioDao();
		Laboratorio objRemovido = dao.findById(id);
		if (objRemovido == null) {
			ViewConsoleUtil.setMensagemErro("O registro informado " + id + " não foi encontrado.");
			preRemover();
		}
		dao.remover(new Laboratorio(id));
		ViewConsoleUtil.setMensagemOperacao("Laboratorio removido com sucesso");
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
		ViewConsoleUtil.setBreadCrumb("Listar Aplicativos");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o nome do aplicativo");
		String filtro = entrada.nextLine();

		buscarAplicativo(filtro);
	}

	/**
	 * Buscar e listar aplicativo de aula
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void buscarAplicativo(String filtro) throws Exception {

		AplicativoDao dao = new AplicativoDao();
		List<Aplicativo> resultado = dao.findByNome(filtro);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Nome");
			for (Aplicativo aplicativo : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(aplicativo.getId()), aplicativo.getNome());
			}

			System.out.println("");
			System.out.println("T2otal de " + resultado.size() + " aplicativos encontrados.");
			System.out.println("");
		}
	}

	/**
	 * Informar aplicativos para laboratorio
	 * @throws Exception 
	 */
	private void informarAplicativosLaboratorio() throws Exception {

		laboratorioAplicativo = new LaboratorioAplicativo();
		SelecionarLaboratorio();
		selecionarAplicativo();
		cadastrarAplicativoLaboratorio();

	}

	/**
	 * Selecionar aplicativo
	 * @throws Exception 
	 */
	private void selecionarAplicativo() throws Exception {

		listarAplicativos();
		Scanner entrada = new Scanner(System.in);
		AplicativoDao dao = new AplicativoDao();

		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do aplicativo correspondente");
		String aplicativo = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		String regex = "\\d+";
		if (!aplicativo.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe um aplicativo válido.");
			selecionarAplicativo();
		}

		Aplicativo aplicativoSelecionado = dao.findById(Integer.parseInt(aplicativo));
		if (aplicativoSelecionado.getId() > 0)
			laboratorioAplicativo.setAplicativo(aplicativoSelecionado);
		else
			selecionarAplicativo();

	}

	/**
	 * Selecionar laboratorio para associar a aplicativo
	 * @throws Exception 
	 */
	private void SelecionarLaboratorio() throws Exception {

		LaboratorioDao dao = new LaboratorioDao();
		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Laboratorio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o nome do laboratorio");
		String filtro = entrada.nextLine();

		List<Laboratorio> resultado = dao.findByNome(filtro);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Nome");
			for (Laboratorio laboratorio : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(laboratorio.getId()), laboratorio.getNome());
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " laboratorios encontrados.");
			System.out.println("");
		}

		ViewConsoleUtil.setMensagemOpcao("Por favor informe o laboratorio desejado");
		String idLaboratorio = entrada.nextLine();

		String regex = "\\d+";
		if (!idLaboratorio.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("Por favor informe um laboratório válido.");
			SelecionarLaboratorio();
		}

		Laboratorio laboratorioSelecionado = dao.findById(Integer.parseInt(idLaboratorio));
		if (laboratorioSelecionado.getId() > 0)
			laboratorioAplicativo.setLaboratorio(laboratorioSelecionado);
		else
			SelecionarLaboratorio();

	}

	/**
	 * Cadastrar Aplicativo laboratorio
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void cadastrarAplicativoLaboratorio() throws Exception {
		LaboratorioAplicativoDao dao = new LaboratorioAplicativoDao();
		LaboratorioAplicativo laboratorioAplicativoPersistido = dao.findByLabApl(
				laboratorioAplicativo.getLaboratorio().getId(), laboratorioAplicativo.getAplicativo().getId());

		if (laboratorioAplicativoPersistido.getId() > 0) {
			ViewConsoleUtil.setMensagemErro("O aplicativo informado já está associado ao laboratório.");
			selecionarOperacao();
		}
		dao.salvar(laboratorioAplicativo);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
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

	public LaboratorioAplicativo getLaboratorioAplicativo() {
		return laboratorioAplicativo;
	}

	public void setLaboratorioAplicativo(LaboratorioAplicativo laboratorioAplicativo) {
		this.laboratorioAplicativo = laboratorioAplicativo;
	}

}
