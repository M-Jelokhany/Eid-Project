package example;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;


public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Human)){
            throw new IllegalArgumentException("The input entity must be of the Human class");
        }
        if(((Human)entity).age < 0){
            throw new InvalidEntityException("Age must be a positive integer");
        } else if ((((Human)entity).name == null) || (((Human)entity).name.isEmpty())) {
            throw new InvalidEntityException("Name is Invalid");
        }
    }
}
