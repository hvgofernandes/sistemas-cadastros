package test;

import entities.User;
import java.util.Locale;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<String> perguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\archives\\formularioExemplo.txt"))){
            String line = br.readLine();

            while (line != null) {
                System.out.println(line);
                perguntas.add(line);
                line = br.readLine();
            }
            /* for (String perg : perguntas) {
                System.out.println(perg);
            }*/

            // PASSO 1 CONCLUÍDO!
            System.out.println("#===#Cadastro de Usuário#===#");
            System.out.print("Digite o nome do usuário que você gostaria de cadastrar: ");
            String name = sc.nextLine();
            System.out.print("Digite o e-mail de " + name + ": ");
            String email = sc.nextLine();
            System.out.print("Digite a idade de " + name + ": ");
            Integer age = sc.nextInt();
            System.out.print("Digite a altura de " + name + ": ");
            Double height = sc.nextDouble();
            User user = new User(name, email, age, height);
            // Seria mais eficiente transformar isso tudo em métod0, se sim, de qual maneira?
            // PASSO 2 CONCÚIDO!
            String archives = "E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\archives";
            String archiveName = archives + "1-" + name + ".txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(archiveName));
            // BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\archives\\formulario.txt", true));
            bw.write(name);
            bw.newLine();
            bw.write(email);
            bw.newLine();
            bw.write(String.valueOf(age));
            bw.newLine();
            bw.write(String.valueOf(height)); // Ver como posso deixar com duas casas decimais!
            bw.newLine();
            bw.newLine();


            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }

    }
}
