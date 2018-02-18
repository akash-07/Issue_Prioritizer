package features;

/**
 * Created by @kash on 2/18/2018.
 */
public class Title implements Weightable{
    String title;

    Title(String title) {
        this.title = title;
    }

    @Override
    public double getWeight() {
        if(title.contains("fix") || title.contains("bug"))
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return title;
    }
}
