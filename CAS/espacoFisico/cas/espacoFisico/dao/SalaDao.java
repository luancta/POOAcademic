package cas.espacoFisico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.espacoFisico.dominio.Sala;
import cas.util.util.GenericDao;

public class SalaDao extends GenericDao {
	
	/**
	 * Retorna todos salas
	 * @param desc
	 * @return
	 */
	public List<Sala> findAll(){
		List<Sala> salas = new ArrayList<Sala>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM espaco_fisico.sala ";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()){
				salas.add(populaSala(rs));
			}
			
			return salas;
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
		/**
		 * Retorna sala por id
		 * @param id
		 * @return
		 */
		public Sala findById(int id){
			List<Sala> salas = new ArrayList<Sala>();
			Connection con = getConnection();
			
			String sql = "SELECT * FROM espaco_fisico.sala WHERE id_sala = ?";
			
			try{
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setInt(1, id);
				
				ResultSet rs = stm.executeQuery();
				
				while (rs.next()){
					salas.add(populaSala(rs));
				}
				
				return salas.isEmpty() ? new Sala() : salas.get(0);
			}catch (SQLException e){
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * Popula sala encontrada
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		private Sala populaSala(ResultSet rs) throws SQLException{
			Sala sala = new Sala();			
			sala.setId(rs.getInt("id_sala"));
			sala.setNumero(rs.getString("numero"));
			return sala;
		}

}
