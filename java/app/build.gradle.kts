plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // java
    implementation("com.google.guava:guava:30.1.1-jre")

    // test: TestNG framework, also requires calling test.useTestNG() below
    testImplementation("org.testng:testng:7.4.0")
}

application {
    mainClass.set("info.lotharschulz.github.org.verifier.App")
}

tasks.named<Test>("test") {
    useTestNG()
}
