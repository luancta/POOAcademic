package cas.util.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author gilley
 */
public class GenericDao {

    private Connection connection;
    private ConexaoUtil conexao = new ConexaoUtil();

    protected GenericDao() {

    }

    public Connection getConnection() {
        return conexao.getConexao();
    }

    protected void save(String insertSql, Object... parametros) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(insertSql);

            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            pstmt.execute();
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void update(String updateSql, Object... parametros) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(updateSql);

            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void delete(String deleteSql, Object... parametros) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(deleteSql);

            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void shutdown() throws SQLException {
        getConnection().createStatement().executeUpdate("SHUTDOWN");
    }
}
