package cas.comum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import cas.acesso.dao.UsuarioDao;
import cas.util.util.GenericDao;

	public class PessoaDao extends GenericDao{
		/**
		 * Cadastrar Pessoa
		 * @param numero
		 * @param tipoUsuario 
		 * @param senha 
		 * @param login 
		 * @param cep 
		 * @param bairro 
		 * @param endereco 
		 * @param nomePai 
		 * @param nomeMae 
		 * @param rg 
		 * @param cpf 
		 * @param dataDataNascimento 
		 */
	public void cadastrarPessoa(String nome, Date dataDataNascimento, String cpf, String rg, String nomeMae, String nomePai, String endereco, String bairro, String cep, String login, String senha, int tipoUsuario){
		PessoaDao dao = new PessoaDao();
		dao.save(" INSERT INTO comum.pessoa (nome, cpf, registro_geral, nome_mae, nome_pai, endereco, bairro, cep, data_nascimento) " + 
		" VALUES (?,?,?,?,?,?,?,?,?) ", nome, cpf, rg, nomeMae, nomePai, endereco, bairro, cep, dataDataNascimento);
	}
	
	/**
	 * Retorna usuario por login
	 * @param desc
	 * @return
	 */
	public Integer findUltimoRegistroCadastrado(){
		Connection con = getConnection();
		
		String sql = "SELECT MAX(id_pessoa) FROM comum.pessoa ";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			
			ResultSet rs = stm.executeQuery();
			
			return rs.getRow();
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
}
