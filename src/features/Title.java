package features;

import analyzer.Config;

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
        double weight = 1d;
        int base = Config.TITLE_BASE;
        //weight *= Math.log10(2);
        if(title.contains("fix") || title.contains("bug"))
            return weight;
        else
            return 0d;
    }

    @Override
    public String toString() {
        return title;
    }
}
