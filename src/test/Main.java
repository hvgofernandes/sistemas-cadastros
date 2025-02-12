package test;

import entities.User;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        String path = "E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\archives";

        List<String> perguntas = lerPerguntas(path + "\\mainMenuQuestions.txt");
        perguntas.forEach(System.out::println);

        try {
            User user1 = coletarDados(sc);
            salvarDados(user1, path);
        } catch (Exception e) {
            System.out.println("Error in catch void main: " + e.getMessage());
        }
        mainMenu(path);

        sc.close();
    }

    private static List<String> lerPerguntas(String path) {
        List<String> perguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt"))) {
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

    private static User coletarDados(Scanner sc) throws Exception{
        System.out.println("#===#Cadastro de Usuário#===#");

        System.out.print("Digite o nome do usuário que você gostaria de cadastrar: ");
        String name = sc.nextLine();
        nameValidator(name);

        System.out.print("Digite o e-mail de " + name + ": ");
        String email = sc.nextLine();
        emailValidator(email);
        registeredEmails.add(email);

        System.out.print("Digite a idade de " + name + ": ");
        Integer age = sc.nextInt();
        ageValidator(age);

        System.out.print("Digite a altura de " + name + ": ");
        Double height = sc.nextDouble();
        heightValidator(height);

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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archiveName))) {
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

    private static void mainMenu(String path) {
        Scanner sc = new Scanner(System.in);
        List<String> perguntas = lerPerguntas(path);
        System.out.println("MENU PRINCIPAL:");
        perguntas.forEach(System.out::println);

        int resposta = sc.nextInt();
        sc.nextLine();

        switch (resposta) {
            case 1:
                try {
                    User user = coletarDados(sc);
                    salvarDados(user, path);
                } catch (Exception e) {
                    System.out.println("Error in switch case 1: " + e.getMessage());
                }
                break;
            case 2:
                listUsers(path);
                break;
            case 3:
                newQuestion("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt");
                break;
            case 4:
                deleteQuestion("E:\\Documents\\diasante\\ws-intellij\\desafio\\sistemas-cadastros\\src\\util\\archives\\mainMenuQuestions.txt");
                break;
            case 5:
                search(path);
                break;
            default:
                System.out.println("xD");
        }

    }

    private static void listUsers(String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        if (files != null) {
            System.out.println("Usuários cadastrados até o momento: ");
            for (File fil : files) {
                String fileName = fil.getName();
                if (fileName.matches("^\\d+-.*\\.txt$")) {
                    String username = fileName.substring(fileName.indexOf("-") + 1, fileName.lastIndexOf("."));
                    System.out.println(username);
                }
            }
        }
    }

    private static void newQuestion(String path) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a pergunta que você gostaria de adicionar: ");
        String newQuestion = sc.nextLine();
        int maxNumber = 0;
        List<String> perguntas = new ArrayList<>();

        String fileName = path;
        File file = new File(fileName);
        System.out.println("Verificando caminho: " + file.getAbsolutePath());
        System.out.println("Arquivo existe? " + file.exists());
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();


            while (line != null) {
                perguntas.add(line);
                if (line.matches("^\\d+ - .*")) {
                    int number = Integer.parseInt(line.split("\\s+-\\s+")[0]);
                    maxNumber = Math.max(maxNumber, number);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error in method new Question: " + e.getMessage());
        }
        String newQuestionFormatted = (maxNumber + 1) + " - " + newQuestion;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.newLine();
            bw.write(newQuestionFormatted);
            bw.flush();
            System.out.println("Pergunta adicionada com sucesso!");
        } catch (IOException e) {
            System.out.println("Error in method newQuestion: " + e.getMessage());
        }
    }

    private static void deleteQuestion(String path) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o número da pergunta que você gostaria de deletar (não é possível apagar as 4 primeiras pergunta): ");
        String numberQuestionDelete = sc.nextLine();

        List<String> perguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            int lineNumber = 1;

            while (line != null) {
                if (line.contains(numberQuestionDelete) && lineNumber > 4) {
                    System.out.println("Pergunta removida: " + line);
                } else {
                    perguntas.add(line);
                }
                lineNumber++;
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error in method deleteQuestion: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String pergunta : perguntas) {
                bw.write(pergunta);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error in method deleteQuestion: " + e.getMessage());
        }
    }

    private static void search(String path) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o nome de quem você deseja buscar: ");
        String searchFor = sc.nextLine().toLowerCase();
        File file = new File(path);
        File[] files = file.listFiles();

        if (files != null) {
            for (File fil : files) {
                String fileName = fil.getName().toLowerCase();
                if (fileName.contains(searchFor)) {
                    String name = fileName.substring(fileName.indexOf("-") + 1, fileName.lastIndexOf("."));
                    System.out.println(name);
                }
            }
        }
        sc.close();
    }

    private static void nameValidator(String name) throws Exception {
        if (name.length() < 10) {
            throw new Exception("Nome deve ter pelo menos 10 caracteres.");
        }
    }

    private static final Set<String> registeredEmails = new HashSet<>();

    private static void emailValidator(String email) throws Exception {
        if (!email.contains("@")) {
            throw new Exception("E-mail deve conter @");
        }
        if (registeredEmails.contains(email)) {
            throw new Exception("E-mail já cadastrado!");
        }
    }

    private static void ageValidator(Integer age) throws Exception {
        if (age < 18) {
            throw new Exception("Para se cadastrar é necessário ter 18 anos ou mais.");
        }
    }

    private static void heightValidator(Double height) throws Exception {
        String sHeight = Double.toString(height);
        if (!sHeight.contains(".")) {
            throw new Exception("Altura inválida. Utilize o ponto (.) ou (,)");
        }
    }
}
