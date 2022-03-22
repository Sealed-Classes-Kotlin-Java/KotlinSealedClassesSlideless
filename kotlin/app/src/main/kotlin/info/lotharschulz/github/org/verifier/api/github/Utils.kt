package info.lotharschulz.github.org.verifier.api.github

import org.kohsuke.github.GHFileNotFoundException
import org.kohsuke.github.GHOrganization
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import java.io.IOException

class Utils {
    companion object {

        private const val acceptableGitHubAPILimit = 500

        fun verifyGitHubConnection(organizationName: String): GitHubConnection {
            val github_oauth = System.getenv("GITHUB_OAUTH").takeUnless { it.isNullOrEmpty() } ?: "default"
            return try {
                GitHubConnection.Success(
                    GitHubBuilder().withOAuthToken(github_oauth, organizationName).build()
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

        fun verifyGithubOrganization(gitHub: GitHub, org: String): GHOrganization? {
            return try {
                gitHub.getOrganization(org)
            } catch (GHfnfe: GHFileNotFoundException) {
                null
            }
        }

        fun listOrgRepos(ghOrg: GHOrganization, limit: Int): List<GHRepository> {
            val repositories = ghOrg.listRepositories()
            return if (limit > -1) {
                repositories.take(limit)
            } else {
                repositories.toList()
            }
        }
    }
}
