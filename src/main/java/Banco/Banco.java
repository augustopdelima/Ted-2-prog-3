package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
    private Connection conexao;
    private boolean estaConectado;
    private String mensagemErro;

    public Banco(String servidor, String porta, String nomeBaco, String usuario, String senha) {
        String urlBase = "jdbc:mysql://?:?/?";
        String realUrl = String.format(urlBase, servidor, porta, nomeBaco);
        this.conectar(realUrl, usuario, senha);
    }

    public Banco(String nomeBanco, String usuario, String senha) {
        String urlBase = "jdbc:mysql://localhost:3306/?";
        String realUrl = String.format(urlBase, nomeBanco);
        this.conectar(realUrl,usuario, senha);
    }


    private void conectar(String url, String usuario, String senha) {
        try {
            this.conexao = DriverManager.getConnection(url, usuario, senha);
            this.estaConectado = true;
        } catch (SQLException e) {
            this.mensagemErro = e.getMessage();
        }
    }

    public Connection obterConexao() {
        return this.conexao;
    }

    public boolean conectado() {
        return this.estaConectado;
    }

    public String obterMensagemErro() {
        return  this.mensagemErro;
    }

    public void encerrarConexao() {
        try {
            this.conexao.close();
        } catch (SQLException e) {
            this.mensagemErro = e.getMessage();
        }
    }
}
