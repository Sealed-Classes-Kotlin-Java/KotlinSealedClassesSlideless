package info.lotharschulz.github.org.verifier.api.github;

import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnection;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionFailure;
import info.lotharschulz.github.org.verifier.api.github.connection.GitHubConnectionSuccess;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentials;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsFailure;
import info.lotharschulz.github.org.verifier.api.github.credentials.GitHubCredentialsSuccess;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.GitHub;

import java.io.IOException;

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
}
