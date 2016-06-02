package cas.ensino.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import cas.acesso.controller.MenuController;
import cas.comum.dao.PessoaDao;
import cas.comum.dominio.Pessoa;
import cas.ensino.dao.DocenteDao;
import cas.ensino.dominio.Docente;
import cas.util.util.ViewConsoleUtil;

public class DocenteController {
	/**
	 * Selecionar operação a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Gerenciar Docente ");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma opção desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar um novo Docente");
		ViewConsoleUtil.setOpcao(2, "Listar Docente(s) existente(s)");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();

		String operacao = entrada.nextLine();

		switch (operacao) {
		case "1":
			preCadastrarDocente();
			break;
		case "2":
			listarDocentes();
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
	 * Pre cadastrar Docente
	 * @throws Exception 
	 */
	private void preCadastrarDocente() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Cadastrar Docente");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o CPF do Docente");
		String cpf = entrada.nextLine();
		cpf.replaceAll("^\\d*[0-9](\\.\\d*[0-9])?$", "");
		
		PessoaDao pessoadao = new PessoaDao();
		Pessoa pessoa = pessoadao.findPessoaByCpf(cpf);
		
		if(pessoa == null || pessoa.getId() == 0){
			System.out.println("Cadastrar PESSOA para o CPF " + cpf + " :");
			System.out.println("Por favor digite o nome da PESSOA:");
			String nome = entrada.nextLine();
			System.out.println("Por favor digite a DATA DE NASCIMENTO:");
			String dataNascimento = entrada.nextLine();
			Date dtNascimento = null;
			if(!dataNascimento.isEmpty()){
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				dtNascimento = formato.parse(dataNascimento);
			}
			System.out.println("Por favor digite o RG:");
			String rg = entrada.nextLine();
			System.out.println("Por favor digite o NOME DA MÃE:");
			String nomeMae = entrada.nextLine();
			System.out.println("Por favor digite o NOME DO PAI:");
			String nomePai = entrada.nextLine();
			System.out.println("Por favor digite o ENDEREÇO:");
			String endereco = entrada.nextLine();
			System.out.println("Por favor digite o BAIRRO:");
			String bairro = entrada.nextLine();
			System.out.println("Por favor digite o CEP:");
			String cep = entrada.nextLine();
			pessoadao.cadastrarPessoa(nome, dtNascimento, cpf, rg, nomeMae, nomePai, endereco, bairro, cep);
			System.out.println("---------------------------------------");
			
			pessoa = pessoadao.findPessoaByCpf(cpf);
			System.out.println("Cadastro de PESSOA realizado com sucesso!!!");
		}
		
		
		System.out.println("Por favor digite a MATRICULA do DOCENTE:");
		String matricula = entrada.nextLine();
		ViewConsoleUtil.setDivisor();

		if (!matricula.isEmpty() && (pessoa != null && pessoa.getId() > 0))
			cadastrarDocente(matricula,pessoa);
		else {
			ViewConsoleUtil.setMensagemErro("Matricula: campo obrigatório não informado.");
			preCadastrarDocente();
		}

	}

	/**
	 * Cadastrar Docente
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void cadastrarDocente(String matricula, Pessoa pessoa) throws Exception {
		DocenteDao dao = new DocenteDao();
		Docente docente = new Docente();
		docente.setMatricula(matricula);
		docente.setPessoa(pessoa);
		dao.salvar(docente);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}

	/**
	 * Listar Docente
	 * @throws Exception 
	 */
	private void listarDocentes() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setBreadCrumb("Listar Docente");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite o nome do docente ou matricula");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}

	/**
	 * Buscar e listar Docente
	 * 
	 * @param nome
	 * @throws Exception 
	 */
	private void buscar(String filtro) throws Exception {

		DocenteDao dao = new DocenteDao();
		List<Docente> resultado = dao.findByNomeMatricula(filtro);
		
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()){
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		}else {
			ViewConsoleUtil.setTabelaHead("id", "Nome", "Operação");
			for (Docente docente : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(docente.getId()), docente.getMatricula(),
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
			listarDocentes();
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
	 * Pre remover Docente
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do docente que você deseja remover");
		String idDocente = entrada.nextLine();
		
		String regex = "\\d+";
		if (!idDocente.matches(regex)){
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um número");
			preRemover();
		}else
			remover(Integer.parseInt(idDocente));
	}

	/**
	 * Remover Docente
	 * 
	 * @param id
	 * @throws Exception 
	 */
	private void remover(int id) throws Exception {

		DocenteDao dao = new DocenteDao();
		Docente objRemovido = dao.findById(id);
		if(objRemovido == null){
			ViewConsoleUtil.setMensagemErro("O registro informado " + id+" não foi encontrado.");
			preRemover();
		}
		dao.remover(new Docente(id));
		ViewConsoleUtil.setMensagemOperacao("Docente removido com sucesso");
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
