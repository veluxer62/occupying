rootProject.name = "occupying"

pluginManagement {
    plugins {
        repositories {
            gradlePluginPortal()
        }
        val kotlinJvmVersion: String by extra
        val springBootVersion: String by extra
        val springDependencyManagementVersion: String by extra

        kotlin("jvm") version kotlinJvmVersion
        kotlin("plugin.spring") version kotlinJvmVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
    }
}