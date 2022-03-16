package info.lotharschulz.github.org.verifier;

import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnection;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionFailure;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionSuccess;
import info.lotharschulz.github.org.verifier.api.github.Utils;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentials;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsFailure;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsSuccess;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganization;
import org.kohsuke.github.GitHub;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.concurrent.Callable;

public class RepositoryScanner implements Callable<Integer>{

    private final int acceptableGitHubAPILimit = 500;

    @Option(names = {"-o", "--organization"}, description = "GitHub organization")
    private String org;

    @Override
    public Integer call() {
        GitHubConnection gitHubConnectionResult = Utils.verifyGitHubConnection(org);

        if (gitHubConnectionResult instanceof GitHubConnectionSuccess gitHubConnectionSuccess) {
            if (verifyGitHubCredentials(gitHubConnectionSuccess)) {
                scanOrg(org, gitHubConnectionSuccess.getGh());
            }
        } else if (gitHubConnectionResult instanceof GitHubConnectionFailure gitHubConnectionFailure) {
            System.out.println("gitHubConnectionFailure.getError(): " + gitHubConnectionFailure.getError());
        } else {
            return 1;
        }
        return 0;
    }

    private boolean verifyGitHubCredentials(GitHubConnectionSuccess gitHubConnectionSuccess) {
        GitHubCredentials gitHubCredentialsResult = Utils.verifyGitHubCredentials(gitHubConnectionSuccess.getGh());
        System.out.println("gitHubCredentialsResult: " + gitHubCredentialsResult);
        if (gitHubCredentialsResult instanceof GitHubCredentialsSuccess){
            return true;
        } else if (gitHubCredentialsResult instanceof GitHubCredentialsFailure gitHubCredentialsFailure) {
            System.out.println("gitHubCredentialsFailure.error(): " + gitHubCredentialsFailure.error());
            return false;
        } else {
            return false;
        }
    }

    private boolean checkRateLimit(GitHub gitHub){
        try {
            if (acceptableGitHubAPILimit < gitHub.getRateLimit().getRemaining()){
                return true;
            }
        } catch (IOException ignored){

        }
        return false;
    }

    private void scanOrg(String organizationName, GitHub gitHub){
        checkRateLimit(gitHub);
        GitHubOrganization githubOrganization = Utils.verifyGithubOrganization(gitHub, organizationName);
        // TODO: scan the repos
        // List<GHOrganization> repos = Utils.listRepos(githubOrganization, limit = -1)
        // repositories.forEach(repo -> System.out.println(repo.toString().toLowerCase()));
        checkRateLimit(gitHub);
    }
}