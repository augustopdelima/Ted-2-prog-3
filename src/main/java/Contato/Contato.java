package Contato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Contato {
    private Integer idContato;
    private String nome;
    private String email;
    private String telefone;
    private Connection conexao;

    public Contato(Connection conexao) {
        this.conexao = conexao;
    }

    public Contato() {}

    public void gravarContato() {
        try {
            String sql = "INSERT INTO tb_contato(nome, e_mail, telefone) VALUES(?, ?, ?)";
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, this.nome);
            statement.setString(2, this.email);
            statement.setString(3, this.telefone);

            statement.execute();
            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                this.idContato = result.getInt(1);
                System.out.println("Contato gravado!");
            }
        } catch (SQLException e){
            System.out.println("Erro de sql: "+e.getMessage());
        }
    }

    public void atualizarContato() throws SQLException {
        String sql = "UPDATE tb_contato SET nome = ?, e_mail = ?, telefone = ? WHERE contato_id = ?";
        PreparedStatement statement = conexao.prepareStatement(sql);

        statement.setString(1, this.nome);
        statement.setString(2, this.email);
        statement.setString(3, this.telefone);

        String id = this.idContato.toString();
        statement.setString(4, id);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Contato atualizado!");
        } else {
            System.out.println("Contato não foi encontrado!");
        }
    }

    public void deletarContato(Integer idContato) throws SQLException {
        String sql = "DELETE FROM tb_contato WHERE contato_id = ?";
        PreparedStatement statement = conexao.prepareStatement(sql);

        String id = idContato.toString();
        statement.setString(1, id);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Contato foi deletado!");
        } else {
            System.out.println("Contato não foi encontrado para deletar!");
        }
    }

    public void deletarContato() throws SQLException {
        this.deletarContato(this.idContato);
    }

    public boolean obterContatoPeloId(Integer idContato)  throws  SQLException {
        String sql = "SELECT contato_id, nome, e_mail, telefone FROM tb_contato WHERE contato_id = ?";
        PreparedStatement statement = conexao.prepareStatement(sql);

        String id = idContato.toString();
        statement.setString(1, id);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            this.setIdContato(result.getInt("contato_id"));
            this.setNome(result.getString("nome"));
            this.setEmail(result.getString("e_mail"));
            this.setTelefone(result.getString("telefone"));
            return true;
        }

        return false;
    }

    public ResultSet obterContatos()  throws SQLException {
        String sql = "SELECT contato_id, nome, e_mail, telefone FROM tb_contato";
        PreparedStatement statement = conexao.prepareStatement(sql);

        return statement.executeQuery();
    }

    public List<Contato> obterListaContatos()  {
        List<Contato> contatos = new ArrayList<>();

        try (ResultSet resultSetContatos = this.obterContatos()) {
            while (resultSetContatos.next()) {
                Contato contato = new Contato();
                contato.setIdContato(resultSetContatos.getInt("contato_id"));
                contato.setNome(resultSetContatos.getString("nome"));
                contato.setEmail(resultSetContatos.getString("e_mail"));
                contato.setTelefone(resultSetContatos.getString("telefone"));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contatos;
    }

    public List<Contato> pesquisaContato(String termo) {
        String sql = "SELECT contato_id, nome, e_mail, telefone " +
            "FROM tb_contato " +
            "WHERE nome LIKE ? OR e_mail LIKE ? OR telefone LIKE ?";
        List<Contato> contatos = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            String termoPesquisa = "%" + termo + "%";
            statement.setString(1, termoPesquisa);
            statement.setString(2, termoPesquisa);
            statement.setString(3, termoPesquisa);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Contato contato = new Contato();
                    contato.setIdContato(resultSet.getInt("contato_id"));
                    contato.setNome(resultSet.getString("nome"));
                    contato.setEmail(resultSet.getString("e_mail"));
                    contato.setTelefone(resultSet.getString("telefone"));
                    contatos.add(contato);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar contatos", e);
        }

        return contatos;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Contato{" +
            "idContato=" + idContato +
            ", nome='" + nome + '\'' +
            ", email='" + email + '\'' +
            ", telefone='" + telefone + '\'' +
            ", conexao=" + conexao +
            "}";
    }
}
