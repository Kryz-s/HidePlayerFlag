plugins {
    application
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.kryz.hideflag"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    implementation("org.jetbrains:annotations:24.0.0")
    compileOnly("org.spigotmc:spigot:1.20.4-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
    shadowJar{
        relocate("org.jetbrains", "dev.kryz.jetbrains")
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
