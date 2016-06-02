package cas.ensino.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.ensino.dominio.Disciplina;
import cas.util.util.GenericDao;

public class DisciplinaDao extends GenericDao {

	/**
	 * Retorna todas Disciplinas
	 * 
	 * @param desc
	 * @return
	 */
	public List<Disciplina> findAll() {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.disciplina ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				disciplinas.add(populaDisciplinas(rs));
			}

			return disciplinas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna disciplina por id
	 * 
	 * @param id
	 * @return
	 */
	public Disciplina findById(int id) {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.disciplina WHERE id_disciplina = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				disciplinas.add(populaDisciplinas(rs));
			}

			return disciplinas.isEmpty() ? new Disciplina() : disciplinas.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna Disciplina por nome ou sigla
	 * 
	 * @param nome
	 * @return
	 */
	public List<Disciplina> findByNomeSigla(String nome) {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.disciplina WHERE nome like ? OR sigla ? ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + nome + '%');
			stm.setString(2, '%' + nome + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				disciplinas.add(populaDisciplinas(rs));
			}

			return disciplinas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar Disciplina informada
	 * @param Disciplina
	 */
	public void salvar(Disciplina disciplina) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO ensino.disciplina(nome,sigla) " + "VALUES (?,?)");

			pstmt.setString(1, disciplina.getNome());
			pstmt.setString(2, disciplina.getSigla());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remover Disciplina informada
	 * @param Disciplina
	 */
	public void remover(Disciplina disciplina) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM ensino.disciplina WHERE id_disciplina = ? ");

			pstmt.setInt(1, disciplina.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Popula Disciplina encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Disciplina populaDisciplinas(ResultSet rs) throws SQLException {
		Disciplina disciplina = new Disciplina();
		disciplina.setId(rs.getInt("id_disciplina"));
		disciplina.setNome(rs.getString("nome"));
		disciplina.setSigla(rs.getString("sigla"));
		return disciplina;
	}
}
