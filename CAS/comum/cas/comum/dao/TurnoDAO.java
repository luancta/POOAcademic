package cas.comum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cas.comum.dominio.Turno;
import cas.util.util.ConexaoUtil;
import cas.util.util.GenericDao;

/*
 * @author Lucas Carvalho
 */
public class TurnoDAO extends GenericDao{

	//Busca os turnos cadastrados
	public List<Turno> findTurnobyDescricao(String desc){
		List<Turno> turnos = new ArrayList<Turno>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM comum.turno WHERE descricao = ?";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, desc);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()){
				Turno turno = new Turno();
				
				turno.setId(rs.getInt("id_turno"));
				turno.setDescricao(rs.getString("descricao"));
		
				turnos.add(turno);
			}
			 
			
			return turnos;
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
}
