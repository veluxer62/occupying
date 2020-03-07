import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jvmTargetVersion: String by project
val projectVersion: String by project
val snakeyamlVersion: String by project
val mockitoInlineVersion: String by project

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

group = "com.kh"
version = projectVersion
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.yaml:snakeyaml:$snakeyamlVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testRuntimeOnly("org.mockito:mockito-inline:$mockitoInlineVersion")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = jvmTargetVersion
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
