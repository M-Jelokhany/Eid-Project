package todo.service;
import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskService {
    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task newTask = (Task) Database.get(taskId);
        newTask.setStatus(Task.Status.Completed);
        Database.update(newTask);
    }

    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Task newTask = (Task) Database.get(taskId);
        newTask.setStatus(Task.Status.InProgress);
        Database.update(newTask);
    }

    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        Task newTask = (Task) Database.get(taskId);
        newTask.setStatus(Task.Status.NotStarted);
        Database.update(newTask);
    }

    public static void addTask(){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Title : ");
            String title = scanner.nextLine();
            if(title.isEmpty()){
                throw new InputMismatchException("Title cannot be empty");
            }

            System.out.println("Description : ");
            String description = scanner.nextLine();
            if(description.isEmpty()){
                throw new InputMismatchException("Description cannot be empty");
            }

            System.out.println("Due date(yyyy-mm-dd) : ");
            String dueDateInput = scanner.nextLine();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = formatter.parse(dueDateInput);

            Task newTask = new Task(title , description , dueDate);
            Database.add(newTask);

            System.out.println();
            System.out.println("Task saved successfully");
            System.out.println("ID : " + newTask.id);
            System.out.println();
        }
        catch (ParseException e) {
            System.out.println();
            System.out.println("Cannot sava task");
            System.out.println("Error : Invalid date format . Please use yyyy-mm-dd");
            System.out.println();
        }
        catch (Exception e){
            System.out.println();
            System.out.println("Cannot save task");
            System.out.println("Error : " + e.getMessage());
            System.out.println();
        }
    }

    public static void updateTask() throws InvalidEntityException {
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("ID : ");
            int taskId = scanner.nextInt();
            scanner.nextLine();
            if(!(Database.contain(taskId)) || (!(Database.get(taskId) instanceof Task))){
                throw new EntityNotFoundException("Cannot find task with ID = " + taskId);
            }

            System.out.println("Field (title , description , dueDate , status) : ");
            String field = scanner.nextLine();

            Task newTask = (Task) Database.get(taskId);
            switch (field){
                case "title":
                    String oldTitle = newTask.getTitle();

                    System.out.println("New Value : ");
                    newTask.setTitle(scanner.nextLine());

                    Database.update(newTask);

                    System.out.println();
                    System.out.println("Successfully updated the task");
                    System.out.println("Field : title");
                    System.out.println("Old Value : " + oldTitle);
                    System.out.println("New Value : " + newTask.getTitle());
                    System.out.println("Modification Date : " + ((Task) Database.get(taskId)).getLastModificationDate());
                    System.out.println();

                    break;
                case "description":
                    String oldDescription = newTask.getDescription();

                    System.out.println("New Value : ");
                    newTask.setDescription(scanner.nextLine());

                    Database.update(newTask);

                    System.out.println();
                    System.out.println("Successfully updated the task");
                    System.out.println("Field : description");
                    System.out.println("Old Value : " + oldDescription);
                    System.out.println("New Value : " + newTask.getDescription());
                    System.out.println("Modification Date : " + ((Task) Database.get(taskId)).getLastModificationDate());
                    System.out.println();

                    break;
                case "dueDate":
                    Date oldDueDate = newTask.getDueDate();

                    System.out.println("New Value : ");
                    String dueDateInput = scanner.nextLine();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    newTask.setDueDate(formatter.parse(dueDateInput));

                    Database.update(newTask);

                    System.out.println();
                    System.out.println("Successfully updated the task");
                    System.out.println("Field : dueDate");
                    System.out.println("Old Value : " + oldDueDate);
                    System.out.println("New Value : " + newTask.getDueDate());
                    System.out.println("Modification Date : " + ((Task) Database.get(taskId)).getLastModificationDate());
                    System.out.println();

                    break;
                case "status":
                    Task.Status oldStatus = newTask.getStatus();

                    System.out.println("New Value (NotStarted , InProgress , Completed) : ");
                    newTask.setStatus(Task.Status.valueOf(scanner.nextLine()));

                    Database.update(newTask);

                    System.out.println();
                    System.out.println("Successfully updated the task");
                    System.out.println("Field : status");
                    System.out.println("Old Value : " + oldStatus);
                    System.out.println("New Value : " + newTask.getStatus());
                    System.out.println("Modification Date : " + ((Task) Database.get(taskId)).getLastModificationDate());
                    System.out.println();

                    break;
                default:
                    throw new InputMismatchException("The input field is invalid");
            }
        } catch (ParseException e) {
            System.out.println();
            System.out.println("Cannot update task");
            System.out.println("Error : Invalid date format . Please use yyyy-mm-dd");
            System.out.println();
        } catch (Exception e){
            System.out.println();
            System.out.println("Cannot update task");
            System.out.println("Error : " + e.getMessage());
            System.out.println();
        }
    }

    public static void completedStatus(int taskRef) throws InvalidEntityException {
        boolean isCompleted = true;
        for(Entity x : Database.getEntities()){
            if((x instanceof Step) && (((Step) x).getTaskRef() == taskRef) && (((Step) x).getStatus() == Step.Status.NotStarted)){
                isCompleted = false;
                break;
            }
        }

        if(isCompleted){
            Task newTask = (Task) Database.get(taskRef);
            newTask.setStatus(Task.Status.Completed);
            Database.update(newTask);
            System.out.println();
            System.out.println("The task titled \"" + newTask.getTitle() + "\" is completed because its steps have been completed");
        }
    }

    public static void inProgressStatus(int taskRef) throws InvalidEntityException{
        boolean isNotStarted = true;
        for(Entity x : Database.getEntities()){
            if((x instanceof Step) && (((Step) x).getTaskRef() == taskRef) && (((Step) x).getStatus() == Step.Status.Completed)){
                isNotStarted = false;
                break;
            }
        }

        if(!isNotStarted && ((Task)Database.get(taskRef)).getStatus() != Task.Status.Completed && ((Task)Database.get(taskRef)).getStatus() != Task.Status.InProgress){
            Task newTask = (Task) Database.get(taskRef);
            newTask.setStatus(Task.Status.InProgress);
            Database.update(newTask);
            System.out.println();
            System.out.println("The status of the task titled \"" + newTask.getTitle() + "\" changes to \"In Progress\" because one of its steps is completed");
        }
    }

    public static void printFieldsOfTask(int taskId){
        System.out.println();
        System.out.println("ID : " + taskId);
        System.out.println("Title : " + ((Task) Database.get(taskId)).getTitle());
        System.out.println("Due Date : " + ((Task) Database.get(taskId)).getDueDate());
        System.out.println("Status : " + ((Task) Database.get(taskId)).getStatus());
    }

    public static void getTaskById(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("ID : ");
            int taskId = scanner.nextInt();
            scanner.nextLine();
            if(!(Database.contain(taskId)) || (!(Database.get(taskId) instanceof Task))){
                throw new EntityNotFoundException("Cannot find task with ID = " + taskId);
            }

            printFieldsOfTask(taskId);
            StepService.printSteps(taskId);
        }catch (Exception e){
            if(e.getMessage() != null) {
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }
            else{
                System.out.println();
                System.out.println("Error : ID must be a number");
                System.out.println();
            }
        }
    }

    public static void getAllTasks(){
        ArrayList<Task> taskEntities = new ArrayList<>();

        boolean notFound = true;
        for(Entity x : Database.getEntities()){
            if(x instanceof Task){
                notFound = false;
                taskEntities.add((Task) x);
            }
        }

        taskEntities.sort(Comparator.comparing(Task::getDueDate));

        for(Task x : taskEntities){
            printFieldsOfTask(x.id);
            StepService.printSteps(x.id);
        }

        if(notFound){
            System.out.println();
            System.out.println("No tasks were found");
            System.out.println();
        }
    }

    public static void getIncompleteTasks(){
        ArrayList<Task> taskEntities = new ArrayList<>();
        for(Entity x : Database.getEntities()){
            if(x instanceof Task){
                taskEntities.add((Task) x);
            }
        }

        taskEntities.sort(Comparator.comparing(Task::getDueDate));

        boolean notFound = true;
        for(Task x : taskEntities){
            if(x.getStatus() != Task.Status.Completed) {
                notFound = false;
                printFieldsOfTask(x.id);
                StepService.printSteps(x.id);
            }
        }

        if(notFound){
            System.out.println();
            System.out.println("No incomplete tasks were found");
            System.out.println();
        }
    }
}
