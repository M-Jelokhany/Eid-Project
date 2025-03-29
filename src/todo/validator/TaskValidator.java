package todo.validator;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException{
        if(!(entity instanceof Task)){
            throw new IllegalArgumentException("The input entity must be of the Task class");
        }

        if((((Task) entity).getTitle() == null) || (((Task) entity).getTitle().isEmpty())){
            throw new InvalidEntityException("Title is Invalid");
        }
    }
}
