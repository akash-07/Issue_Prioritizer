package features;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by @kash on 2/18/2018.
 */
public class Comment implements Weightable{
     private Calendar created_at;
     private AuthorAssociation author;
     private String body;
     private long issue_id;
     private long id;

     public Comment(String date, String author, long issue_id, long id, String body)  {
         this.author = new AuthorAssociation(author);
         int year = Integer.parseInt(date.substring(0,4));
         int month = Integer.parseInt(date.substring(5,7));
         int day = Integer.parseInt(date.substring(8,10));
         this.created_at = Calendar.getInstance();
         created_at.set(year, month, day);
         this.issue_id = issue_id;
         this.id = id;
         this.body = body;
     }

    public AuthorAssociation getAuthor() {
        return author;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public long getIssue_id() {
        return issue_id;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    //Write this method for weight
    @Override
    public double getWeight() {
         Calendar today = Calendar.getInstance();
         long diff = today.getTimeInMillis() - created_at.getTimeInMillis();
         long days = TimeUnit.MILLISECONDS.toDays(diff);
         double weight = author.getWeight() + 1/Math.log(days + 1);
         return weight;
    }
}
