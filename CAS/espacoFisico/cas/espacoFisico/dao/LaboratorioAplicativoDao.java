package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.espacoFisico.dominio.LaboratorioAplicativo;
import cas.util.util.GenericDao;

public class LaboratorioAplicativoDao extends GenericDao {

	/**
	 * Retorna todos laboratorios
	 * 
	 * @param desc
	 * @return
	 */
	public List<LaboratorioAplicativo> findAll() {
		List<LaboratorioAplicativo> laboratorios = new ArrayList<LaboratorioAplicativo>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.laboratorio_aplicativo ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				laboratorios.add(populaLaboratorioAplicativo(rs));
			}

			return laboratorios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna laboratorio por id
	 * 
	 * @param id
	 * @return
	 */
	public LaboratorioAplicativo findById(int id) {
		List<LaboratorioAplicativo> laboratorios = new ArrayList<LaboratorioAplicativo>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.laboratorio_aplicativo WHERE id_laboratorio_aplicativo = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				laboratorios.add(populaLaboratorioAplicativo(rs));
			}

			return laboratorios.isEmpty() ? new LaboratorioAplicativo() : laboratorios.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Retorna laboratorio por id
	 * 
	 * @param id
	 * @return
	 */
	public LaboratorioAplicativo findByLabApl(int idLaboraotrio, int idAplicativo) {
		List<LaboratorioAplicativo> laboratorios = new ArrayList<LaboratorioAplicativo>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.laboratorio_aplicativo WHERE id_laboratorio = ? AND id_aplicativo = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idLaboraotrio);
			stm.setInt(2, idAplicativo);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				laboratorios.add(populaLaboratorioAplicativo(rs));
			}

			return laboratorios.isEmpty() ? new LaboratorioAplicativo() : laboratorios.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar LaboratorioAplicativo de aula informada
	 * @param laboratorio
	 */
	public void salvar(LaboratorioAplicativo laboratorio) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO espaco_fisico.laboratorio_aplicativo " + "(id_laboratorio, id_aplicativo) " + "VALUES (?,?)");

			pstmt.setInt(1, laboratorio.getLaboratorio().getId());
			pstmt.setInt(2, laboratorio.getAplicativo().getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remover LaboratorioAplicativo de aula informada
	 * @param laboratorio
	 */
	public void remover(LaboratorioAplicativo laboratorio) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM espaco_fisico.laboratorio_aplicativo " + "WHERE id_laboratorio_aplicativo = ? ");

			pstmt.setInt(1, laboratorio.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Popula laboratorio encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private LaboratorioAplicativo populaLaboratorioAplicativo(ResultSet rs) throws SQLException {
		LaboratorioDao labDao = new LaboratorioDao();
		AplicativoDao aplDao = new AplicativoDao();
		
		LaboratorioAplicativo laboratorioAplicativo = new LaboratorioAplicativo();
		laboratorioAplicativo.setId(rs.getInt("id_laboratorio_aplicativo"));
		laboratorioAplicativo.setLaboratorio( labDao.findById( rs.getInt("id_laboratorio") ));
		laboratorioAplicativo.setAplicativo(aplDao.findById(rs.getInt("id_aplicativo")));
		return laboratorioAplicativo;
	}

}
