package info.lotharschulz.github.org.verifier.api.github.repository;

public final class GitHubRepositoryFailure extends GitHubRepository{
    private final String error;

    public GitHubRepositoryFailure(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
