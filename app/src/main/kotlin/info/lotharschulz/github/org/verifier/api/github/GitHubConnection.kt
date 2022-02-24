package info.lotharschulz.github.org.verifier.api.github

import org.kohsuke.github.GitHub

sealed class GitHubConnection {
    data class Success(val github: GitHub) : GitHubConnection()
    data class Failure(val error: String) : GitHubConnection()
}
