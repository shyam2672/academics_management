package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Scanner input = new Scanner(System.in);

        System.out.println("Main menu");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Logout");
//        Register f=new Register();
//        f.register();
        int c = 0;
        if (input.hasNextInt()) {
            c = input.nextInt();
        }
        if (c == 1) {
            System.out.print("cfd\n");
            new Register().register();
        } else {
            System.out.print("fuck");
        }
    }
}