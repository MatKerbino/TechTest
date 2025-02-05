package org.example;

import java.util.Scanner;

import static org.example.PostgreSqlService.Utils.menuPostgreSql;
import static org.example.mySqlService.Utils.menuMySql;

public class Main {

    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Selecione um banco de dados: ");
        System.out.println("1 - MySql");
        System.out.println("2 - PostgreSql");
        int num = Integer.parseInt(teclado.nextLine());

        switch (num) {
            case 1:
                menuMySql();
                break;
            case 2:
                menuPostgreSql();
                break;
            default:
                System.exit(-42);
                break;
        }
    }
}