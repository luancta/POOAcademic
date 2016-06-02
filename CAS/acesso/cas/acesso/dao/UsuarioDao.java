package cas.acesso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.acesso.dominio.Usuario;
import cas.util.util.GenericDao;

public class UsuarioDao extends GenericDao {
	
	/**
	 * Retorna todos usuarios
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	public List<Usuario> findAll() throws Exception{
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
			throw new Exception(e.getMessage());
		}
	}
	
		/**
		 * Retorna usuario por login
		 * @param desc
		 * @return
		 * @throws Exception 
		 */
		public List<Usuario> findUsuariobyLoginSenha(String login, String senha) throws Exception{
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
				throw new Exception(e.getMessage());
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
		
		public void cadastrarUsuario(String login, String senha, int tipoUsuario, int idPessoa) throws Exception{
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
				throw new Exception(e.getMessage());
			}
		}
		
		public List<Usuario> findByLogin(String nome) throws Exception {
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
				throw new Exception(e.getMessage());
			}
		}
		

		public void remover(Usuario usuario) throws Exception {
			try {
				PreparedStatement pstmt = getConnection()
						.prepareStatement("DELETE FROM acesso.usuario " + "WHERE id_usuario = ? ");

				pstmt.setInt(1, usuario.getId());

				pstmt.execute();
				pstmt.close();

			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		
		public Usuario findById(int id) throws Exception {
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
				throw new Exception(e.getMessage());
			}
		}
}
