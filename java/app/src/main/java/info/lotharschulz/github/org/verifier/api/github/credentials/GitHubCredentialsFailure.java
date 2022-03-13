package info.lotharschulz.github.org.verifier.api.github.credentials;

public record GitHubCredentialsFailure(String error) implements GitHubCredentials{
}
