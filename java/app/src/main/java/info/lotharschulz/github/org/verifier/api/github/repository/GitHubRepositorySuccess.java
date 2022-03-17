package info.lotharschulz.github.org.verifier.api.github.repository;

import org.kohsuke.github.GHRepository;

import java.util.List;

public record GitHubRepositorySuccess(List<GHRepository> ghRepositories) implements GitHubRepository{
}
