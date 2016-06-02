package cas.acesso.controller;

import java.util.Scanner;

import cas.ensino.controller.EstudanteController;
import cas.espacoFisico.controller.AplicativoController;
import cas.espacoFisico.controller.LaboratorioController;
import cas.espacoFisico.controller.LocalAulaController;
import cas.espacoFisico.controller.ProjetorController;
import cas.espacoFisico.controller.SalaController;

public class MenuController {
	
	public void getTelaMenu () throws Exception {
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Menu Principal #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Gerenciar Usuário");
		System.out.println("2 - Gerenciar Sala de Aula");
		System.out.println("3 - Gerenciar Turma");
		System.out.println("4 - Gerenciar Projetor");
		System.out.println("5 - Gerenciar Aplicativo");
		System.out.println("6 - Gerenciar Laboratório");
		System.out.println("7 - Gerenciar Local Aula");
		System.out.println("8 - Gerenciar Estudante");
		
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
		case "5":
			AplicativoController aplicativoController = new AplicativoController();
			aplicativoController.selecionarOperacao();
			break;
		case "6":
			LaboratorioController laboratorioController = new LaboratorioController();
			laboratorioController.selecionarOperacao();
			break;
		case "7":
			LocalAulaController localAulaController = new LocalAulaController();
			localAulaController.selecionarOperacao();
			break;
		case "8":
			EstudanteController estudanteController = new EstudanteController();
			estudanteController.selecionarOperacao();
			break;
		default: System.out.println("Opção selecionada inexistente"); 
				 getTelaMenu();
				 break;
		}

	}
}
