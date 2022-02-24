package info.lotharschulz.github.org.verifier

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import info.lotharschulz.github.org.verifier.api.github.GitHubConnection
// import info.lotharschulz.github.org.verifier.api.github.Organization
import info.lotharschulz.github.org.verifier.api.github.Utils
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
            is GitHubConnection.Success -> runVerifier(organization.toString(), gitHubConnection.github)
            is GitHubConnection.Failure -> println("could not connect to github.com")
        }
    }
    private fun verifyGitHubConnection(): GitHubConnection = Utils.verifyGitHubConnection()

    private fun runVerifier(organizationName: String, github: GitHub) {
        println(organizationName)
        println(github)
/*        if (checkRateLimit(github, displayError = true)) {
            val githubOrganization = verifyGithubOrganization(github, organizationName)
            val repositories = utils.listRepos(githubOrganization, limit = -1)
            utils.writeRepositoriesBranchProtectionSettingsToCSV(repositories)
        }
        checkRateLimit(github, displayError = false)*/
    }
}
