import io.github.patrick.gradle.remapper.RemapTask

plugins {
    application
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.github.patrick.remapper") version "1.4.2"
}

group = "net.kryz.hideflag"
version = "1.1"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    implementation("org.jetbrains:annotations:24.0.0")
    //compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
    //compileOnly("org.spigotmc:spigot:1.20.6-R0.1-SNAPSHOT:remapped-mojang")
    compileOnly("org.spigotmc:spigot:1.20.4-R0.1-SNAPSHOT")
    compileOnly("net.essentialsx:EssentialsX:2.20.1")
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
//
//tasks.build {
//    dependsOn(tasks.shadowJar)
//}

tasks{
    remap{
        version.set("1.20.4")
        action.set(RemapTask.Action.MOJANG_TO_SPIGOT)
    }
}
