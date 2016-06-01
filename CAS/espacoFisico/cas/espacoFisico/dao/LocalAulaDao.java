package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.espacoFisico.dominio.LocalAula;
import cas.util.util.GenericDao;

public class LocalAulaDao extends GenericDao {

	/**
	 * Retorna todos localAulas
	 * 
	 * @param desc
	 * @return
	 */
	public List<LocalAula> findAll() {
		List<LocalAula> localAulas = new ArrayList<LocalAula>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.localAula ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				localAulas.add(populaLocalAula(rs));
			}

			return localAulas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna localAula por id
	 * 
	 * @param id
	 * @return
	 */
	public LocalAula findById(int id) {
		List<LocalAula> localAulas = new ArrayList<LocalAula>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.local_aula WHERE id_local_Aula = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				localAulas.add(populaLocalAula(rs));
			}

			return localAulas.isEmpty() ? new LocalAula() : localAulas.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna localAula por bloco
	 * 
	 * @param id
	 * @return
	 */
	public List<LocalAula> findByBloco(String bloco) {
		List<LocalAula> localAulas = new ArrayList<LocalAula>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.local_aula WHERE bloco like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + bloco + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				localAulas.add(populaLocalAula(rs));
			}

			return localAulas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar LocalAula de aula informada
	 * 
	 * @param localAula
	 */
	public void salvar(LocalAula localAula) {
		try {
			PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO espaco_fisico.local_aula "
					+ " (bloco, capacidade, id_sala, id_laboratorio) " + " VALUES (?,?,?,?)");

			pstmt.setString(1, localAula.getBloco());
			pstmt.setInt(2, localAula.getCapacidade());
			pstmt.setInt(3, localAula.getSala() != null ? localAula.getSala().getId() : 1);
			pstmt.setInt(4, localAula.getLaboratorio() != null ? localAula.getLaboratorio().getId() : 1);

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remover LocalAula de aula informada
	 * 
	 * @param localAula
	 */
	public void remover(LocalAula localAula) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM espaco_fisico.local_aula " + "WHERE id_local_aula = ? ");

			pstmt.setInt(1, localAula.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Popula localAula encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private LocalAula populaLocalAula(ResultSet rs) throws SQLException {
		LocalAula localAula = new LocalAula();
		localAula.setId(rs.getInt("id_local_aula"));
		localAula.setBloco(rs.getString("bloco"));
		localAula.setCapacidade(rs.getInt("capacidade"));
		if (rs.getInt("id_laboratorio") > 0) {
			LaboratorioDao labDao = new LaboratorioDao();
			localAula.setLaboratorio(labDao.findById(rs.getInt("id_laboratorio")));
		}
		if (rs.getInt("id_sala") > 0) {
			SalaDao salaDao = new SalaDao();
			localAula.setSala(salaDao.findById(rs.getInt("id_sala")));
		}
		return localAula;
	}

}
