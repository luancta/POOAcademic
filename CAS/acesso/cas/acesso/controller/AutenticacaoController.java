package cas.acesso.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cas.acesso.dao.UsuarioDao;
import cas.acesso.dominio.Usuario;
import cas.util.util.ViewConsoleUtil;

//import cas.comum.gui.MenuFrame;

public class AutenticacaoController {
	
	private boolean autenticado = false;
	
	public void getTelaInicialSistema () throws Exception{
		Scanner entrada = new Scanner(System.in);
		Scanner entrada2 = new Scanner(System.in);
		
		System.out.println("####### Bem vindo ao sistema CAS #######");
		System.out.println("---------------------------------------");
		System.out.println("Entrar no Sistema");
		System.out.println("Usuário: "); 
		String login = entrada.nextLine();
		System.out.println("Senha: ");
		String senha = entrada2.nextLine();
		System.out.println("---------------------------------------");
		
		autenticar(login, senha);
	}
	
	/**
	 * Realizar autenticação das informações para entrar no sistema
	 * @param login
	 * @param senha
	 * @throws Exception 
	 */
	public void autenticar(String login, String senha) throws Exception{
		List<Usuario> usuarios = new ArrayList<Usuario>();
		UsuarioDao dao = new UsuarioDao();
		usuarios = dao.findUsuariobyLoginSenha(login, senha);
		if (!usuarios.isEmpty()) {
			autenticado = true;
			limpaTela();
			MenuController menuController = new MenuController();
			menuController.getTelaMenu();
		}else{
			autenticado = false;
			ViewConsoleUtil.limparConsole();
			ViewConsoleUtil.setMensagemErro("Usuário ou senha inválido.");
			getTelaInicialSistema();

		}
				
	}
	
    public void limpaTela(){
        for(int i = 0; i <= 20; i++){
              System.out.println('\n');
        }
    }

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
}
