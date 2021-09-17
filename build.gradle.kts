import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.kaliber"
version = "1.0"

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // command framework
    implementation("me.mattstudios.utils:matt-framework:1.4.6")

    // adventure api
    implementation("net.kyori:adventure-platform-bukkit:4.0.0-SNAPSHOT")

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.9")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions { jvmTarget = "1.8" }
    }

    withType<ShadowJar> {
        minimize()
        relocate("kotlin", "me.kaliber.libs.kotlin")
        relocate("me.mattstudios.mf", "me.kaliber.libs.cmd")
        relocate("net.kyori", "me.kaliber.libs.adventure")
        archiveFileName.set("CombatStats-${project.version}.jar")
    }

    withType<ProcessResources> {
        filesMatching("**/plugin.yml") {
            expand("version" to version)
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
