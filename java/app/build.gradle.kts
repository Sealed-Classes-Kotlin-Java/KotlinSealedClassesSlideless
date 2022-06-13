plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // java
    implementation("com.google.guava:guava:30.1.1-jre")

    // testNG
    testImplementation("org.testng:testng:7.4.0")

    // github api
    implementation("org.kohsuke:github-api:1.301")

    // cli
    implementation("info.picocli:picocli:4.6.3")
}

val classMain = "info.lotharschulz.github.org.verifier.App"

val ENABLE_PREVIEW = "--enable-preview"

tasks.withType<JavaCompile> {
    options.compilerArgs.add(ENABLE_PREVIEW)
//    options.compilerArgs.add("-Xlint:preview")
}
tasks.test {
    useJUnitPlatform()
    jvmArgs(ENABLE_PREVIEW)
}

val fatJar = task("customFatJar", type = Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Implementation-Title"] = "GitHub repository verifier"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = classMain
    }
    archiveFileName.set("app.jar")
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
    destinationDirectory.set(layout.buildDirectory.dir("dist"))
}

tasks.getByName("build").dependsOn("customFatJar")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.named<Test>("test") {
    useTestNG()
}

application {
    mainClass.set(classMain)
}
