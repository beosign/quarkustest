Anleitung nach https://quarkus.io/guides/getting-started#bootstrapping-the-project

```
mvn io.quarkus:quarkus-maven-plugin:1.13.3.Final:create \
  -DprojectGroupId=de.beosign \
  -DprojectArtifactId=quarkustest \
  -DclassName="de.beosign.quarkustest.GreetingResource" \
  -Dpath="/hello"
```
  
* Compile und DEV-Mode von Quarkus starten mit dem Launcher "Run As" auf eclipse/launch/quarkus-compile-dev.launch
* Debuggen mittels Remote Debugger mit dem Launcher "Debug As" auf eclipse/launch/GreetingResource-DEBUG.launch"
* Runnable Uber-JAR erstellen in target\quarkus-app via "Run As" auf quarkustest-package.launch => Ausführen mittels `java -jar quarkustest\target\quarkus-app\quarkus-run.jar`

* Native Image für GraalVM erstellen:
  ** Anleitung der GraalVM Installation nach https://quarkus.io/guides/building-native-image#configuring-c-development
  ** Once you've installed Visual Studio, all of your build commands should be run through the Native Tools Command Prompt. If you keep the default Start Menu shortcuts while installing Visual Studio, this will be accessible at: Start -> Visual Studio 2019 -> Tools -> x64 Native Tools Command Prompt
  ** Dann `mvn package -Pnative` ausführen im Eclipse-Projekt-Folder um das native image zu erstellen
  ** Dann ausführen mittels `quarkustest\target\quarkustest-1.0.0-SNAPSHOT-runner.exe`