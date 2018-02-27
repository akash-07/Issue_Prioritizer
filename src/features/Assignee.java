package features;

import analyzer.Config;

/**
 * Created by @kash on 2/18/2018.
 */
public class Assignee implements Weightable{
    double weight;

    public Assignee()  {
        weight = 1d;
    }

    @Override
    public double getWeight() {
        int base = Config.ASSIGNEE_BASE;
        //weight *= Math.log10(2);
        return weight;
    }
}
