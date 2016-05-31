package cas.acesso.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import cas.acesso.dao.UsuarioDao;
import cas.comum.dao.PessoaDao;
import cas.espacoFisico.dao.SalaDao;

public class UsuarioController {

	/**
	 * Selecionar opera��o a ser realizada
	 * @throws ParseException 
	 */
	public void selecionarOperacao() throws ParseException{
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Usu�rio #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma op��o desejada:");
		System.out.println("1 - Cadastrar um novo usu�rio.;");
		System.out.println("2 - Listar usu�rio(s) existente(s).;");
		System.out.println("3 - Remover usu�rio(s) existente(s).;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":preCadastrarUsuario(); break;
		case "2":listarSalas(); break;
		case "9":voltar(); break;
		default: System.out.println("Op��o selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar usu�rio
	 * @throws ParseException 
	 */
	private void preCadastrarUsuario() throws ParseException{
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Usu�rio #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite o nome da PESSOA:");
		String nome = entrada.nextLine();
		System.out.println("Por favor digite a DATA DE NASCIMENTO:");
		String dataNascimento = entrada.nextLine();
		System.out.println("Por favor digite o CPF:");
		String cpf = entrada.nextLine();
		System.out.println("Por favor digite o RG:");
		String rg = entrada.nextLine();
		System.out.println("Por favor digite o NOME DA M�E:");
		String nomeMae = entrada.nextLine();
		System.out.println("Por favor digite o NOME DO PAI:");
		String nomePai = entrada.nextLine();
		System.out.println("Por favor digite o ENDERE�O:");
		String endereco = entrada.nextLine();
		System.out.println("Por favor digite o BAIRRO:");
		String bairro = entrada.nextLine();
		System.out.println("Por favor digite o CEP:");
		String cep = entrada.nextLine();
		System.out.println("---------------------------------------");
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
		
		Date dataDataNascimento = null;
		Integer numeroTipoUsuario = null;
		if(nome.isEmpty()){
			System.out.println("\n**Nome: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(dataNascimento.isEmpty()){
			System.out.println("\n**Data de Nascimento: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		else{
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			dataDataNascimento = formato.parse(dataNascimento);
		}
		if(cpf.isEmpty()){
			System.out.println("\n**CPF: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(rg.isEmpty()){
			System.out.println("\n**RG: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(nomeMae.isEmpty()){
			System.out.println("\n**Nome M�e: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(nomePai.isEmpty()){
			System.out.println("\n**Nome Pai: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(endereco.isEmpty()){
			System.out.println("\n**Endere�o: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(bairro.isEmpty()){
			System.out.println("\n**Bairro: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
		if(cep.isEmpty()){
			System.out.println("\n**CEP: campo obrigat�rio n�o informado. \n");
			preCadastrarUsuario();
		}
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
		PessoaDao pessoaDao = new PessoaDao();
		pessoaDao.cadastrarPessoa(nome, dataDataNascimento, cpf, rg, nomeMae, nomePai, endereco, bairro, cep, login, senha, numeroTipoUsuario);
		Integer idPessoa = pessoaDao.findUltimoRegistroCadastrado();
		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.cadastrarUsuario(login, senha, numeroTipoUsuario, idPessoa);
	}
	

	
	/**
	 * listar sala de aula
	 */
	private void listarSalas(){
		
	}
	
	/**
	 * Voltar para ar�a administrativa
	 */
	private void voltar(){
		//TODO AINDA VAI SER CRIADO
	}
	

}
