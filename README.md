A sample Java application to display famous quotes. 

Run `mvn package` to create JAR, and `mvn package -Pwar` to create WAR.

## Configuration Files

4 configuration files are provided:

* `application.yml` is used for local development, and configures an in memory H2 database
* `deployed-application.yml` is processed by Octopus during a deployment to include the environment name, and configures a PostgresSQL database
* `docker-application.yml` is used by the Docker image, and configures an in memory H2 database
* `postgress-application.yml` can be used for local development against a PostgresSQL database

## Deploying to Sonatype

To deploy to [Sonatype](https://oss.sonatype.org/#welcome) run the command:

```
mvn -Psonatype "-Dgpg.keyname=gpgkeyname" "-Dgpg.passphrase=keypassword" clean deploy
```

Note that you need to configure `~/.m2/settings.xml` with your Sonatype credentials. 
See [these instructions](https://central.sonatype.org/pages/apache-maven.html) for details.

The WAR file has been published as `com.octopus:randomquotes`, and is available on
[Maven central](https://repo1.maven.org/maven2/com/octopus/randomquotes/).
