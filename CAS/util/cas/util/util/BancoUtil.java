package cas.util.util;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gilley
 */
public class BancoUtil {

    Connection conexao = null;

    public void CriarTabelas() {
        ConexaoUtil con = new ConexaoUtil();
        try {
            if (conexao == null) {
                conexao = con.getConexao();
            }
            java.sql.Statement stm = conexao.createStatement();

            String sql = "CREATE schema comum;\n" +
						"CREATE TABLE comum.turno ( id_turno integer not null, descricao character varying (255), CONSTRAINT pk_TurnoID PRIMARY KEY (id_turno));";
            
            ResultSet rs = stm.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e);
            e.printStackTrace();
        }
    }
   

}
