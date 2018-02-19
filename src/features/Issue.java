package features;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by @kash on 2/18/2018.
 */
public class Issue implements Weightable{
    private Calendar created_at;
    private Title title;
    private List<Comment> comments;
    private AuthorAssociation author_assoc;
    private List<Label> labels;
    private List<Assignee> assignees;
    private long id;
    private long number;
    private String body;

    public Issue(long id, long number, String created_at, String title, List<Comment> comments, List<Assignee> assignees, List<Label> labels, String author_assoc, String body)  {
        this.title = new Title(title);
        this.assignees = assignees;
        this.labels = labels;
        this.author_assoc = new AuthorAssociation(author_assoc);
        this.id = id;
        this.number = number;
        int year = Integer.parseInt(created_at.substring(0,4));
        int month = Integer.parseInt(created_at.substring(5,7));
        int days = Integer.parseInt(created_at.substring(8,10));
        this.created_at = Calendar.getInstance();
        this.created_at.set(year, month, days);
        this.body = body;
        this.comments = comments;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public AuthorAssociation getAuthor_assoc() {
        return author_assoc;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public double getWeight() {
        double weight = 0;
        weight += title.getWeight() + author_assoc.getWeight();
        for(Assignee assignee: assignees)
            weight += assignee.getWeight();
        for(Comment comment: comments)
            weight += comment.getWeight();
        return weight;
    }
}
