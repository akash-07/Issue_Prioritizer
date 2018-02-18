package features;

/**
 * Created by @kash on 2/18/2018.
 */
public class Assignee implements Weightable{
    int weight;

    public Assignee()  {
        weight = 1;
    }
    @Override
    public double getWeight() {
        return weight;
    }
}
