package cas.acesso.controller;

import java.util.ArrayList;
import java.util.List;

import cas.acesso.dao.UsuarioDao;
import cas.acesso.dominio.Usuario;

//import cas.comum.gui.MenuFrame;

public class AutenticacaoController {
	
	/**
	 * Realizar autenticação das informações para entrar no sistema
	 * @param login
	 * @param senha
	 */
	public void autenticar(String login, String senha){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		UsuarioDao dao = new UsuarioDao();
		usuarios = dao.findUsuariobyLoginSenha(login, senha);
		if (!usuarios.isEmpty()) {

		}else{

		}
				
	}

}
