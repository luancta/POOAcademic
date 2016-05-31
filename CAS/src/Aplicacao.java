import java.util.Scanner;

import cas.acesso.controller.AutenticacaoController;
import cas.util.util.BancoUtil;
import cas.util.util.ConexaoUtil;

public class Aplicacao {

	public static void main(String[] args) {
		
		ConexaoUtil con = new ConexaoUtil();
        con.setBanco("CasDB");
		boolean existeBD = false;
		 existeBD = con.verificarBanco();
        if(!existeBD){ 
			ConexaoUtil.createDB();
	        BancoUtil bd = new BancoUtil();
	        bd.CriarTabelas();
        }
		
		try {			
			AutenticacaoController autenticacao = new AutenticacaoController();
			autenticacao.getTelaInicialSistema();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
	}
}
