
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConexaoBD {
   
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Carrega o driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Faz a conexão com o banco (use seu banco real)
            String url = "jdbc:mysql://localhost:3306/cenaflix";
            String usuario = "root";
            String senha = "Du010890*";

            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println(" Conexão realizada com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println(" Driver JDBC não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
        return conn;
    }

    // Método para fechar a conexão
    public static void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println(" Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println(" Erro ao fechar a conexão.");
                e.printStackTrace();
            }
        }
    }
}

