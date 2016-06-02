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
	public void cadastrarPessoa(String nome, Date dataDataNascimento, String cpf, String rg, String nomeMae, String nomePai, String endereco, String bairro, String cep){
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement(" INSERT INTO comum.pessoa (nome, cpf, registro_geral, nome_mae, nome_pai, endereco, bairro, cep, data_nascimento) " + 
							" VALUES (?,?,?,?,?,?,?,?,?) ");

			pstmt.setString(1, nome);
			pstmt.setString(2, cpf);
			pstmt.setString(3, rg);
			pstmt.setString(4, nomeMae);
			pstmt.setString(5, nomePai);
			pstmt.setString(6, endereco);
			pstmt.setString(7, bairro);
			pstmt.setString(8, cep);
			pstmt.setDate(9, new java.sql.Date(dataDataNascimento.getTime()));
			
			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 * Retorna usuario por login
	 * @param desc
	 * @return
	 */
	public Integer findPessoaByCpf(String cpf){
		Connection con = getConnection();
		
		String sql = "SELECT id_pessoa FROM comum.pessoa WHERE cpf = ?";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, cpf);
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				return rs.getInt("id_pessoa");
			}
			return null;
			
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
}
