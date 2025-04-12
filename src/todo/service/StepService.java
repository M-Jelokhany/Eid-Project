package todo.service;
import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class StepService {
    public static void addStep(){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("TaskID : ");
            int taskRef = scanner.nextInt();
            scanner.nextLine();
            if((!(Database.contain(taskRef))) || (!(Database.get(taskRef) instanceof Task))){
                throw new EntityNotFoundException("Cannot find task with ID = " + taskRef);
            }

            System.out.println("Title : ");
            String title = scanner.nextLine();
            if(title.isEmpty()){
                throw new InputMismatchException("Title cannot be empty");
            }

            Step newStep = new Step(title , taskRef);
            Database.add(newStep);

            System.out.println();
            System.out.println("Step saved successfully");
            System.out.println("ID : " + newStep.id);
            System.out.println("Creation Date : " + newStep.getCreationDate());
            System.out.println();
        }
        catch(Exception e){
            System.out.println();
            System.out.println("Cannot save step");
            if(e.getMessage() != null){
                System.out.println("Error : " + e.getMessage());
            }
            else{
                System.out.println("Error : TaskID must be a number");
            }
            System.out.println();
        }
    }

    public static void deleteSteps(int id) throws InvalidEntityException{
        System.out.println();
        System.out.println("Related steps : ");

        boolean notFound = true;
        for (Iterator<Entity> iterator = Database.getEntities().iterator(); iterator.hasNext(); ) {
            Entity x = iterator.next();
            if (x instanceof Step && ((Step) x).getTaskRef() == id) {
                notFound = false;
                System.out.println("Step with title \"" + ((Step) x).getTitle() + "\" and ID = " + x.id + " is deleted");
                iterator.remove();
            }
        }

        if(notFound){
            System.out.println("There were no relevant steps");
        }
    }

    public static void updateStep(){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("ID : ");
            int stepId = scanner.nextInt();
            scanner.nextLine();
            if(!(Database.contain(stepId)) || (!(Database.get(stepId) instanceof Step))){
                throw new EntityNotFoundException("Cannot find step with ID = " + stepId);
            }

            System.out.println("Field (title , status) : ");
            String field = scanner.nextLine();

            Step newStep = (Step) Database.get(stepId);
            switch (field){
                case "title":
                    String oldTitle = newStep.getTitle();

                    System.out.println("New Value : ");
                    newStep.setTitle(scanner.nextLine());

                    Database.update(newStep);

                    System.out.println();
                    System.out.println("Successfully updated the step");
                    System.out.println("Field : title");
                    System.out.println("Old Value : " + oldTitle);
                    System.out.println("New Value : " + newStep.getTitle());
                    System.out.println("Modification Date : " + ((Step) Database.get(stepId)).getLastModificationDate());
                    System.out.println();

                    break;
                case "status":
                    Step.Status oldStatus = newStep.getStatus();

                    System.out.println("New Value (NotStarted , Completed) : ");
                    newStep.setStatus(Step.Status.valueOf(scanner.nextLine()));

                    Database.update(newStep);
                    TaskService.inProgressStatus(newStep.getTaskRef());
                    TaskService.completedStatus(newStep.getTaskRef());

                    System.out.println();
                    System.out.println("Successfully updated the step");
                    System.out.println("Field : status");
                    System.out.println("Old Value : " + oldStatus);
                    System.out.println("New Value : " + newStep.getStatus());
                    System.out.println("Modification Date : " + ((Step) Database.get(stepId)).getLastModificationDate());
                    System.out.println();

                    break;
                default:
                    throw new InputMismatchException("The input field is invalid");
            }
        }catch (Exception e){
            System.out.println();
            System.out.println("Cannot update step");
            if(e.getMessage() != null) {
                System.out.println("Error : " + e.getMessage());
            }
            else{
                System.out.println("Error : ID must be a number");
            }
            System.out.println();
        }
    }

    public static void printSteps(int taskId){
        System.out.println("Steps :");
        boolean notFound = true;
        for(Entity x : Database.getEntities()){
            if((x instanceof Step) && (((Step) x).getTaskRef() == taskId)){
                notFound = false;
                System.out.println("     + " + ((Step) x).getTitle() + " :");
                System.out.println("         ID : " + x.id );
                System.out.println("         Status : " + ((Step) x).getStatus());
                System.out.println();
            }
        }
        if(notFound){
            System.out.println("     No steps found");
            System.out.println();
        }
    }
}
