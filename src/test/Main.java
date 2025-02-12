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
        String path = "E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\archives";
        String path2 = "E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt";

        List<String> perguntas = lerPerguntas(path + "\\formularioExemplo.txt");
        perguntas.forEach(System.out::println);

        User user1 = coletarDados(sc);
        salvarDados(user1, path);

        System.out.println("#===#MENU PRINCIPAL#===#");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Listar todos usuários cadastrados");
        System.out.println("3 - Cadastrar nova pergunta no formulário");
        System.out.println("4 - Deletar pergunta do formulário");
        System.out.println("5 - Pesquisar usuário por nome, idade ou e-mail");
        int resposta = sc.nextInt();

        switch (resposta) {
            case 1: User user2 = coletarDados(sc);
            salvarDados(user2, path);
            case 2:
                listUsers(path);
            case 3:
                mainMenu(path2);


        }
    }
    private static List<String> lerPerguntas(String path) {
        List<String> perguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();

            while (line != null) {
                perguntas.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error in method lerPerguntas: " + e.getMessage());
        }
        return perguntas;
    }
    private static User coletarDados(Scanner sc) {
        System.out.println("#===#Cadastro de Usuário#===#");
        System.out.print("Digite o nome do usuário que você gostaria de cadastrar: ");
        String name = sc.nextLine();
        System.out.print("Digite o e-mail de " + name + ": ");
        String email = sc.nextLine();
        System.out.print("Digite a idade de " + name + ": ");
        Integer age = sc.nextInt();
        System.out.print("Digite a altura de " + name + ": ");
        Double height = sc.nextDouble();
        return new User(name, email, age, height);
    }

    private static String generateArchiveName(String username, String path) {
        /*
         o formulárioExemplo está na mesma pasta onde estes arquivos estão salvos e este métod0 analisa todos da pasta
         para ai sim somar 1 conforme o projeto, qual a melhor solução para resolver isto? poderia simplesmente por eles
         em outra pasta, como deveria criar uma execeção para eles?
         */
        File file = new File(path);
        File[] files = file.listFiles();
        int maxNumber = 0;
        if (files != null) {
            for (File fil : files) {
                String fileName = fil.getName();

                try {
                    int number = Integer.parseInt(fileName.split("-")[0]);
                    if (number > maxNumber) {
                        maxNumber = number;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error in method generateArchiveName: " + e.getMessage());
                }
            }
        }
        int nextNumber = maxNumber + 1;
        return path + "\\" + nextNumber + "-" + username + ".txt";
    }

    private static void salvarDados(User user, String path) {
        String archiveName = generateArchiveName(user.getName().toUpperCase(), path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archiveName))){
            bw.write(user.getName());
            bw.newLine();
            bw.write(user.getEmail());
            bw.newLine();
            bw.write(String.valueOf(user.getAge()));
            bw.newLine();
            bw.write(String.format("%.2f", user.getHeight()));
            bw.newLine();
            bw.flush();
            System.out.println("Cadastro realizado com sucesso em: " + archiveName);
        } catch (IOException e) {
            System.out.println("Error in method salvarDados: " + e.getMessage());
        }
    }

    private static void mainMenu (String path) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a pergunta que você gostaria de adicionar: ");
        String newQuestion = sc.nextLine();

        File file = new File("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt");
        File[] files = file.listFiles();
        int maxNumber = 0;
        if (files != null) {
            for (File question : files) {
                String fileName = question.getName();

                try {
                    int number = Integer.parseInt(fileName.split("-")[0]);
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error in method mainMenu: " + e.getMessage());
                }
            }
        }


        try (BufferedReader br = new BufferedReader(new FileReader("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt"))){
            String line = br.readLine();

            while (line != null ){
                System.out.println(line);
                line = br.readLine();
            }


        } catch (IOException e) {
            System.out.println("Error in method mainMenu: " + e.getMessage());
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt", true))){
            bw.newLine();
            bw.write(maxNumber + " - " + newQuestion);
        } catch (IOException e) {
            System.out.println("Error in method mainMenu: " + e.getMessage());
        }

        /*
        System.out.println("#===#MENU PRINCIPAL#===#");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Listar todos usuários cadastrados");
        System.out.println("3 - Cadastrar nova pergunta no formulário");
        System.out.println("4 - Deletar pergunta do formulário");
        System.out.println("5 - Pesquisar usuário por nome, idade ou e-mail");
        int resposta = sc.nextInt();

        switch (resposta) {
            case 1: User user2 = coletarDados(sc);
                salvarDados(user2, path);
            case 2:
                listUsers(path);
            case 3:
                System.out.println("Digite a pergunta que você gostaria de cadastrar: ");
                String newQuestion2 = sc.nextLine();
        }
         */
    }

    private static void listUsers (String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        if (files != null) {
            System.out.println("Usuários cadastrados até o momento: ");
            for (File fil : files) {
                String fileName = fil.getName();
                if (fileName.matches("^\\d+-.*\\.txt$"));
                String username = fileName.substring(fileName.indexOf("-") + 1, fileName.lastIndexOf("."));
                System.out.println(username);
            }
        }
    }
}
