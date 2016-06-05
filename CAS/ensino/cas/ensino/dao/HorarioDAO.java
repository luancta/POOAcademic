package cas.ensino.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.comum.dao.TurnoDAO;
import cas.comum.dominio.DiaSemana;
import cas.comum.dominio.Turno;
import cas.ensino.dominio.Horario;
import cas.util.util.GenericDao;

public class HorarioDAO extends GenericDao{

	//Busca todos os horarios cadastrados
	public List<Horario> findHorarios() throws Exception{
		List<Horario> horarios = new ArrayList<Horario>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM  ensino.horario";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()){
				horarios.add(populaHorario(rs));
			}
			
			if(!horarios.isEmpty())
				return horarios;
			else
				return null;
			
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	//Busca um horário pelo seu identificador
	public Horario findHorarioById(int idHorario) throws Exception{
		List<Horario> horarios = new ArrayList<Horario>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM  ensino.horario WHERE id_horario = ?";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1, idHorario);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()){
				horarios.add(populaHorario(rs));
			}
			
			if(!horarios.isEmpty())
				return horarios.get(0);
			else
				return null;
			
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	/* Método utilizado para popular as coleções de horários, obtidas nas consultas ao banco.
	 * 
	 * @param rs
	 * 
	 * @return horario
	 */
	private Horario populaHorario(ResultSet rs) throws Exception{
		TurnoDAO turnoDao = new TurnoDAO();
		Horario horario = new Horario();
		Turno turno = new Turno();
		
		
		horario.setId(rs.getInt("id_horario"));
		int turnoId = rs.getInt("id_turno");
		if(turnoId != 0){
			turno = turnoDao.findTurnobyId(turnoId);
		}
		horario.setTurno(turno);
		horario.setHoraInicio(rs.getTime("hora_inicio"));
		horario.setHoraFim(rs.getTime("hora_time"));
		horario.setDiaSemana(DiaSemana.get(rs.getInt("dia_semana")));
		
		return horario;
	}
	
	public void salvar(Horario horario) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("INSERT INTO ensino.horario (id_turno, hora_inicio, hora_time, dia_semana) " + "VALUES (?,?,?,?)");

			pstmt.setInt(1, horario.getTurno().getId());
			pstmt.setTime(2, horario.getHoraInicio());
			pstmt.setTime(3, horario.getHoraFim());
			pstmt.setInt(4, horario.getDiaSemana().getIndexOfDia(horario.getDiaSemana()));

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	public void remover(int idHorario) throws Exception {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM ensino.horario WHERE id_horario = ?");

			pstmt.setInt(1, idHorario);

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
