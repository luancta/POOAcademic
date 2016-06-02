package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.espacoFisico.dominio.Aplicativo;
import cas.util.util.GenericDao;

public class AplicativoDao extends GenericDao {

	/**
	 * Retorna todos aplicativos
	 * 
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	public List<Aplicativo> findAll() throws Exception {
		List<Aplicativo> aplicativos = new ArrayList<Aplicativo>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.aplicativo ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				aplicativos.add(populaAplicativo(rs));
			}

			return aplicativos;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna aplicativo por id
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Aplicativo findById(int id) throws Exception {
		List<Aplicativo> aplicativos = new ArrayList<Aplicativo>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.aplicativo WHERE id_aplicativo = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				aplicativos.add(populaAplicativo(rs));
			}

			return aplicativos.isEmpty() ? new Aplicativo() : aplicativos.get(0);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna aplicativo por número
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<Aplicativo> findByNome(String nome) throws Exception {
		List<Aplicativo> aplicativos = new ArrayList<Aplicativo>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.aplicativo WHERE nome like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + nome + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				aplicativos.add(populaAplicativo(rs));
			}

			return aplicativos;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Salvar Aplicativo de aula informada
	 * @param aplicativo
	 * @throws Exception 
	 */
	public void salvar(Aplicativo aplicativo) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO espaco_fisico.aplicativo " + "(nome) " + "VALUES (?)");

			pstmt.setString(1, aplicativo.getNome());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Remover Aplicativo de aula informada
	 * @param aplicativo
	 * @throws Exception 
	 */
	public void remover(Aplicativo aplicativo) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM espaco_fisico.aplicativo " + "WHERE id_aplicativo = ? ");

			pstmt.setInt(1, aplicativo.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Popula aplicativo encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Aplicativo populaAplicativo(ResultSet rs) throws SQLException {
		Aplicativo aplicativo = new Aplicativo();
		aplicativo.setId(rs.getInt("id_aplicativo"));
		aplicativo.setNome(rs.getString("nome"));
		return aplicativo;
	}

}
