package info.lotharschulz.github.org.verifier.api.github

import kotlin.test.Test
import kotlin.test.assertNotNull

class UtilsTest {
    @Test fun verifyGitHubConnection() {
        assertNotNull(Utils.verifyGitHubConnection("test org"), "Utils should have a verifyGitHubConnection method")
    }
}
