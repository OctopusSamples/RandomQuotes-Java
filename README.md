A sample Java application to display famous quotes.

Run `mvn package` to create JAR, and `mvn package -Pwar` to create WAR.

To deploy to Sonatype (https://oss.sonatype.org/#welcome) run the command:

```
mvn -Pwar "-Dgpg.keyname=gpgkeyname" "-Dgpg.passphrase=keypassword" deploy
```

Note that you need to configure `~/.m2/settings.xml` with your Sonatype credentials. 
See https://central.sonatype.org/pages/apache-maven.html for details.