package features;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by @kash on 2/18/2018.
 */
public class Issue implements Weightable, Comparable<Issue>{
    private Calendar created_at;
    private Title title;
    private List<Comment> comments;
    private AuthorAssociation author_assoc;
    private List<Label> labels;
    private List<Assignee> assignees;
    private long id;
    private long number;
    private String body;
    double weight;

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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Issue issue) {
        double diff = issue.weight - this.weight;
        if(diff > 0)
            return 1;
        else if(diff == 0)
            return 0;
        else
            return -1;
    }

    @Override
    public double getWeight() {
        double weight = 0;
        Calendar today = Calendar.getInstance();
        long days = today.get(Calendar.DAY_OF_MONTH) - created_at.get(Calendar.DAY_OF_MONTH);
        if(days == 0)
            days += 1;
        System.out.println("Issue [" + getNumber() + "]");
        System.out.println("Time in days: " + days);
        System.out.println("\ttitle\tauthor\tassignee\tlabels\tcomment\t\ttotal");

        DecimalFormat format = new DecimalFormat("#.00");
        //Adding title and author weight
        double title_weight = title.getWeight();
        title_weight /= Math.log10(days + 1);
        System.out.print("\t\t" + format.format(title_weight));

        double author_weight = author_assoc.getWeight();
        author_weight /= Math.log(days + 1);
        System.out.print("\t\t" + format.format(author_weight));

        //assignee weight
        double assignee_weight = 0;
        for(Assignee assignee: assignees)
            assignee_weight += assignee.getWeight();
        assignee_weight /= Math.log10(days + 1);
        System.out.print("\t\t" + format.format(assignee_weight));

        //calculating label weight
        double labels_weight = 0;
        for(Label label: labels)
            labels_weight  += label.getWeight();
        labels_weight /= Math.log10(days + 1);
        System.out.print("\t\t" + format.format(labels_weight));

        //Adding weight of comments
        double comment_weight = 0;
        for(Comment comment: comments)
            comment_weight += comment.getWeight();
        System.out.print("\t\t" + format.format(comment_weight));

        weight += title_weight + author_weight + assignee_weight + labels_weight + comment_weight;
        System.out.println("\t\t" + format.format(weight));
        return weight;
    }
}
