package features;

/**
 * Created by @kash on 2/18/2018.
 */
public class Label implements Weightable{
    private String name;
    private long id;
    private long issue_id;

    public Label(String name, long id, long issue_id) {
        this.id = id;
        this.issue_id = issue_id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getIssue_id() {
        return issue_id;
    }

    @Override
    public double getWeight() {
        return 1;
    }
}
