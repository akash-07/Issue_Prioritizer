package analyzer;

import features.Issue;

import java.util.Collections;
import java.util.List;

/**
 * Created by @kash on 2/26/2018.
 */
public class PriorityUtils {

    public static List<Issue> getSortedIssues(List<Issue>issues)    {
        for(Issue issue: issues)
                issue.setWeight(issue.getWeight());
        Collections.sort(issues);
        return issues;
    }
}
