package jjb;

import org.kohsuke.args4j.CmdLineException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Executor executor = null;
        try {
            executor = new Executor(args);
            executor.run();
        } catch (CmdLineException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
