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
	 */
	public List<Docente> findAll() {
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
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna Docente por id
	 * 
	 * @param id
	 * @return
	 */
	public Docente findById(int id) {
		List<Docente> docentes = new ArrayList<Docente>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.docente WHERE id_docente = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				docentes.add(populaDocentes(rs));
			}

			return docentes.isEmpty() ? new Docente() : docentes.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna Docente por nome ou matricula
	 * 
	 * @param nome
	 * @return
	 */
	public List<Docente> findByNomeMatricula(String nome) {
		List<Docente> Docentes = new ArrayList<Docente>();
		Connection con = getConnection();

		String sql = 	  " SELECT d.* FROM ensino.docente d "
						+ " INNER JOIN comum.pessoa p USING (id_docente) " 
						+ " WHERE p.nome like ? OR d.matricula ? ";

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
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar Docente informado
	 * @param Docente
	 */
	public void salvar(Docente docente) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO ensino.docente(matricula,id_pessoa) " + "VALUES (?,?)");

			pstmt.setString(1, docente.getMatricula());
			pstmt.setString(2, String.valueOf(docente.getPessoa().getId()));

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remover Docente informada
	 * @param Docente
	 */
	public void remover(Docente docente) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM ensino.docente WHERE id_docente = ? ");

			pstmt.setInt(1, docente.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Popula Docente encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Docente populaDocentes(ResultSet rs) throws SQLException {
		Docente docente = new Docente();
		docente.setId(rs.getInt("id_docente"));
		docente.setMatricula(rs.getString("matricula"));
		docente.setPessoa(new Pessoa(rs.getInt("id_pessoa")));
		return docente;
	}
}
