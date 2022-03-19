package info.lotharschulz.github.org.verifier.api.github.repository;

import org.kohsuke.github.GHRepository;

import java.util.List;

public final class GitHubRepositorySuccess extends GitHubRepository{

    private final List<GHRepository> repositories ;

    public GitHubRepositorySuccess(List<GHRepository> repositories) {
        this.repositories = repositories;
    }

    public List<GHRepository> getRepositories() {
        return repositories;
    }
}
