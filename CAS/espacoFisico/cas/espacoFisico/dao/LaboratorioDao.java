package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.espacoFisico.dominio.Laboratorio;
import cas.util.util.GenericDao;

public class LaboratorioDao extends GenericDao {

	/**
	 * Retorna todos laboratorios
	 * 
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	public List<Laboratorio> findAll() throws Exception {
		List<Laboratorio> laboratorios = new ArrayList<Laboratorio>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.laboratorio ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				laboratorios.add(populaLaboratorio(rs));
			}

			return laboratorios;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna laboratorio por id
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Laboratorio findById(int id) throws Exception {
		List<Laboratorio> laboratorios = new ArrayList<Laboratorio>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.laboratorio WHERE id_laboratorio = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				laboratorios.add(populaLaboratorio(rs));
			}

			return laboratorios.isEmpty() ? new Laboratorio() : laboratorios.get(0);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna laboratorio por número
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<Laboratorio> findByNome(String nome) throws Exception {
		List<Laboratorio> laboratorios = new ArrayList<Laboratorio>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.laboratorio WHERE nome like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + nome + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				laboratorios.add(populaLaboratorio(rs));
			}

			return laboratorios;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Salvar Laboratorio de aula informada
	 * @param laboratorio
	 * @throws Exception 
	 */
	public void salvar(Laboratorio laboratorio) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO espaco_fisico.laboratorio " + "(nome) " + "VALUES (?)");

			pstmt.setString(1, laboratorio.getNome());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Remover Laboratorio de aula informada
	 * @param laboratorio
	 * @throws Exception 
	 */
	public void remover(Laboratorio laboratorio) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM espaco_fisico.laboratorio " + "WHERE id_laboratorio = ? ");

			pstmt.setInt(1, laboratorio.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Popula laboratorio encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Laboratorio populaLaboratorio(ResultSet rs) throws SQLException {
		Laboratorio laboratorio = new Laboratorio();
		laboratorio.setId(rs.getInt("id_laboratorio"));
		laboratorio.setNome(rs.getString("nome"));
		return laboratorio;
	}

}
