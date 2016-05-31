package cas.acesso.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import cas.espacoFisico.controller.ProjetorController;
import cas.espacoFisico.controller.SalaController;

public class MenuController {
	
	public void getTelaMenu () throws ParseException, IOException {
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Menu Principal #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Gerenciar Usuário");
		System.out.println("2 - Gerenciar Sala de Aula");
		System.out.println("3 - Gerenciar Turma");
		System.out.println("4 - Gerenciar Projetor");
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":
			UsuarioController usuarioController = new UsuarioController();
			usuarioController.selecionarOperacao();
			break;
		case "2":
			SalaController salaController = new SalaController();
			salaController.selecionarOperacao();
			break;
		case "4":
			ProjetorController projetorController = new ProjetorController();
			projetorController.selecionarOperacao();
			break;
		default: System.out.println("Opção selecionada inexistente"); 
				 getTelaMenu();
				 break;
		}

	}
}
