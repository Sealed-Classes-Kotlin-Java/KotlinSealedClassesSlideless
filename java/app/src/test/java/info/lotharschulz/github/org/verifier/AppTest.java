package info.lotharschulz.github.org.verifier;

import info.lotharschulz.github.org.verifier.api.github.Utils;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class AppTest {
    @Test public void utilsHasVerifyGitHubConnection() throws Exception {
        assertNotNull(Utils.verifyGitHubConnection("empty"), "Utils should have a verifyGitHubConnection method");
    }
}
