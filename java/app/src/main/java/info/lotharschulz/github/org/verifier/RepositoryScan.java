package info.lotharschulz.github.org.verifier;

import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

public class RepositoryScan implements Callable<Integer>{

    @Option(names = {"-o", "--organization"}, description = "GitHub organization")
    private String org;

    @Override
    public Integer call() throws Exception {
        System.out.println("call");
        System.out.println("org: " + org);
        return 0;
    }
}
