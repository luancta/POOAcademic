package cas.ensino.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.comum.dominio.Pessoa;
import cas.ensino.dominio.Docente;
import cas.util.util.GenericDao;

public class DocenteDao extends GenericDao{

	/**
	 * Retorna todos Docentes
	 * 
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	public List<Docente> findAll() throws Exception {
		List<Docente> Docentes = new ArrayList<Docente>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.docente ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				Docentes.add(populaDocentes(rs));
			}

			return Docentes;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna Docente por id
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Docente findById(int id) throws Exception {
		List<Docente> docentes = new ArrayList<Docente>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.docente WHERE id_docente = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				docentes.add(populaApenasDocente(rs));
			}

			return docentes.isEmpty() ? new Docente() : docentes.get(0);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna Docente por nome ou matricula
	 * 
	 * @param nome
	 * @return
	 * @throws Exception 
	 */
	public List<Docente> findByNomeMatricula(String nome) throws Exception {
		List<Docente> Docentes = new ArrayList<Docente>();
		Connection con = getConnection();

		String sql = 	  " SELECT * FROM ensino.docente d "
						+ " INNER JOIN comum.pessoa p ON d.id_pessoa = p.id_pessoa " 
						+ " WHERE p.nome like ? OR d.matricula like ? ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + nome + '%');
			stm.setString(2, '%' + nome + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				Docentes.add(populaDocentes(rs));
			}

			return Docentes;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Salvar Docente informado
	 * @param Docente
	 * @throws Exception 
	 */
	public void salvar(Docente docente) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO ensino.docente(matricula,id_pessoa) " + "VALUES (?,?)");

			pstmt.setString(1, docente.getMatricula());
			pstmt.setString(2, String.valueOf(docente.getPessoa().getId()));

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Remover Docente informada
	 * @param Docente
	 * @throws Exception 
	 */
	public void remover(Docente docente) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM ensino.docente WHERE id_docente = ? ");

			pstmt.setInt(1, docente.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Popula Docente encontrado
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Docente populaDocentes(ResultSet rs) throws SQLException {
		Docente docente = new Docente();
		docente.setId(rs.getInt("id_docente"));
		docente.setMatricula(rs.getString("matricula"));
		
		Pessoa pessoa = new Pessoa(rs.getInt("id_pessoa"));
		pessoa.setNome(rs.getString("nome"));
		pessoa.setCpf(rs.getString("cpf"));
		pessoa.setRg(rs.getString("registro_geral"));
		pessoa.setNomeMae(rs.getString("nome_mae"));
		pessoa.setNomePai(rs.getString("nome_pai"));
		pessoa.setEndereco(rs.getString("endereco"));
		pessoa.setBairro(rs.getString("bairro"));
		pessoa.setCep(rs.getString("cep"));
		pessoa.setDataNascimento(rs.getDate("data_nascimento"));
		
		docente.setPessoa(pessoa);
		return docente;
	}
	
	/**
	 * Popula Docente encontrado
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Docente populaApenasDocente(ResultSet rs) throws SQLException {
		Docente docente = new Docente();
		docente.setId(rs.getInt("id_docente"));
		docente.setMatricula(rs.getString("matricula"));
		Pessoa pessoa = new Pessoa(rs.getInt("id_pessoa"));
		docente.setPessoa(pessoa);
		return docente;
	}
}
