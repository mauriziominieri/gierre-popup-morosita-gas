import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
	kotlin("plugin.serialization") version "1.6.10"
}

group = "com.kotlin"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.2")
	implementation("org.springframework.boot:spring-boot-starter-web:2.6.2")
	implementation("org.springframework.boot:spring-boot-starter-mail:2.6.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.springfox:springfox-swagger2:2.9.2") // dont update to the lastest version
	implementation("io.springfox:springfox-swagger-ui:2.9.2") // dont update to the lastest version
	implementation("mysql:mysql-connector-java:8.0.27")
	implementation("org.apache.poi:poi:5.1.0")
	implementation("org.apache.poi:poi-ooxml:5.1.0")
	implementation("com.google.code.gson:gson:2.8.9")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.6.2")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
	implementation("org.json:json:20211205")


	runtimeOnly("com.h2database:h2:2.0.204")

	compileOnly("org.projectlombok:lombok:1.18.22")

	developmentOnly("org.springframework.boot:spring-boot-devtools:2.6.2")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.6.2")
	annotationProcessor("org.projectlombok:lombok:1.18.22")

	testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
