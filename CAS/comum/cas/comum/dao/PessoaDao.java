package cas.comum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import cas.acesso.dao.UsuarioDao;
import cas.comum.dominio.Pessoa;
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
	 * Retorna Pessoa por CPF
	 * @param desc
	 * @return
	 */
	public Pessoa findPessoaByCpf(String cpf){
		Connection con = getConnection();
		
		String sql = "SELECT * FROM comum.pessoa WHERE cpf = ? ";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, cpf);
			ResultSet rs = stm.executeQuery();
			rs.next();
			Pessoa pessoa =  populaPessoa(rs);
			if(pessoa != null && pessoa.getId() > 0)
				return pessoa;
			else
				return null;
			
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Popula Pessoa
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Pessoa populaPessoa(ResultSet rs) throws SQLException {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(rs.getInt("id_pessoa"));
		pessoa.setNome(rs.getString("nome"));
		pessoa.setCpf(rs.getString("cpf"));
		pessoa.setRg(rs.getString("registro_geral"));
		pessoa.setNomeMae(rs.getString("nome_mae"));
		pessoa.setNomePai(rs.getString("nome_pai"));
		pessoa.setEndereco(rs.getString("endereco"));
		pessoa.setBairro(rs.getString("bairro"));
		pessoa.setCep(rs.getString("cep"));
		pessoa.setDataNascimento(rs.getDate("data_nascimento"));
		return pessoa;
	}
}
