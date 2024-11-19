package org.example;

import Banco.Banco;
import Contato.Contato;

public class Main {
    public static void main(String[] args) {
        Banco b1 = new Banco("programacao3", "programacao3", "123456");

        Contato c1 = new Contato(b1.obterConexao());
        c1.setNome("teste 1");
        c1.setEmail("teste@exemplo.com");
        c1.setTelefone("51999999999");

        c1.gravarContato();
    }
}