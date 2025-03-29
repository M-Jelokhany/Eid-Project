import db.Database;
import db.exception.InvalidEntityException;
import todo.service.StepService;
import todo.service.TaskService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        do {
            System.out.println("Enter command (add task , add step , delete , update task , update step , get task-by-id , get all-tasks , get incomplete-tasks , exit) : ");
            String command = scanner.nextLine();

            switch (command) {
                case "exit":
                    System.out.println("Exiting the program");
                    exit = true;
                    break;
                case "add task":
                    TaskService.addTask();
                    break;
                case "add step":
                    StepService.addStep();
                    break;
                case "delete":
                    Database.deleteEntity();
                    break;
                case "update task":
                    TaskService.updateTask();
                    break;
                case "update step":
                    StepService.updateStep();
                    break;
                case "get task-by-id":
                    TaskService.getTaskById();
                    break;
                case "get all-tasks":
                    TaskService.getAllTasks();
                    break;
                case "get incomplete-tasks":
                    TaskService.getIncompleteTasks();
                    break;
                default:
                    System.out.println();
                    System.out.println("Command is Invalid . Please try again");
                    System.out.println();
                    break;
            }
        } while (!exit);
    }
}