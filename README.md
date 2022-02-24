
```sh
export GITHUB_OAUTH=<your github personal access token>
echo $GITHUB_OAUTH

./gradlew clean build && java -jar app/build/libs/app.jar --organization="Sealed-Classes-Kotlin-Java"
./gradlew clean build && java -jar app/build/libs/app.jar -o Sealed-Classes-Kotlin-Java
```