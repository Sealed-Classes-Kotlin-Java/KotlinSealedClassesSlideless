package info.lotharschulz.github.org.verifier;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class AppTest {
    @Test public void repoScannerHasCall() throws Exception {
        RepositoryScanner classUnderTest = new RepositoryScanner();
//        assertNotNull(classUnderTest.call(), "RepositoryScan should have a call method");
    }
}
