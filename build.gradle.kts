plugins {
    application
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.kryz.hideflag"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    compileOnly("org.jetbrains:annotations:24.0.0")
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
}


tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

application {
    mainClass.set("net.kryz.hideflag.Main")
}

tasks{
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}