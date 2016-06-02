package cas.acesso.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cas.acesso.dominio.TipoUsuario;
import cas.acesso.dominio.Usuario;
import cas.espacoFisico.dao.LaboratorioDao;
import cas.espacoFisico.dominio.Laboratorio;
import cas.util.util.GenericDao;
import cas.util.util.ViewConsoleUtil;

public class UsuarioDao extends GenericDao {
	
	/**
	 * Retorna todos usuarios
	 * @param desc
	 * @return
	 */
	public List<Usuario> findAll(){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Connection con = getConnection();
		
		String sql = "SELECT * FROM acesso.usuario ";
		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()){
				usuarios.add(populaUsuario(rs));
			}
			
			return usuarios;
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
		/**
		 * Retorna usuario por login
		 * @param desc
		 * @return
		 */
		public List<Usuario> findUsuariobyLoginSenha(String login, String senha){
			List<Usuario> usuarios = new ArrayList<Usuario>();
			Connection con = getConnection();
			
			String sql = "SELECT * FROM acesso.usuario WHERE login = ? AND senha = ?";
			
			try{
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setString(1, login);
				stm.setString(2, senha);
				
				ResultSet rs = stm.executeQuery();
				
				while (rs.next()){
					usuarios.add(populaUsuario(rs));
				}
				
				return usuarios;
			}catch (SQLException e){
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * Popula usuário encontrado
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		private Usuario populaUsuario(ResultSet rs) throws SQLException{
			Usuario usuario = new Usuario();			
			usuario.setId(rs.getInt("id_usuario"));
			usuario.setLogin(rs.getString("login"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setTipoUsuario( rs.getInt("tipo_usuario"));
			return usuario;
		}
		
		public void cadastrarUsuario(String login, String senha, int tipoUsuario, int idPessoa){
			try {
				PreparedStatement pstmt = getConnection()
						.prepareStatement(" INSERT INTO acesso.usuario (login, senha, tipo_usuario, id_pessoa) " +  
				" VALUES (?,?,?,?) ");
				
				pstmt.setString(1, login);
				pstmt.setString(2, senha);
				pstmt.setInt(3, tipoUsuario);
				pstmt.setInt(4, idPessoa);
	
				pstmt.execute();
				pstmt.close();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public List<Usuario> findByLogin(String nome) {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			Connection con = getConnection();

			String sql = "SELECT * FROM acesso.usuario WHERE login like ?";

			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setString(1, '%' + nome + '%');

				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					usuarios.add(populaUsuario(rs));
				}

				return usuarios;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		

		public void remover(Usuario usuario) {
			try {
				PreparedStatement pstmt = getConnection()
						.prepareStatement("DELETE FROM acesso.usuario " + "WHERE id_usuario = ? ");

				pstmt.setInt(1, usuario.getId());

				pstmt.execute();
				pstmt.close();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public Usuario findById(int id) {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			Connection con = getConnection();

			String sql = "SELECT * FROM acesso.usuario WHERE id_usuario = ?";

			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setInt(1, id);

				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					usuarios.add(populaUsuario(rs));
				}

				return usuarios.isEmpty() ? new Usuario() : usuarios.get(0);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
}
