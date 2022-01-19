val coreKtxVersion by extra("1.6.0")
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.5.10"
    var coreVersion : String by extra
    coreVersion = "1.5.0"
    val coreKtxVersion by extra(kotlin_version)
    val compose_version by extra("1.0.0")
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
//        classpath(("gradle-plugin", kotlin_version))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        flatDir {
            dirs("libs")
        }
        maven(url = "https://storage.googleapis.com/download.flutter.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

