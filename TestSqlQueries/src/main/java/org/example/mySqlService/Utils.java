package org.example.mySqlService;

import java.sql.*;
import java.util.Scanner;

public class Utils {

    /**
     * SSL, ou Secure Sockets Layer, é um protocolo de segurança que estabelece uma conexão criptografada entre um servidor web e um navegador.
     * Desenvolvido pela Netscape em 1995, o SSL foi criado para garantir a privacidade, autenticidade e integridade dos dados transmitidos na internet,
     * especialmente durante transações que envolvem informações sensíveis, como dados pessoais e financeiros
     */

    static Scanner tec = new Scanner(System.in);

    public static Connection conectar(){
        String CLASSE_DRIVER = "com.mysql.jdbc.Driver";
        String USUARIO = "Kerbino";
        String SENHA = "1234";
        String URL_SERVIDOR = "jdbc:mysql://localhost:3306/jmysql?useSSL=false";

        try {
            Class.forName(CLASSE_DRIVER);
            return DriverManager.getConnection(URL_SERVIDOR, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
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
            PreparedStatement produtos = conn.prepareStatement(BUSCAR_TODOS);
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
        String nome = tec.nextLine();

        System.out.println("Informe o preço do produto: ");
        float preco = tec.nextFloat();

        System.out.println("Informe a quantidade em estoque: ");
        int estoque = tec.nextInt();

        String INSERIR = "INSERT INTO produtos (nome, preco, estoque) VALUES (?, ?, ?)";
        //Prevenção de SQL Injection
        try {
            Connection conn = conectar();
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

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
        int id = Integer.parseInt(tec.nextLine());

        String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id=?";

        try {
            Connection conn = conectar();
            PreparedStatement produto = conn.prepareStatement(BUSCAR_POR_ID);
            produto.setInt(1, id);
            ResultSet res = produto.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                System.out.println("Informe o nome do produto: ");
                String nome = tec.nextLine();

                System.out.println("Informe o preço do produto: ");
                float preco = tec.nextFloat();

                System.out.println("Informe a quantidade em estoque: ");
                int estoque = tec.nextInt();

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
        int id = Integer.parseInt(tec.nextLine());

        String DELETAR_POR_ID = "DELETE FROM produtos WHERE id=?";
        String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id=?";

        System.out.println("Informe o código do produto: ");
        try {
            Connection conn = conectar();
            PreparedStatement produto = conn.prepareStatement(BUSCAR_POR_ID);
            produto.setInt(1, id);
            ResultSet res = produto.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(BUSCAR_POR_ID);
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
}
