plugins {
    java
    `java-library`
    `maven-publish`
    kotlin("jvm") version "2.0.20"
    id("io.github.goooler.shadow") version "8.1.8"
    id("com.willfp.libreforge-gradle-plugin") version "1.0.0"
}

group = "net.refractored"
version = findProperty("version")!!
val libreforgeVersion = findProperty("libreforge-version")

base {
    archivesName.set(project.name)
}

dependencies {
    project(":eco-core").dependencyProject.subprojects {
        implementation(this)
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "io.github.goooler.shadow")

    repositories {
        mavenLocal()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.auxilor.io/repository/maven-public/")
        maven("https://jitpack.io")
        maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.enginehub.org/repo")
        maven(url = "https://repo.rosewooddev.io/repository/public/")
    }

    dependencies {

        implementation("com.github.Revxrsal.Lamp:common:3.3.2")
        implementation("com.github.Revxrsal.Lamp:bukkit:3.3.2")

        compileOnly("net.kyori:adventure-platform-bukkit:4.1.2")
        implementation("net.kyori:adventure-text-minimessage:4.16.0")

        compileOnly("com.willfp:eco:6.74.4")
        compileOnly("org.jetbrains:annotations:23.0.0")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")

        // FAWE (WorldEdit)
        implementation(platform("com.intellectualsites.bom:bom-newest:1.49"))
        compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
        compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }

        // WorldGuard
        compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.12")

        // PlayerPoints
        compileOnly("org.black_ixx:playerpoints:3.2.6")
    }

    java {
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks {
        shadowJar {
            relocate("com.willfp.libreforge.loader", "net.refractored.spearicalutils.libreforge.loader")
        }

//        compileKotlin {
//            kotlinOptions {
//                jvmTarget = "17"
//            }
//        }

        compileJava {
            options.isDeprecation = true
            options.encoding = "UTF-8"

            dependsOn(clean)
        }

        processResources {
            filesMatching(listOf("**plugin.yml", "**eco.yml")) {
                expand(
                    "version" to project.version,
                    "libreforgeVersion" to libreforgeVersion,
                    "pluginName" to rootProject.name
                )
            }
        }

        build {
            dependsOn(shadowJar)
        }
    }
}
