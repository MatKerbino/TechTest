package org.example.PostgreSqlService;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Utils {

    static Scanner teclado = new Scanner(System.in);

    public static Connection conectar(){
        Properties props = new Properties();
        props.setProperty("user", "kerbino");
        props.setProperty("password", "1234");
        props.setProperty("ssl", "false");
        String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/jpostgresql";
        try {
            return DriverManager.getConnection(URL_SERVIDOR, props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void desconectar(Connection conn) {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void listar () {
        String BUSCAR_TODOS = "SELECT * FROM produtos";

        try {
            Connection conn = conectar();
            PreparedStatement produtos = conn.prepareStatement(
                    BUSCAR_TODOS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = produtos.executeQuery();

            // não tem como saber quantos resultados foram encontrados.
            // ta na hora da gambiarra
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();
            // ir para o último elemento para pegar o número da linha e depois voltar para o início

            if (qtd > 0) {
                System.out.println("Listando produtos...");
                System.out.println('='*20);

                while (res.next()) {
                    System.out.println("Id: " + res.getInt(1));
                    System.out.println("Produto: " + res.getString(2));
                    System.out.println("Preço: " + res.getFloat(3));
                    System.out.println("Estoque: " + res.getInt(4));
                    System.out.println('='*20);
                }
            } else {
                System.out.println("Não existem produtos");
            }
            produtos.close();
            desconectar(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void inserir () {
        System.out.println("Informe o nome do produto: ");
        String nome = teclado.nextLine();

        System.out.println("Informe o preço do produto: ");
        float preco = teclado.nextFloat();

        System.out.println("Informe a quantidade em estoque: ");
        int estoque = teclado.nextInt();

        String INSERIR = "INSERT INTO produtos (nome, preco, estoque) VALUES (?, ?, ?)";
        //Prevenção de SQL Injection
        try {
            Connection conn = conectar();
            PreparedStatement salvar = conn.prepareStatement(
                    INSERIR,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            salvar.setString(1, nome);
            salvar.setFloat(2, preco);
            salvar.setInt(3,estoque);

            salvar.executeUpdate();
            salvar.close();
            desconectar(conn);
            System.out.println("O produto " + nome + " foi inserido com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void atualizar() {
        System.out.println("Informe o código do produto: ");
        int id = Integer.parseInt(teclado.nextLine());

        String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id=?";

        try {
            Connection conn = conectar();
            PreparedStatement produto = conn.prepareStatement(
                    BUSCAR_POR_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            produto.setInt(1, id);
            ResultSet res = produto.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                System.out.println("Informe o nome do novo produto: ");
                String nome = teclado.nextLine();

                System.out.println("Informe o preço do novo produto: ");
                float preco = teclado.nextFloat();

                System.out.println("Informe a quantidade em estoque: ");
                int estoque = teclado.nextInt();

                String ATUALIZAR = "UPDATE produtos SET nome=?, preco=?, estoque=? WHERE id=?";
                PreparedStatement upd = conn.prepareStatement(ATUALIZAR);

                upd.setString(1, nome);
                upd.setFloat(2, preco);
                upd.setInt(3,estoque);

                upd.executeUpdate();
                upd.close();
                desconectar(conn);

                System.out.println("O produto " + nome + " foi atualizado com sucesso");
            } else {
                System.out.println("Não existe produto com o id informado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void deletar() {
        System.out.println("Informe o código do produto: ");
        int id = Integer.parseInt(teclado.nextLine());

        String DELETAR_POR_ID = "DELETE FROM produtos WHERE id=?";
        String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id=?";

        System.out.println("Informe o código do produto: ");
        try {
            Connection conn = conectar();
            PreparedStatement produto = conn.prepareStatement(
                    BUSCAR_POR_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            produto.setInt(1, id);
            ResultSet res = produto.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(DELETAR_POR_ID);
                del.setInt(1, id);
                del.executeUpdate();
                del.close();
                desconectar(conn);
                System.out.println("Produto deletado com sucesso");
            } else {
                System.out.println("Não existe o produto com o id informado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void menuPostgreSql() {
        System.out.println("==============Gerenciamento de produtos==================");
        System.out.println("Selecione uma opção: ");
        System.out.println("1 - Listar opções");
        System.out.println("2 - Inserir produtos");
        System.out.println("3 - Atualizar produtos");
        System.out.println("4 - Deletar produtos");
        int input = Integer.parseInt(teclado.nextLine());

        switch (input) {
            case 1:
                listar();
                break;
            case 2:
                inserir();
                break;
            case 3:
                atualizar();
                break;
            case 4:
                deletar();
                break;
            default:
                System.exit(-42);
                break;
        }
    }
}
