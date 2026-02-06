package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String[] MENU = {"add", "remove", "list", "exit"};
    static String[][] TASKS;
    static Scanner scanner = new Scanner(System.in);
    static Validation VALIDATION = new Validation();
    static String FILE_NAME = "tasks.csv";

    public static void main(String[] args) {

        readFile(FILE_NAME);
        boolean flag = true;
        printMenu();
        while (flag) {
            flag = menuOption(scanner.nextLine());
        }
    }

    public static boolean menuOption(String option) {

        switch (option) {
            case "add":
                addTask();
                System.out.println("Task has been added.");
                printMenu();
                return true;
            case "list":
                listAllTasks();
                printMenu();
                return true;
            case "remove":
                removeTask();
                printMenu();
                return true;
            case "exit":
                exitTaskManager();
                System.out.println(ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET);
                return false;
        }
        return true;
    }

    private static void exitTaskManager() {

        try (FileWriter fw = new FileWriter(FILE_NAME);) {
            for (String[] task : TASKS) {
                for (String s : task) {
                    fw.write(s);
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error during writing data to file.");
        }
    }

    private static void removeTask() {

        System.out.println("Please select number to remove.");
        String taskNumToRemove = scanner.next();
        while (!VALIDATION.numberToRemoveValidation(taskNumToRemove, TASKS)) {
            taskNumToRemove = scanner.next();
        }

        int taskNumToRemoveInt = Integer.parseInt(taskNumToRemove);
        TASKS = ArrayUtils.remove(TASKS, taskNumToRemoveInt);

        System.out.println("Value was successfully deleted.");
    }

    private static void listAllTasks() {

        for (int row = 0; row < TASKS.length; row++) {
            System.out.print(row + ": ");
            for (int col = 0; col < TASKS[0].length; col++) {
                System.out.print(TASKS[row][col]);
            }
            System.out.println();
        }
    }

    public static void readFile(String fileName) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            int row = 0;
            int countlines = (int) Files.lines(Paths.get(fileName)).count();
            TASKS = new String[countlines][];

            while (bufferedReader.ready()) {
                String[] splittedline = bufferedReader.readLine().split(",");
                TASKS[row] = splittedline;
                row++;
            }
        } catch (IOException e) {
            System.out.println("Error during file read.");
        }
    }


    public static void printMenu() {
        System.out.println("Please select an option:");
        for (String menuOption : MENU) {
            System.out.println(menuOption);
        }
    }

    public static void addTask() {

        System.out.println("Please add task description");
        String taskDescription = scanner.nextLine();
        while (!VALIDATION.descriptionValidation(taskDescription)) {
            taskDescription = scanner.nextLine();
        }

        System.out.println("Please add task due date");
        String dueDateInput = scanner.nextLine();
        while (!VALIDATION.dateValidation(dueDateInput)) {
            dueDateInput = scanner.nextLine();
        }

        System.out.println("Is your task important: true/false");
        String importance = scanner.nextLine();
        while (!VALIDATION.importanceOfValidation(importance)) {
            importance = scanner.nextLine();
        }

        TASKS = Arrays.copyOf(TASKS, TASKS.length + 1);

        for (int row = TASKS.length - 1; row >= 0; row--) {
            TASKS[TASKS.length - 1] = new String[]
                    {taskDescription.concat(" "), dueDateInput.concat(" "), importance};
        }
    }

}
