package data;

import java.util.Scanner;

/**
 * Created by @kash on 2/18/2018.
 */
public class TestRun {
    public static void main(String[] args)  {
        String url = "https://api.github.com/repos/akash-07/Issue_Prioritizer/issues";
        url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
        RunUtils.intialize(url);
        Scanner sc = new Scanner(System.in);
        System.out.println("Waiting for user input...");
        sc.nextLine();
        RunUtils.refresh(url, "past10min");
    }
}
