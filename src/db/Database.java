package db;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.service.StepService;
import java.util.*;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int number = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database(){
    }

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if(validator != null) {
            validator.validate(e);
        }

        e.id = number;
        number += 1;
        if(e instanceof Trackable){
            ((Trackable) e).setCreationDate(new Date());
            ((Trackable) e).setLastModificationDate(new Date());
        }

        entities.add(e.copy());
    }

    public static Entity get(int id){
        for(Entity x : entities){
            if(x.id == id){
                return x.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id){
        boolean notFound = true;
        for(Entity x : entities){
            if(x.id == id){
                notFound = false;
                entities.remove(x);
                break;
            }
        }
        if(notFound) {
            throw new EntityNotFoundException(id);
        }
    }

    public static void update(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if(validator != null) {
            validator.validate(e);
        }

        if(e instanceof Trackable) {
            ((Trackable) e).setLastModificationDate(new Date());
        }

        boolean notFound = true;
        for(Entity x : entities){
            if(x.id == e.id){
                entities.set(entities.indexOf(x) , e.copy());
                notFound = false;
                break;
            }
        }

        if(notFound){
            throw new EntityNotFoundException(e.id);
        }
    }

    public static void registerValidator(int entityCode , Validator validator) {
        if(validators.containsKey(entityCode)){
            throw new IllegalArgumentException("This validator has already been added to the hashmap validators");
        }

        validators.put(entityCode , validator);
    }

    public static ArrayList<Entity> getEntities(){
        return entities;
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> entities = new ArrayList<>();
        for(Entity x : Database.entities){
            if(x.getEntityCode() == entityCode){
                entities.add(x);
            }
        }

        return entities;
    }

    public static boolean contain(int id){
        boolean notFound = true ;
        for(Entity x : entities){
            if(x.id == id){
                notFound = false;
                break;
            }
        }

        return !notFound;
    }

    public static void deleteEntity(){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("ID : ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if(!(Database.contain(id))){
                throw new EntityNotFoundException("Cannot delete entity with ID = " + id + "\n" + "Error : Cannot find entity with ID = " + id);
            }
            if(Database.get(id) instanceof Task){
                StepService.deleteSteps(id);
                Database.delete(id);
                System.out.println();
                System.out.println("Entity with ID = " + id + " successfully deleted");
                System.out.println();
            }
            else{
                Database.delete(id);
                System.out.println();
                System.out.println("Entity with ID = " + id + " successfully deleted");
                System.out.println();
            }
        }catch (Exception e){
            System.out.println();
            if(e.getMessage() != null){
                System.out.println(e.getMessage());
            }
            else{
                System.out.println("Error : ID must be a number");
            }
            System.out.println();
        }
    }
}


