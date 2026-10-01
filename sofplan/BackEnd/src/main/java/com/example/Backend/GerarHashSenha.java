package com.example.Backend;

import java.util.Scanner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHashSenha {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Digite uma nova senha: ");
            String senha = scanner.nextLine();
            System.out.println(new BCryptPasswordEncoder().encode(senha));
        }
    }
}