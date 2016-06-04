package cas.acesso.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import cas.acesso.dao.UsuarioDao;
import cas.acesso.dominio.TipoUsuario;
import cas.acesso.dominio.Usuario;
import cas.comum.controller.PessoaController;
import cas.util.util.ViewConsoleUtil;

public class UsuarioController {

	/**
	 * Selecionar opera��o a ser realizada
	 * @throws Exception 
	 */
	public void selecionarOperacao() throws Exception{
		
		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		ViewConsoleUtil.setBreadCrumb("Gerenciar Usu�rio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor digite uma op��o desejada:");
		ViewConsoleUtil.setOpcao(1, "Cadastrar uma novo usu�rio.;");
		ViewConsoleUtil.setOpcao(2, "Listar usu�rio(s) existente(s).;");
		ViewConsoleUtil.setOpcao(3, "Remover usu�rio(s) existente(s).;");
		ViewConsoleUtil.setOpcao(9, "<< Voltar");
		ViewConsoleUtil.setDivisor();
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":preCadastrarUsuario(); break;
		case "2":listarUsuario(); break;
		case "9":
			voltar();
			break;
		default: System.out.println("Op��o selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar usu�rio
	 * @throws Exception 
	 */
	private void preCadastrarUsuario() throws Exception{
		Scanner entrada = new Scanner(System.in);
		
		PessoaController pessoaController = new PessoaController();
		Integer idPessoa = pessoaController.cadastrarPessoa();
		if(idPessoa == null){
			preCadastrarUsuario();
		}
		
		System.out.println("Por favor digite o LOGIN:");
		String login = entrada.nextLine();
		System.out.println("Por favor digite o SENHA:");
		String senha = entrada.nextLine();
		System.out.println("Por favor digite uma op��o desejada:");
		System.out.println("1 - Usu�rio Administrador");
		System.out.println("2 - Usu�rio Coordenandor");
		System.out.println("3 - Usu�rio Secret�rio");
		String tipoUsuario = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		Integer numeroTipoUsuario = null;
		if(login.isEmpty()){
			System.out.println("\n**Login: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(senha.isEmpty()){
			System.out.println("\n**Senha: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(tipoUsuario.isEmpty()){
			System.out.println("\n**Tipo Usu�rio: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		else{
			numeroTipoUsuario = Integer.parseInt(tipoUsuario);
		}

		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.cadastrarUsuario(login, senha, numeroTipoUsuario, idPessoa);
		ViewConsoleUtil.setMensagemOperacao("Cadastro realizado com sucesso");
		selecionarOperacao();
	}
	

	
	/**
	 * listar sala de aula
	 * @throws Exception 
	 */
	private void listarUsuario() throws Exception{
		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Usu�rio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o login do usu�rio:");
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
	
	private void buscar(String filtro) throws Exception {

		UsuarioDao dao = new UsuarioDao();
		List<Usuario> resultado = dao.findByLogin(filtro);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();

		if (resultado.isEmpty()) {
			ViewConsoleUtil.setMensagemErro("Nenhum registro foi encontrado");
			selecionarOperacao();
		} else {
			ViewConsoleUtil.setTabelaHead("id", "Login", "Tipo de Usu�rio");
			for (Usuario usuario : resultado) {
				System.out.println("");
				ViewConsoleUtil.setTabelaItem(String.valueOf(usuario.getId()), usuario.getLogin(), String.valueOf(TipoUsuario.get(usuario.getTipoUsuario())),
						"R - Remover/  B - Buscar / V - Voltar");
			}

			System.out.println("");
			System.out.println("Total de " + resultado.size() + " usu�rios encontrados.");
			System.out.println("");
		}

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setMensagemOpcao("Por favor informe a opera��o desejada");
		String operacao = entrada.nextLine();

		String regex = "\\d+";
		if (!operacao.matches(regex))
			operacao = operacao.toUpperCase();
		else {
			ViewConsoleUtil.setMensagemErro("A op��o informada n�o pode ser um n�mero");
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
			ViewConsoleUtil.setMensagemErro("Op��o selecionada inexistente");
			buscar(filtro);
			break;
		}

	}
	
	private void listarUsuarios() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.limparConsole();
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setBreadCrumb("Listar Usu�rio");
		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o login do usu�rio");
		String filtro = entrada.nextLine();

		buscar(filtro);
	}
	
	/**
	 * Pre remover usuario de aula
	 * @throws Exception 
	 */
	private void preRemover() throws Exception {

		Scanner entrada = new Scanner(System.in);

		ViewConsoleUtil.setDivisor();
		ViewConsoleUtil.setMensagemOpcao("Por favor informe o id do usu�rio que voc� deseja remover");
		String idUsuario = entrada.nextLine();

		String regex = "\\d+";
		if (!idUsuario.matches(regex)) {
			ViewConsoleUtil.setMensagemErro("A op��o informada deve ser um id");
			preRemover();
		} else
			remover(Integer.parseInt(idUsuario));
	}
	
	private void remover(int id) throws Exception {

		UsuarioDao dao = new UsuarioDao();
		Usuario objRemovido = dao.findById(id);
		if (objRemovido == null) {
			ViewConsoleUtil.setMensagemErro("O registro informado " + id + " n�o foi encontrado.");
			preRemover();
		}
		dao.remover(new Usuario(id));
		ViewConsoleUtil.setMensagemOperacao("Usu�rio removido com sucesso");
		selecionarOperacao();
	}
	
	/**
	 * Voltar para ar�a administrativa
	 */
	private void voltar() throws Exception {

		MenuController menu = new MenuController();
		menu.getTelaMenu();

	}
	

}
