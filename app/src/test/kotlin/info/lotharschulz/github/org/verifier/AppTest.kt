package info.lotharschulz.github.org.verifier

import kotlin.test.Test
import kotlin.test.assertNotNull

class AppTest {
    @Test fun greeting() {
//        assertNotNull(classUnderTest.greeting, "app should have a greeting")
        assertNotNull("classUnderTest.greeting", "app should have a greeting")
    }
}
