package info.lotharschulz.github.org.verifier;

import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnection;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionFailure;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionSuccess;
import info.lotharschulz.github.org.verifier.api.github.Utils;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentials;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsFailure;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsSuccess;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganization;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganizationFailure;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganizationSuccess;
import info.lotharschulz.github.org.verifier.api.github.repository.GitHubRepositorySuccess;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class RepositoryScanner implements Callable<Integer>{

    private final int acceptableGitHubAPILimit = 500;

    @Option(names = {"-o", "--organization"}, description = "GitHub organization")
    private String org;

    @Override
    public Integer call() {
        GitHubConnection gitHubConnectionResult = Utils.verifyGitHubConnection(org);
        switch (gitHubConnectionResult) {
            case GitHubConnectionSuccess gitHubConnectionSuccess -> {
                if (verifyGitHubCredentials(gitHubConnectionSuccess)) {
                    scanOrg(org, gitHubConnectionSuccess.getGh());
                }
            }
            case GitHubConnectionFailure gitHubConnectionFailure -> {
                System.out.println("gitHubConnectionFailure.getError(): " + gitHubConnectionFailure.getError());
            }
            default -> {
                return 1;
            }
        }
        return 0;
    }

    private boolean verifyGitHubCredentials(GitHubConnectionSuccess gitHubConnectionSuccess) {
        GitHubCredentials gitHubCredentialsResult = Utils.verifyGitHubCredentials(gitHubConnectionSuccess.getGh());
        switch (gitHubCredentialsResult) {
            case GitHubCredentialsSuccess gitHubCredentialsSuccess -> {
                return true;
            }
            case GitHubCredentialsFailure gitHubCredentialsFailure -> {
                System.out.println("gitHubCredentialsFailure.error(): " + gitHubCredentialsFailure.error());
                return false;
            }
        }
        return false;
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
        switch (githubOrganization) {
            case GitHubOrganizationSuccess gitHubOrganizationSuccess -> {
                List<GHRepository> repos = Utils.listRepositories(gitHubOrganizationSuccess.ghOrganization());
                repos.forEach(repo -> System.out.println(repo.getName().toLowerCase()));
            }
            case GitHubOrganizationFailure gitHubOrganizationFailure -> {
                System.out.println(gitHubOrganizationFailure.error());
            }
        }
        checkRateLimit(gitHub);
    }
}
