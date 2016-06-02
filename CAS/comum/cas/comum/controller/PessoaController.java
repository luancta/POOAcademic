package cas.comum.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import cas.comum.dao.PessoaDao;
import cas.comum.dominio.Pessoa;

public class PessoaController {
	
	
	/**
	 * Pre cadastrar pessoa
	 * @throws ParseException 
	 */
	public Integer cadastrarPessoa() throws ParseException{
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("####### Cadastrar Pessoa #######");
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
		
		Date dataDataNascimento = null;
		Integer numeroTipoUsuario = null;
		if(nome.isEmpty()){
			System.out.println("\n**Nome: campo obrigatório não informado. \n");
			return null;
		}
		if(dataNascimento.isEmpty()){
			System.out.println("\n**Data de Nascimento: campo obrigatório não informado. \n");
			return null;
		}
		else{
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			dataDataNascimento = formato.parse(dataNascimento);
		}
		if(cpf.isEmpty()){
			System.out.println("\n**CPF: campo obrigatório não informado. \n");
			return null;
		}
		if(rg.isEmpty()){
			System.out.println("\n**RG: campo obrigatório não informado. \n");
			return null;
		}
		if(nomeMae.isEmpty()){
			System.out.println("\n**Nome Mãe: campo obrigatório não informado. \n");
			return null;
		}
		if(nomePai.isEmpty()){
			System.out.println("\n**Nome Pai: campo obrigatório não informado. \n");
			return null;
		}
		if(endereco.isEmpty()){
			System.out.println("\n**Endereço: campo obrigatório não informado. \n");
			return null;
		}
		if(bairro.isEmpty()){
			System.out.println("\n**Bairro: campo obrigatório não informado. \n");
			return null;
		}
		if(cep.isEmpty()){
			System.out.println("\n**CEP: campo obrigatório não informado. \n");
			return null;
		}
		PessoaDao pessoaDao = new PessoaDao();
		pessoaDao.cadastrarPessoa(nome, dataDataNascimento, cpf, rg, nomeMae, nomePai, endereco, bairro, cep);
		Pessoa pessoa = pessoaDao.findPessoaByCpf(cpf);
		return pessoa.getId();
	}
}
