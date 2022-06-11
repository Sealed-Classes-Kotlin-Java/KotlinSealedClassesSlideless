# Kotlin vs Java Sealed Classes

Sample code explaining differences between sealed classes in Java and Kotlin.

- Kotlin Sealed Classes: https://kotlinlang.org/docs/sealed-classes.html
- Java Sealed Classes: https://openjdk.java.net/jeps/409

## Kotlin

code without sealed classes ([source](https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/startBranch/kotlin/app/src/main/kotlin/info/lotharschulz/github/org/verifier/api/github/Utils.kt#L47-L53)):

```kotlin
fun verifyGithubOrganization(gitHub: GitHub, org: String): GHOrganization? {
    return try {
        gitHub.getOrganization(org)
    } catch (ioe: IOException) {
        null
    }
}
```

usage ([source](https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/startBranch/kotlin/app/src/main/kotlin/info/lotharschulz/github/org/verifier/RepositoryScanner.kt#L42-L46)):
```kotlin
  val githubOrganization = Utils.verifyGithubOrganization(github, organizationName)
  githubOrganization?.let {
      val repositories = Utils.listOrgRepos(it, limit = -1)
      repositories.forEach { println("repo: ${it.name}") }
  }
```


similar code with sealed classes ([source](https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/main/kotlin/app/src/main/kotlin/info/lotharschulz/github/org/verifier/api/github/Utils.kt#L48-L54)):

```kotlin
fun verifyGithubOrganization(gitHub: GitHub, org: String): Organization {
    return try {
        Organization.Success(gitHub.getOrganization(org))
    } catch (ioe: IOException) {
        Organization.Failure(ioe.localizedMessage)
    }
}
```

## Java

code without sealed class ([source](https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/startBranch/java/app/src/main/java/info/lotharschulz/github/org/verifier/api/github/Utils.java#L50-L56)):

```java
public static List<GHRepository> listRepositories(GHOrganization gitHubOrganization){
    try {
        return gitHubOrganization.listRepositories().toList();
    } catch (IOException ioe) {
        return null;
    }
}
```

similar code with sealed classes ([source](https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/java_02_listRepos_sealedClasses/java/app/src/main/java/info/lotharschulz/github/org/verifier/api/github/Utils.java#L63-L69)):

```java
public static GitHubRepository listRepos(GHOrganization gitHubOrganization){
    try {
        return new GitHubRepositorySuccess(gitHubOrganization.listRepositories().toList());
    } catch (IOException ioe) {
        return new GitHubRepositoryFailure(ioe.getLocalizedMessage());
    }
}
```

similar code with sealed interfaces and records ([source](https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/main/java/app/src/main/java/info/lotharschulz/github/org/verifier/api/github/Utils.java#L67-L73)):

```java
public static GitHubRepository listGitHubRepositories(GHOrganization gitHubOrganization){
    try {
        return new GitHubRepositorySuccess(gitHubOrganization.listRepositories().toList());
    } catch (IOException ioe) {
        return new GitHubRepositoryFailure(ioe.getLocalizedMessage());
    }
}
```

## Usage

Java and Kotlin code reads GitHub organization data

### Kotlin
```sh
cd kotlin
export GITHUB_OAUTH=<your github personal access token>
./gradlew clean build && java -jar app/build/dist/app.jar -o [GitHub organization to read repositories from]
```

### Java
```sh
cd java
export GITHUB_OAUTH=<your github personal access token>
./gradlew clean build && java --enable-preview -jar app/build/dist/app.jar -o [GitHub organization to read repositories from]
```
