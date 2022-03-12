package info.lotharschulz.github.org.verifier;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class AppTest {
    @Test public void appHasAGreeting() throws Exception {
        RepositoryScan classUnderTest = new RepositoryScan();
        assertNotNull(classUnderTest.call(), "RepositoryScan should have a call method");
    }
}
