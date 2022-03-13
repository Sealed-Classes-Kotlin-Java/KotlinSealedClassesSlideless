package info.lotharschulz.github.org.verifier.api.github.connection;

import org.kohsuke.github.GitHub;

public final class GitHubConnectionSuccess extends GitHubConnection {

    private final GitHub gh;

    public GitHubConnectionSuccess(GitHub gh) {
        this.gh = gh;
    }

    public GitHub getGh() {
        return gh;
    }
}
