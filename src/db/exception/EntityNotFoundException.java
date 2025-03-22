package db.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(){
        super("Cannot find entity");
    }

    public EntityNotFoundException(String massage){
        super(massage);
    }

    public EntityNotFoundException(int id){
        super("Cannot find entity with id = " + id);
    }
}
