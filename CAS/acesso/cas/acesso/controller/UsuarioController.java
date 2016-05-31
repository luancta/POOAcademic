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
	 * Selecionar operação a ser realizada
	 * @throws ParseException 
	 */
	public void selecionarOperacao() throws ParseException{
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Gerenciar Usuário #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Cadastrar um novo usuário.;");
		System.out.println("2 - Listar usuário(s) existente(s).;");
		System.out.println("3 - Remover usuário(s) existente(s).;");
		System.out.println("9 - << Voltar.;");
		System.out.println("---------------------------------------");
		
		String operacao = entrada.nextLine();
		
		switch (operacao) {
		case "1":preCadastrarUsuario(); break;
		case "2":listarSalas(); break;
		case "9":voltar(); break;
		default: System.out.println("Opção selecionada inexistente"); 
				 selecionarOperacao();
				 break;
		}
	}
	
	/**
	 * Pre cadastrar usuário
	 * @throws ParseException 
	 */
	private void preCadastrarUsuario() throws ParseException{
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Usuário #######");
		System.out.println("---------------------------------------");
		System.out.println("Por favor digite o nome da PESSOA:");
		String nome = entrada.nextLine();
		System.out.println("Por favor digite a DATA DE NASCIMENTO:");
		String dataNascimento = entrada.nextLine();
		System.out.println("Por favor digite o CPF:");
		String cpf = entrada.nextLine();
		System.out.println("Por favor digite o RG:");
		String rg = entrada.nextLine();
		System.out.println("Por favor digite o NOME DA MÃE:");
		String nomeMae = entrada.nextLine();
		System.out.println("Por favor digite o NOME DO PAI:");
		String nomePai = entrada.nextLine();
		System.out.println("Por favor digite o ENDEREÇO:");
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
		System.out.println("Por favor digite uma opção desejada:");
		System.out.println("1 - Usuário Administrador");
		System.out.println("2 - Usuário Coordenandor");
		System.out.println("3 - Usuário Secretário");
		String tipoUsuario = entrada.nextLine();
		System.out.println("---------------------------------------");
		
		Date dataDataNascimento = null;
		Integer numeroTipoUsuario = null;
		if(nome.isEmpty()){
			System.out.println("\n**Nome: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(dataNascimento.isEmpty()){
			System.out.println("\n**Data de Nascimento: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		else{
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			dataDataNascimento = formato.parse(dataNascimento);
		}
		if(cpf.isEmpty()){
			System.out.println("\n**CPF: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(rg.isEmpty()){
			System.out.println("\n**RG: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(nomeMae.isEmpty()){
			System.out.println("\n**Nome Mãe: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(nomePai.isEmpty()){
			System.out.println("\n**Nome Pai: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(endereco.isEmpty()){
			System.out.println("\n**Endereço: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(bairro.isEmpty()){
			System.out.println("\n**Bairro: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(cep.isEmpty()){
			System.out.println("\n**CEP: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(login.isEmpty()){
			System.out.println("\n**Login: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(senha.isEmpty()){
			System.out.println("\n**Senha: campo obrigatório não informado. \n");
			preCadastrarUsuario();
		}
		if(tipoUsuario.isEmpty()){
			System.out.println("\n**Tipo Usuário: campo obrigatório não informado. \n");
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
	 * Voltar para aréa administrativa
	 */
	private void voltar(){
		//TODO AINDA VAI SER CRIADO
	}
	

}
