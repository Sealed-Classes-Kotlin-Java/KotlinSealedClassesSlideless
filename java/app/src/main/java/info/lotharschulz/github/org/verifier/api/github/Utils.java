package info.lotharschulz.github.org.verifier.api.github;

import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnection;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionFailure;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionSuccess;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentials;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsFailure;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsSuccess;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganization;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganizationFailure;
import info.lotharschulz.github.org.verifier.api.github.organization.GitHubOrganizationSuccess;
import info.lotharschulz.github.org.verifier.api.github.repository.GitHubRepository;
import info.lotharschulz.github.org.verifier.api.github.repository.GitHubRepositoryFailure;
import info.lotharschulz.github.org.verifier.api.github.repository.GitHubRepositorySuccess;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Utils {
    public static GitHubConnection verifyGitHubConnection(String organizationName){
        String github_oauth = System.getenv("GITHUB_OAUTH");
        if (github_oauth == null){
            return new GitHubConnectionFailure("GITHUB_OAUTH is null");
        }
        GitHubBuilder gb = new GitHubBuilder();
        try {
            return new GitHubConnectionSuccess(
                gb.withOAuthToken(github_oauth, organizationName).build()
            );
        } catch (IOException ioe){
            return new GitHubConnectionFailure(ioe.getLocalizedMessage());
        }
    }

    public static GitHubCredentials verifyGitHubCredentials(GitHub gitHub){
        boolean valid = gitHub.isCredentialValid();
        if (valid) return new GitHubCredentialsSuccess(valid);
        else return new GitHubCredentialsFailure("github credentials not valid");
    }
    
    public static GitHubOrganization verifyGithubOrganization(GitHub gitHub, String organizationName){
        try {
            return new GitHubOrganizationSuccess(gitHub.getOrganization(organizationName));
        } catch (IOException ioe){
            return new GitHubOrganizationFailure(ioe.getLocalizedMessage());
        }
    }

    public static List<GHRepository> listRepositories(GHOrganization gitHubOrganization){
        GitHubRepository gitHubRepository = listGitHubRepositories(gitHubOrganization);
        switch (gitHubRepository) {
            case GitHubRepositorySuccess gitHubRepositorySuccess -> {
                return gitHubRepositorySuccess.ghRepositories();
            }
            case GitHubRepositoryFailure gitHubRepositoryFailure -> {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }

    public static GitHubRepository listGitHubRepositories(GHOrganization gitHubOrganization){
        try {
            return new GitHubRepositorySuccess(gitHubOrganization.listRepositories().toList());
        } catch (IOException ioe) {
            return new GitHubRepositoryFailure(ioe.getLocalizedMessage());
        }
    }
}
