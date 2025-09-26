package data;

import conexao.ConexaoBD;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//Essa Classe contém todos os metodos padrão CRUD

public class FilmeDao {

    public void insert(Filme filme) throws SQLException {
        String sql = "INSERT INTO filmes(nome,dataLancamento,categoria) VALUES (?,?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;

        try {

            conn = ConexaoBD.getConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, filme.getNome());
            pstm.setString(2, filme.getDataLancamento());
            pstm.setString(3, filme.getCategoria());

            pstm.executeUpdate();
            System.out.println("Filme inserido com sucesso!");
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao cadastrar filme: " + ex.getMessage());
        } finally {
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM filmes WHERE id = ? ";
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConexaoBD.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int linhasAfetadas = pstm.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Filme deletado com sucesso!");
            } else {
                System.out.println("Nenhum filme encontrado com esse ID.");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao deletar filme: " + ex.getMessage());
        } finally {
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void atualizar(Filme filme) throws SQLException {
       String sql = "UPDATE filmes SET nome = ?, dataLancamento = ?, categoria = ? WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstm = null;

    try {
        
        conn = ConexaoBD.getConnection();
        pstm = conn.prepareStatement(sql);

        pstm.setString(1, filme.getNome());
        pstm.setString(2, filme.getDataLancamento());
        pstm.setString(3, filme.getCategoria());
        pstm.setInt(4, filme.getId());

        int linhasAfetadas = pstm.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("Filme atualizado com sucesso!");
        } else {
            System.out.println("Nenhum filme encontrado com esse ID.");
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao atualizar filme: " + ex.getMessage());
    } finally {
        if (pstm != null) {
            pstm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    }

    public List<Filme> listar() throws SQLException {
        List<Filme> listaFilmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String dataLancamento = rs.getString("dataLancamento");
                String categoria = rs.getString("categoria");

                Filme filme = new Filme(id, nome, dataLancamento, categoria); // precisa de um construtor com ID
                listaFilmes.add(filme);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar filmes: " + ex.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return listaFilmes;
    }
public List<Filme> listarPorCategoria(String categoria) {
    List<Filme> lista = new ArrayList<>();
    String sql = "SELECT * FROM filmes WHERE categoria LIKE ?";

    try (Connection conn = ConexaoBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + categoria + "%"); // busca parcial
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Filme filme = new Filme(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("dataLancamento"),
                rs.getString("categoria")
            );
            lista.add(filme);
        }

    } catch (SQLException e) {
        System.err.println("❌ Erro ao listar filmes por categoria:");
        e.printStackTrace();
    }
    return lista;
}
}
