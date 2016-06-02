package cas.ensino.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.ensino.dominio.Turma;
import cas.util.util.GenericDao;

public class TurmaDao extends GenericDao {

	/**
	 * Retorna todos turmas
	 * 
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	public List<Turma> findAll() throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.turma ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				turmas.add(populaTurma(rs));
			}

			return turmas;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna turma por id
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Turma findById(int id) throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.turma WHERE id_turma = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				turmas.add(populaTurma(rs));
			}

			return turmas.isEmpty() ? new Turma() : turmas.get(0);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna turma por número
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<Turma> findByCodigo(String codigo) throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		Connection con = getConnection();

		String sql = "SELECT * FROM ensino.turma WHERE codigo like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + codigo + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				turmas.add(populaTurma(rs));
			}

			return turmas;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Salvar Turma de aula informada
	 * @param turma
	 * @throws Exception 
	 */
	public void salvar(Turma turma) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO ensino.turma " + "(codigo) " + "VALUES (?)");

			pstmt.setString(1, turma.getCodigo());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Remover Turma de aula informada
	 * @param turma
	 * @throws Exception 
	 */
	public void remover(Turma turma) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM ensino.turma " + "WHERE id_turma = ? ");

			pstmt.setInt(1, turma.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Popula turma encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Turma populaTurma(ResultSet rs) throws SQLException {
		Turma turma = new Turma();
		turma.setId(rs.getInt("id_turma"));
		turma.setCodigo(rs.getString("codigo"));
		return turma;
	}

}
