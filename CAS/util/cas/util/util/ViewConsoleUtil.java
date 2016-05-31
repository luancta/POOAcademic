package cas.util.util;

/**
 * Utilitario para manipular visualização
 * 
 * @author Gilley
 *
 */
public class ViewConsoleUtil {

	/**
	 * Exibir Bread Crumb
	 * 
	 * @param head
	 */
	public static void setBreadCrumb(String... head) {
		for (int i = 0; i < head.length; i++) {
			if (i == 0)
				System.out.println(head[i]);
			else
				System.out.println(" > " + head[i]);
		}
	}

	/**
	 * Exibir Divisor
	 */
	public static void setDivisor() {
		System.out.println("#####################################################################");
	}

	/**
	 * Exibir Mensagem de opção
	 * 
	 * @param mensagem
	 */
	public static void setMensagemOpcao(String mensagem) {
		System.out.println(mensagem + ":");
	}

	/**
	 * Exibir OPção a ser selecionada
	 * @param numeroOpcao
	 * @param opcao
	 */
	public static void setOpcao(Integer numeroOpcao, String opcao) {
		System.out.println(numeroOpcao + " - " + opcao + ";");
	}

	/**
	 * Exibir mensagem de erro
	 * 
	 * @param erro
	 */
	public static void setMensagemErro(String erro) {
		System.out.println("");
		System.out.println("** " + erro);
		System.out.println("");
	}

	/**
	 * Exibir mensagem de operação realizada
	 * 
	 * @param mensagem
	 */
	public static void setMensagemOperacao(String mensagem) {
		System.out.println("");
		System.out.println("<<<" + mensagem + ">>>");
		System.out.println("");
	}

	/**
	 * Exibir head de tabela
	 * @param mensagem
	 */
	public static void setTabelaHead(String... mensagem) {
		for (int i = 0; i < mensagem.length; i++) {
			if (i == 0)
				System.out.print(mensagem[i]);
			else
				System.out.print(" | " + mensagem[i]);
		}
	}

	/**
	 * Exibir Item de tabela
	 * @param mensagem
	 */
	public static void setTabelaItem(String... mensagem) {
		for (int i = 0; i < mensagem.length; i++) {
			if (i == 0)
				System.out.print(mensagem[i]);
			else
				System.out.print(" | " + mensagem[i]);
		}
	}

	/**
	 * Limpar console
	 */
	public final static void limparConsole() {
		try {
			final String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		}

		catch (final Exception e) {
			// Handle any exceptions.
		}
	}

}
