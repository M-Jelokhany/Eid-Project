package example;
import db.Entity;
import db.Trackable;
import java.util.Date;

public class Document extends Entity implements Trackable {
    public String content;
    public static final int DOCUMENT_ENTITY_CODE = 11;
    private Date creationDate;
    private Date lastModificationDate;

    public Document(String content){
        this.content = content;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    @Override
    public Document copy() {
        Document copyDocument = new Document(content);
        copyDocument.id = this.id;
        copyDocument.creationDate = new Date(this.creationDate.getTime());
        copyDocument.lastModificationDate = new Date(this.creationDate.getTime());

        return copyDocument;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;
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
}
