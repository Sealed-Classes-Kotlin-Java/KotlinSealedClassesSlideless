package info.lotharschulz.github.org.verifier.api.github

sealed class GitHubCredentials {
    data class Success(val success: Boolean) : GitHubCredentials()
    data class Failure(val error: String) : GitHubCredentials()
}
