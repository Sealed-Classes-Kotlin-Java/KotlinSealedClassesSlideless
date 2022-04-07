### Build and run code

generate a [personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) and grant `read:org, repo` [roles](https://docs.github.com/en/organizations/managing-access-to-your-organizations-repositories/repository-roles-for-an-organization)  

```sh
export GITHUB_OAUTH=<your github personal access token>
echo $GITHUB_OAUTH

./gradlew clean build && java -jar app/build/dist/app.jar --organization=[GitHub organization to read repositories from]
# or
./gradlew clean build && java -jar app/build/dist/app.jar -o [GitHub organization to read repositories from]

# sample calls to scan this organization
./gradlew clean build && java -jar app/build/dist/app.jar --organization="Sealed-Classes-Kotlin-Java"
./gradlew clean build && java -jar app/build/dist/app.jar -o Sealed-Classes-Kotlin-Java
```
