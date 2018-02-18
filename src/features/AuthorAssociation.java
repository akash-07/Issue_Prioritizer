package features;

/**
 * Created by @kash on 2/18/2018.
 */
public class AuthorAssociation implements Weightable{
    String author_assoc;
    AuthorAssociation(String author_assoc)  {
        this.author_assoc = author_assoc;
    }

    // Assign weights here
    @Override
    public double getWeight() {
        double weight = 0;
        if(author_assoc.equals("NONE"))
            weight = 1;
        else if(author_assoc.equals("CONTRIBUTOR"))
            weight = 1.5;
        else if(author_assoc.equals("MEMBER"))
            weight = 2;
        else
            throw new Error("author association undentified.");
        //Unreachable statement
        return weight;
    }

    @Override
    public String toString() {
        return author_assoc;
    }
}
