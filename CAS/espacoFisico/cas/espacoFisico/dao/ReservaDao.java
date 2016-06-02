package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.ensino.dao.TurmaDao;
import cas.espacoFisico.dominio.Reserva;
import cas.util.util.GenericDao;

public class ReservaDao extends GenericDao {

	/**
	 * Retorna todos reservas
	 * 
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	public List<Reserva> findAll() throws Exception {
		List<Reserva> reservas = new ArrayList<Reserva>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.reserva ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				reservas.add(populaReserva(rs));
			}

			return reservas;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Retorna reserva por id
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Reserva findById(int id) throws Exception {
		List<Reserva> reservas = new ArrayList<Reserva>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.reserva WHERE id_reserva = ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				reservas.add(populaReserva(rs));
			}

			return reservas.isEmpty() ? new Reserva() : reservas.get(0);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Retorna reservas por codigo da turma
	 * 
	 * @param codTurma
	 * @return
	 * @throws Exception
	 */
	public List<Reserva> findByTurma(String codTurma) throws Exception {
		List<Reserva> reservas = new ArrayList<Reserva>();
		Connection con = getConnection();

		String sql = "SELECT * FROM espaco_fisico.reserva "
				+ "JOIN ensino.turma t USING (id_turma) "
				+ "WHERE codigo like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, codTurma);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				reservas.add(populaReserva(rs));
			}

			return reservas;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Salvar Reserva de aula informada
	 * 
	 * @param reserva
	 * @throws Exception 
	 */
	public void salvar(Reserva reserva) throws Exception {
		try {
			PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO espaco_fisico.reserva "
					+ " (usa_projetor, ativo, data,  id_turma, id_local_aula) " + " VALUES (?,?,?,?,?)");

			pstmt.setBoolean(1, reserva.isUsaProjetor());
			pstmt.setBoolean(2, reserva.isAtivo());
			pstmt.setObject(3, reserva.getData());
			pstmt.setInt(3, reserva.getTurma() != null ? reserva.getTurma().getId() : 1);
			pstmt.setInt(4, reserva.getLocalAula() != null ? reserva.getLocalAula().getId() : 1);

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Remover Reserva de aula informada
	 * 
	 * @param reserva
	 * @throws Exception 
	 */
	public void remover(Reserva reserva) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM espaco_fisico.reserva " + "WHERE id_reserva = ? ");

			pstmt.setInt(1, reserva.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Popula reserva encontrada
	 * 
	 * @param rs
	 * @return
	 * @throws Exception 
	 */
	private Reserva populaReserva(ResultSet rs) throws Exception {
		Reserva reserva = new Reserva();
		reserva.setId(rs.getInt("id_local_aula"));
		reserva.setUsaProjetor(rs.getBoolean("usa_projetor"));
		reserva.setAtivo(rs.getBoolean("ativo"));
		reserva.setData(rs.getDate("data"));
		if (rs.getInt("id_turma") > 0) {
			TurmaDao turmaDao = new TurmaDao();
			reserva.setTurma(turmaDao.findById(rs.getInt("id_turma")));
		}
		if (rs.getInt("id_local_aula") > 0) {
			LocalAulaDao localAulaDao = new LocalAulaDao();
			reserva.setLocalAula(localAulaDao.findById(rs.getInt("id_local_aula")));
		}
		return reserva;
	}

}
