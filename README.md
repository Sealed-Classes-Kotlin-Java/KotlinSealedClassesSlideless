# Kotlin vs Java Sealed Classes

Sample code explaining differences between sealed classes in Java and Kotlin.

Java and Kotlin code read GitHub organization data using sealed classes.

## Kotlin

code without sealed class usages:


```kotlin
fun verifyGithubOrganization(gitHub: GitHub, org: String): GHOrganization? {
    return try {
        gitHub.getOrganization(org)
    } catch (ioe: IOException) {
        null
    }
}
```

https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/startBranch/kotlin/app/src/main/kotlin/info/lotharschulz/github/org/verifier/api/github/Utils.kt#L47-L53

code usig sealed classes:

```kotlin
fun verifyGithubOrganization(gitHub: GitHub, org: String): Organization {
    return try {
        Organization.Success(gitHub.getOrganization(org))
    } catch (ioe: IOException) {
        Organization.Failure(ioe.localizedMessage)
    }
}
```

https://github.com/Sealed-Classes-Kotlin-Java/KotlinSealedClassesSlideless/blob/main/kotlin/app/src/main/kotlin/info/lotharschulz/github/org/verifier/api/github/Utils.kt#L48-L54

## Java

TODO
