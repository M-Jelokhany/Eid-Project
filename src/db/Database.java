package db;
import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int number = 1;

    private Database(){
    }

    public static void add(Entity e){
        e.id = number;
        entities.add(e.copy());
        number += 1;
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
                entities.remove(x);
                notFound = false;
            }
        }
        if(notFound) {
            throw new EntityNotFoundException(id);
        }
    }

    public static void update(Entity e){
        boolean notFound = true;
        for(Entity x : entities){
            if(x.id == e.id){
                entities.set(entities.indexOf(x) , e.copy());
                notFound = false;
            }
        }
        if(notFound){
            throw new EntityNotFoundException(e.id);
        }
    }
}
