package features;

import analyzer.Config;

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
            weight = 0;
        else if(author_assoc.equals("CONTRIBUTOR"))
            weight = 1;
        else if(author_assoc.equals("MEMBER") || author_assoc.equals("OWNER"))
            weight = 1.5;
        else
            weight = 0;
            //throw new Error("author association undentified.");
        int base = Config.AUTHOR_ASSOC_BASE;
        //weight *= Math.log10(2);
        return weight;
    }

    @Override
    public String toString() {
        return author_assoc;
    }
}
