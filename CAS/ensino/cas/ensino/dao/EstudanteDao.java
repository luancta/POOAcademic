package cas.ensino.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cas.acesso.dominio.Usuario;
import cas.comum.dominio.Pessoa;
import cas.ensino.dominio.Estudante;
import cas.util.util.GenericDao;

public class EstudanteDao extends GenericDao{

	
	public void cadastrarEstudante(String matricula, String curso, int idPessoa){
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement(" INSERT INTO ensino.estudante (matricula, curso, id_pessoa) " +  
			" VALUES (?,?,?,?) ");
			
			pstmt.setString(1, matricula);
			pstmt.setString(2, curso);
			pstmt.setInt(3, idPessoa);

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Estudante> findByMatricula(String matricula) {
		List<Estudante> estudantes = new ArrayList<Estudante>();
		Connection con = getConnection();

		String sql = " SELECT estudante.id_estudante, estudante.matricula, estudante.curso, pessoa.id_pessoa, pessoa.nome FROM ensino.estudante estudante"
				+ " JOIN comum.pessoa pessoa ON pessoa.id_pessoa = estudante.id_pesssoa "
				+ " WHERE matricula like ?";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, '%' + matricula + '%');

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				estudantes.add(populaEstudante(rs));
			}

			return estudantes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Estudante populaEstudante(ResultSet rs) throws SQLException{
		Estudante estudante = new Estudante();			
		estudante.setId(rs.getInt("estudante.id_estudante"));
		estudante.setMatricula(rs.getString("estudante.matricula"));
		estudante.setCurso(rs.getString("estudante.curso"));
		estudante.setPessoa(new Pessoa());
		estudante.getPessoa().setId(rs.getInt("pessoa.id_pessoa"));
		estudante.getPessoa().setNome(rs.getString("pessoa.nome"));
		return estudante;
	}
	
	public Estudante findById(int id) {
		List<Estudante> estudantes = new ArrayList<Estudante>();
		Connection con = getConnection();

		String sql = " SELECT estudante.id_estudante, estudante.matricula, estudante.curso, pessoa.id_pessoa, pessoa.nome FROM ensino.estudante estudante WHERE estudante.id_estudante = ? ";

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				estudantes.add(populaEstudante(rs));
			}

			return estudantes.isEmpty() ? new Estudante() : estudantes.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remover(Estudante estudante) {
		try {
			PreparedStatement pstmt = getConnection()
					.prepareStatement("DELETE FROM ensino.estudante " + "WHERE id_estudante = ? ");

			pstmt.setInt(1, estudante.getId());

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
