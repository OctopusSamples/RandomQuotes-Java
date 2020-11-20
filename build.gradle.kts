plugins {
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	id("java")
    id("com.liftric.octopus-deploy-plugin") version "1.5.0"
}


group = "com.octopus"
version = "0.1.8"

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.flywaydb:flyway-core")
	implementation("org.postgresql:postgresql")
	implementation("com.h2database:h2:1.4.199")
	implementation("com.google.guava:guava:28.1-jre")
	implementation("org.apache.commons:commons-lang3:3.9")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
	withType(Test::class).configureEach {
		useJUnitPlatform()
	}
}

// https://github.com/Liftric/octopus-deploy-plugin
octopus {
    serverUrl.set("http://localhost:8080/")
    apiKey.set("API-TESTTEST123TRESDTSDD")

    generateChangelogSinceLastTag = true

    val jar by tasks.existing(Jar::class)
    packageName.set(jar.get().archiveBaseName.get())
    version.set(jar.get().archiveVersion.get())
    pushPackage.set(jar.get().archiveFile)
}