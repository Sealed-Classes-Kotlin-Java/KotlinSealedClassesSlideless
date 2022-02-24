package info.lotharschulz.github.org.verifier.api.github

import okhttp3.OkHttpClient
import org.kohsuke.github.GitHubBuilder
import org.kohsuke.github.extras.okhttp3.OkHttpConnector
import java.io.IOException

class Utils {
    companion object {
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
    }
}
