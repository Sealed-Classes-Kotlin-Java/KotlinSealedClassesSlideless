plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation(kotlin("stdlib"))

    // github api
    implementation("org.kohsuke:github-api:1.301")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // cli
    implementation("com.github.ajalt.clikt:clikt:3.4.0")

    // http
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
}

val classNameMain = "info.lotharschulz.github.org.verifier.AppKt"

val fatJar = task("customFatJar", type = Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Implementation-Title"] = "GitHub repository verifier"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = classNameMain
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

application {
    mainClass.set(classNameMain)
}
