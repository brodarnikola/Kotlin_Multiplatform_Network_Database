buildscript {
    val kotlin_version by extra("1.4.32")
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    val kotlinVersion = "1.4.32"
    val sqlDelightVersion: String by project

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("com.android.tools.build:gradle:4.2.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.35")
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}