package cas.acesso.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cas.acesso.dao.UsuarioDao;
import cas.acesso.dominio.Usuario;

//import cas.comum.gui.MenuFrame;

public class AutenticacaoController {
	public void getTelaInicialSistema () throws ParseException, IOException{
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
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public void autenticar(String login, String senha) throws ParseException, IOException{
		List<Usuario> usuarios = new ArrayList<Usuario>();
		UsuarioDao dao = new UsuarioDao();
		usuarios = dao.findUsuariobyLoginSenha(login, senha);
		if (!usuarios.isEmpty()) {
			limpaTela();
			MenuController menuController = new MenuController();
			menuController.getTelaMenu();
		}else{

		}
				
	}
	
    public void limpaTela(){
        for(int i = 0; i <= 20; i++){
              System.out.println('\n');
        }
    }
}
