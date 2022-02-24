plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
//    id("org.jetbrains.kotlin.jvm") version "1.6.10"
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
    // implementation("org.slf4j:slf4j-api:1.7.30")

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

val fatJar = task("fatJar", type = Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Implementation-Title"] = "GitHub repository verifier"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = classNameMain
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set(classNameMain)
}
