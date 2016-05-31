package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.espacoFisico.dominio.Marca;
import cas.espacoFisico.dominio.Projetor;
import cas.util.util.GenericDao;

public class ProjetorDao extends GenericDao {

	/**
	 * Retorna todos projetors
	 * 
	 * @param desc
	 * @return
	 */
	public List<Projetor> findAll() {
		List<Projetor> projetors = new ArrayList<Projetor>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.projetor ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				projetors.add(populaProjetor(rs));
			}

			return projetors;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna projetor por id
	 * 
	 * @param id
	 * @return
	 */
	public Projetor findById(int id) {
		List<Projetor> projetors = new ArrayList<Projetor>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.projetor WHERE id_projetor = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				projetors.add(populaProjetor(rs));
			}

			return projetors.isEmpty() ? new Projetor() : projetors.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna projetor por número
	 * 
	 * @param id
	 * @return
	 */
	public List<Projetor> findByTombo(String tombo) {
		List<Projetor> projetors = new ArrayList<Projetor>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.projetor WHERE tombo like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + tombo + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				projetors.add(populaProjetor(rs));
			}

			return projetors;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar Projetor de aula informada
	 * @param projetor
	 */
	public void salvar(Projetor projetor) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO espaco_fisico.projetor " + "(tombo, marca) " + "VALUES (?,?)");

			pstmt.setString(1, projetor.getTombo());
			pstmt.setInt(2, projetor.getMarca().ordinal());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remover Projetor de aula informada
	 * @param projetor
	 */
	public void remover(Projetor projetor) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM espaco_fisico.projetor " + "WHERE id_projetor = ? ");

			pstmt.setInt(1, projetor.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Popula projetor encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Projetor populaProjetor(ResultSet rs) throws SQLException {
		Projetor projetor = new Projetor();
		projetor.setId(rs.getInt("id_projetor"));
		projetor.setTombo(rs.getString("tombo"));
		projetor.setMarca(Marca.getMarca(rs.getInt("marca")));
		return projetor;
	}

}
