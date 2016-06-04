package cas.acesso.controller;

import java.util.Scanner;

import cas.comum.controller.TurnoController;
import cas.ensino.controller.DisciplinaController;
import cas.ensino.controller.DocenteController;
import cas.ensino.controller.EstudanteController;
import cas.ensino.controller.HorarioController;
import cas.ensino.controller.TurmaController;
import cas.espacoFisico.controller.AplicativoController;
import cas.espacoFisico.controller.LaboratorioController;
import cas.espacoFisico.controller.LocalAulaController;
import cas.espacoFisico.controller.ProjetorController;
import cas.espacoFisico.controller.ReservaController;
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
		System.out.println("9 - Gerenciar Horário");
		System.out.println("10 - Gerenciar Reserva");
		System.out.println("11 - Gerenciar Disciplina");
		System.out.println("12 - Gerenciar Docente");
		System.out.println("13 - Gerenciar Turno");
		
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
		case "3":
			TurmaController turmaController = new TurmaController();
			turmaController.selecionarOperacao();
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
		case "9":
			HorarioController horarioController = new HorarioController();
			horarioController.selecionarOperacao();
			break;
		case "10":
			ReservaController reservaController = new ReservaController();
			reservaController.selecionarOperacao();
			break;
		case "11":
			DisciplinaController disciplinaController = new DisciplinaController();
			disciplinaController.selecionarOperacao();
			break;
		case "12":
			DocenteController docenteController = new DocenteController();
			docenteController.selecionarOperacao();
			break;
		case "13":
			TurnoController turnoController = new TurnoController();
			turnoController.selecionarOperacao();
			break;
		default: System.out.println("Opção selecionada inexistente"); 
				 getTelaMenu();
				 break;
		}

	}
}
