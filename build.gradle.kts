import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.triumphteam.helper.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("me.mattstudios.triumph") version "0.2.8"
}

group = "me.kaliber"
version = "1.1"

repositories {
    mavenCentral()

    paper()
    papi()
}

dependencies {

    implementation(
        triumph("cmd", "1.4.6"), // command framework
        adventure("bukkit", "4.1.0"), // adventure
        kotlin("stdlib", "1.6.10").toString()
    )

    compileOnly(
        paper("1.18.1"),
        papi("2.10.10")
    )
}

bukkit {
    name = "CombatStats"
    description = "Record Combat Statistics"
    authors = listOf("Kaliber")
    depend = listOf("PlaceholderAPI")
    apiVersion = "1.13"
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

}
