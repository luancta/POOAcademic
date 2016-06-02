package cas.comum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.comum.dominio.Turno;
import cas.util.util.GenericDao;

/*
 * @author Lucas Carvalho
 */
public class TurnoDAO extends GenericDao{
	
	//Busca, através do identificador, um turno cadastrado
	public Turno findTurnobyId(Integer id) throws Exception{
		List<Turno> turnos = new ArrayList<Turno>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM comum.turno WHERE id_turno = ?";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()){
				turnos.add(populaTurno(rs));
			}

			if (!turnos.isEmpty())
				return turnos.get(0);
			else
				return new Turno();
		}catch (SQLException e){
			throw new Exception(e.getMessage());
		}
	}
	
	//Busca um turno pela descrição
	public Turno findTurnobyDescricao(String desc) throws Exception{
		List<Turno> turnos = new ArrayList<Turno>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM comum.turno WHERE descricao = ?";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, desc);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()){
				turnos.add(populaTurno(rs));
			}

			if (!turnos.isEmpty())
				return turnos.get(0);
			else
				return new Turno();
		}catch (SQLException e){
			throw new Exception(e.getMessage());
		}
	}

	//Busca todos os turnos cadastrados
	public List<Turno> findTurnos() throws Exception{
		List<Turno> turnos = new ArrayList<Turno>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM comum.turno";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()){
				turnos.add(populaTurno(rs));
			}

			if (!turnos.isEmpty())
				return turnos;
			else
				return null;
		}catch (SQLException e){
			throw new Exception(e.getMessage());
		}
	}
	
	
	/* Método utilizado para popular as coleções de turnos, obtidas nas consultas ao banco.
	 * 
	 * @param rs
	 * 
	 * @return turno
	 */
	private Turno populaTurno(ResultSet rs) throws SQLException{
		Turno turno = new Turno();
		
		turno.setId(rs.getInt("id_turno"));
		turno.setDescricao(rs.getString("descricao"));
		
		return turno;
	}
}
