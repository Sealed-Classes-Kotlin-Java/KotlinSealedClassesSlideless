package info.lotharschulz.github.org.verifier

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import info.lotharschulz.github.org.verifier.api.github.GitHubConnection
import info.lotharschulz.github.org.verifier.api.github.GitHubCredentials
import info.lotharschulz.github.org.verifier.api.github.Utils
import info.lotharschulz.github.org.verifier.api.github.Utils.Companion.checkRateLimit
import org.kohsuke.github.GitHub

class RepositoryVerifier : CliktCommand(printHelpOnEmptyArgs = true, help = "helpMessage") {
    override fun run() {
        processCommands()
    }
    init {
        context {
            printExtraMessages = true
        }
    }
    private val organization by option(
        "-o",
        "--organization",
        help = "GitHub organization"
    ).validate {
        if (it.trim().isBlank()) message("Organization can not be empty")
    }

    private fun processCommands() {
        when (val gitHubConnection = verifyGitHubConnection()) {
            is GitHubConnection.Success -> when (val valid = verifyGitHubCredentials(gitHubConnection.github)) {
                is GitHubCredentials.Success -> runVerifier(organization.toString(), gitHubConnection.github)
                is GitHubCredentials.Failure -> println(valid.error)
            }
            is GitHubConnection.Failure -> println("could not connect to github.com")
        }
    }

    private fun runVerifier(organizationName: String, github: GitHub) {
        if (checkRateLimit(github)) {
            val githubOrganization = Utils.verifyGithubOrganization(github, organizationName)
            val repositories = Utils.listRepos(githubOrganization, limit = -1)
            repositories.forEach { println("repo: ${it.name}") }
        }
        checkRateLimit(github)
    }

    private fun verifyGitHubConnection(): GitHubConnection = Utils.verifyGitHubConnection()

    private fun verifyGitHubCredentials(github: GitHub): GitHubCredentials = Utils.verifyGitHubCredentials(github)
}
