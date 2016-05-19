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

            ResultSet rs = stm.executeQuery(" CREATE TABLE comum.usuario\n");

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e);
            e.printStackTrace();
        }
    }
   

}
