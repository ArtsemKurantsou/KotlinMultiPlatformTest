buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.plugin)
        classpath(Dependencies.Android.plugin)
        classpath(Dependencies.SqlDelight.plugin)
        classpath(Dependencies.Kotlin.serializationPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}