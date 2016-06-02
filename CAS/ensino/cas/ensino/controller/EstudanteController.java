package cas.ensino.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import cas.comum.controller.PessoaController;
import cas.ensino.dao.EstudanteDao;
import cas.ensino.dominio.Estudante;
import cas.util.util.ViewConsoleUtil;

public class EstudanteController {

	/**
	 * Selecionar operação a ser realizada
	 * @throws ParseException 
	 */
	public void selecionarOperacao() throws ParseException{
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Estudante #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Cadastrar um novo estudante.;");
		System.out.println("2 - Listar estudante(s) existente(s).;");
		System.out.println("3 - Remover estudante(s) existente(s).;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":preCadastrarEstudante(); break;
		case "2":listarEstudante(); break;
		case "9":voltar(); break;
		default: System.out.println("Opção selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar usuário
	 * @throws ParseException 
	 */
	private void preCadastrarEstudante() throws ParseException{
		PessoaController pessoaController = new PessoaController();
		Integer idPessoa = pessoaController.cadastrarPessoa();
		if(idPessoa == null){
			preCadastrarEstudante();
		}
		Scanner entrada = new Scanner(System.in);
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite a matrícula:");
		String matricula = entrada.nextLine();
		System.out.println("Por favor digite o curso:");
		String curso = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		if(matricula.isEmpty()){
			System.out.println("\n**Matrícula: campo obrigatório não informado. \n");
			preCadastrarEstudante();
		}
		if(curso.isEmpty()){
			System.out.println("\n**Senha: campo obrigatório não informado. \n");
			preCadastrarEstudante();
		}

		EstudanteDao estudanteDao = new EstudanteDao();
		estudanteDao.cadastrarEstudante(matricula, curso, idPessoa);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}
	

	
	/**
	 * listar estudante
	 */
	private void listarEstudante(){
		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Estudante");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe a matricula do estudante:");
		String filtro = entrada.nextLine();

		try {
			buscar(filtro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void buscar(String filtro) throws IOException, ParseException {

		EstudanteDao dao = new EstudanteDao();
		List<Estudante> resultado = dao.findByMatricula(filtro);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Login", "Tipo de Usuário");
			for (Estudante estudante : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(estudante.getId()), estudante.getMatricula(), estudante.getCurso(), estudante.getPessoa().getNome(),
						"R - Remover/  B - Buscar / V - Voltar");
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " usuários encontrados.");
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
			listarUsuarios();
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
	
	private void listarUsuarios() throws IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Usuário");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o login do usuário");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}
	
	/**
	 * Pre remover usuario de aula
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void preRemover() throws NumberFormatException, IOException, ParseException {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do estudante que você deseja remover");
		String idEstudante = entrada.nextLine();

		String regex = "\\d+";
		if (!idEstudante.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("A opção informada deve ser um id");
			preRemover();
		} else
			remover(Integer.parseInt(idEstudante));
	}
	
	private void remover(int id) throws IOException, ParseException {

		EstudanteDao dao = new EstudanteDao();
		Estudante objRemovido = dao.findById(id);
		if (objRemovido == null) {
			ViewConsoleUtil.setMensagemErro("O registro informado " + id + " não foi encontrado.");
			preRemover();
		}
		dao.remover(new Estudante(id));
		ViewConsoleUtil.setMensagemOperacao("Usuário removido com sucesso");
		selecionarOperacao();
	}
	
	/**
	 * Voltar para aréa administrativa
	 */
	private void voltar(){
		//TODO AINDA VAI SER CRIADO
	}
	

}
