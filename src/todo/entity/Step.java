package todo.entity;
import db.Entity;
import db.Trackable;
import java.util.Date;

public class Step extends Entity implements Trackable {
    private String title;
    private Status status;
    private int taskRef;
    private static final int STEP_ENTITY_CODE = 10;
    private Date creationDate;
    private Date lastModificationDate;

    public enum Status{
        NotStarted , Completed;
    }

    public Step(String title , int taskRef){
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    @Override
    public Step copy(){
        Step copyStep = new Step(this.title , this.taskRef);
        copyStep.status = this.status;
        copyStep.id = this.id;
        copyStep.creationDate = new Date(this.creationDate.getTime());
        copyStep.lastModificationDate = new Date(this.creationDate.getTime());

        return copyStep;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date){
        creationDate = date;
    }

    @Override
    public Date getCreationDate(){
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date){
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate(){
        return lastModificationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTaskRef() {
        return taskRef;
    }

    public void setTaskRef(int taskRef) {
        this.taskRef = taskRef;
    }
}
