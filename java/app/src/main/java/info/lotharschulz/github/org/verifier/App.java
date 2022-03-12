package info.lotharschulz.github.org.verifier;

import picocli.CommandLine;

public class App {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new RepositoryScan()).execute(args);
        System.exit(exitCode);
    }
}
