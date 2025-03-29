package todo.validator;
import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Step)){
            throw new IllegalArgumentException("The input entity must be of the Task class");
        }

        if((((Step) entity).getTitle() == null) || (((Step) entity).getTitle().isEmpty())){
            throw new InvalidEntityException("Title is Invalid");
        }

        boolean notFound = true;
        for(Entity x : Database.getEntities()){
            if (x.id == ((Step) entity).getTaskRef()) {
                notFound = false;
                break;
            }
        }
        if(notFound){
            throw new InvalidEntityException("TaskRef is Invalid");
        }
    }
}
