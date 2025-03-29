package todo.entity;
import db.Entity;
import db.Trackable;
import java.util.Date;

public class Task extends Entity implements Trackable {
    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private static final int TASK_ENTITY_CODE = 13;
    private Date creationDate;
    private Date lastModificationDate;

    public enum Status{
        NotStarted , InProgress , Completed;
    }

    public Task(String title , String description , Date dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.NotStarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    @Override
    public Task copy(){
        Task copyTask = new Task(this.title , this.description , this.dueDate);
        copyTask.status = this.status;
        copyTask.id = this.id;
        copyTask.creationDate = new Date(this.creationDate.getTime());
        copyTask.lastModificationDate = new Date(this.creationDate.getTime());

        return copyTask;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
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

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate(){
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
