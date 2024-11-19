package org.example;

import java.io.IOException;
import java.util.Scanner;

import Banco.Banco;
import Contato.Contato;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco("programacao3", "programacao3", "123456");
        Contato contato = new Contato(banco.obterConexao());
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 7) {
            System.out.printf("%2s\n", "===== SISTEMA DE CONTATOS =====");
            System.out.printf("%2s\n", "1. Gravar contato");
            System.out.printf("%2s\n", "2. Atualizar contato");
            System.out.printf("%2s\n", "3. Deletar contato");
            System.out.printf("%2s\n", "4. Exibir contato");
            System.out.printf("%2s\n", "5. Filtrar contatos");
            System.out.printf("%2s\n", "6. Listar contatos");
            System.out.printf("%2s\n", "7. Sair");
            System.out.println();
            System.out.printf("%2s", "Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            limpaTela(false);

            switch (opcao) {
                case 1 -> gravarContato(scanner, contato);
                case 2 -> atualizarContato(scanner, contato);
                case 3 -> deletarContato(scanner, contato);
                case 4 -> exibirContato(scanner, contato);
                case 5 -> filtrarContatos(scanner);
                case 6 -> listarContatos();
                case 7 -> System.out.println("Saindo...");
                default -> mostraMensagemTemporaria("Opção inválida! Tente novamente", true);
            }
        }

        scanner.close();
    }

    public static void gravarContato(Scanner scanner, Contato contato) {
        System.out.println("===== GRAVAR CONTATO =====\n");
        formularioContato(scanner, contato);
        limpaTela(false);
        contato.gravarContato();
        limpaTela(true);
    }

    public static void atualizarContato(Scanner scanner, Contato contato) {
        System.out.println("===== ALTERAR CONTATO =====\n");
        formularioContatoId(scanner, contato);
        formularioContato(scanner, contato);
        limpaTela(false);
        contato.atualizarContato();
        limpaTela(true);
    }

    public static void deletarContato(Scanner scanner, Contato contato) {
        System.out.println("===== DELETAR CONTATO =====\n");
        formularioContatoId(scanner, contato);
        limpaTela(false);
        contato.deletarContato();
        limpaTela(true);
    }

    public static void exibirContato(Scanner scanner, Contato contato) {
        System.out.println("===== EXIBIR CONTATO =====\n");
        formularioContatoId(scanner, contato);
        limpaTela(false);
        contato.obterContatoPeloId(contato.getIdContato());
        limpaTela(true);
    }
    
    public static void filtrarContatos(Scanner scanner) {

    }

    public static void listarContatos() {

    }

    public static void formularioContato(Scanner scanner, Contato contato) {
        System.out.printf("Nome do contato: ");
        contato.setNome(scanner.nextLine());

        System.out.printf("Email do contato: ");
        contato.setEmail(scanner.nextLine());

        System.out.printf("Telefone do contato: ");
        contato.setTelefone(scanner.nextLine());
    }

    public static void formularioContatoId(Scanner scanner, Contato contato) {
        System.out.printf("Id do contato: ");
        contato.setIdContato(scanner.nextInt());
        scanner.nextLine();
    }

    public static void mostraMensagemTemporaria(String mensagem, boolean limparTela) {
        if (limparTela) limpaTela(false);
        System.out.print(mensagem);
        limpaTela(true);
    }

    public static void limpaTela(boolean delay) {
        if (delay) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}