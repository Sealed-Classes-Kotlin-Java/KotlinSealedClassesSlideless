package info.lotharschulz.github.org.verifier.api.github.connection;

public final class GitHubConnectionFailure extends GitHubConnection{
    private final String error;

    public GitHubConnectionFailure(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
