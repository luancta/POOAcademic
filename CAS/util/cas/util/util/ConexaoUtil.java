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
public class ConexaoUtil {
    Connection conexao = null;
    
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static String nomeBanco = "";
    public Connection getConexao (){
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conexao = DriverManager.getConnection("jdbc:hsqldb:file:c:/cas/"+nomeBanco, "sa", "");
            if (conexao != null) {
                return conexao;
            } else {
                System.out.println("Não foi possível conectar com o banco");
                return null;
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver JDBC. ");
            return null;
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e);
            e.printStackTrace();
            return null;
        }
    }   
    
    public static Connection createDB() {
        try {
            String URL = "jdbc:hsqldb:file:c:/cas/"+nomeBanco+";user=sa;password=;";
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
     public boolean verificarBanco() {
        ConexaoUtil con = new ConexaoUtil();
        try {
            if (conexao == null) {
                conexao = con.getConexao();
            }
            java.sql.Statement stm = conexao.createStatement();

            ResultSet rs = stm.executeQuery(" SELECT * FROM comum.usuario ");
            while (rs.next()) {
               int id =  rs.getInt(1);
               id++;
            }
//            stm.execute("SHUTDOWN");
            return true;
        } catch (SQLException e) {
            return false;            
        }
    }
    
    public static void setBanco(String banco) {
        nomeBanco = banco;
    }   
    
    
}
