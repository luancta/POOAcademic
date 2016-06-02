import cas.acesso.controller.AutenticacaoController;
import cas.acesso.controller.MenuController;
import cas.util.util.BancoUtil;
import cas.util.util.ConexaoUtil;
import cas.util.util.ViewConsoleUtil;

public class Aplicacao {

	public static void main(String[] args) throws Exception {

		ConexaoUtil con = new ConexaoUtil();
		con.setBanco("CasDB");
		boolean existeBD = false;
		existeBD = con.verificarBanco();
		if (!existeBD) {
			ConexaoUtil.createDB();
			BancoUtil bd = new BancoUtil();
			bd.CriarTabelas();
		}

		AutenticacaoController autenticacao = new AutenticacaoController();
		MenuController mnenu = new MenuController();
		try {
			autenticacao.getTelaInicialSistema();
		} catch (Exception e) {
			// Após exception não deixa o sistema morrer.
			ViewConsoleUtil.setDivisor();
			ViewConsoleUtil.setBreadCrumb("Erro ao realizar operação");
			ViewConsoleUtil.setDivisor();
			e.printStackTrace();
			ViewConsoleUtil.setDivisor();
			if (autenticacao.isAutenticado())
				mnenu.getTelaMenu();
			else
				autenticacao.getTelaInicialSistema();
		}

	}
}
