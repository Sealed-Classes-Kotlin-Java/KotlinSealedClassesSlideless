
```sh
export GITHUB_OAUTH=<your github personal access token>
echo $GITHUB_OAUTH

./gradlew clean build && java -jar app/build/dist/app.jar --organization="Sealed-Classes-Kotlin-Java"
./gradlew clean build && java -jar app/build/dist/app.jar -o Sealed-Classes-Kotlin-Java
```