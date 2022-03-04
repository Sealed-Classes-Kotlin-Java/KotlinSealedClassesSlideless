package info.lotharschulz.github.org.verifier.api.github

import okhttp3.OkHttpClient
import org.kohsuke.github.GHFileNotFoundException
import org.kohsuke.github.GHOrganization
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.kohsuke.github.extras.okhttp3.OkHttpConnector
import java.io.IOException

class Utils {
    companion object {

        private const val acceptableGitHubAPILimit = 2500

        fun verifyGitHubConnection(): GitHubConnection {
            return try {
                GitHubConnection.Success(
                    GitHubBuilder.fromEnvironment()
                        .withConnector(
                            OkHttpConnector(OkHttpClient())
                        ).build()
                )
            } catch (ioe: IOException) {
                GitHubConnection.Failure(ioe.toString())
            }
        }

        fun verifyGitHubCredentials(github: GitHub): GitHubCredentials {
            val valid = github.isCredentialValid
            return if (valid) GitHubCredentials.Success(valid)
            else GitHubCredentials.Failure("credentials not valid")
        }

        fun checkRateLimit(github: GitHub): Boolean {
            val remainingGitHubAPILimit = github.rateLimit.getRemaining()
            return if (acceptableGitHubAPILimit < remainingGitHubAPILimit) {
                true
            } else {
                false.also {
                    println(
                        """
                        $remainingGitHubAPILimit is not enough as remaining GitHub API limit.
                        Please wait some time until the api limit recovered
                        """.trimIndent()
                    )
                }
            }
        }

        fun verifyGithubOrganization(gitHub: GitHub, org: String): Organization {
            return try {
                Organization.Success(gitHub.getOrganization(org))
            } catch (GHfnfe: GHFileNotFoundException) {
                Organization.Failure(GHfnfe.message.toString())
            }
        }

        fun listRepos(org: Organization, limit: Int): List<GHRepository> =
            when (org) {
                is Organization.Success -> listOrgRepos(org.githubOrg, limit)
                is Organization.Failure -> emptyList()
            }

        private fun listOrgRepos(ghOrg: GHOrganization, limit: Int): List<GHRepository> {
            val repositories = ghOrg.listRepositories()
            return if (limit > -1) {
                repositories.take(limit)
            } else {
                repositories.toList()
            }
        }
    }
}