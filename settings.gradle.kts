pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "KotlinMultiPlatformTest"
include(":androidApp")
include(":shared")
include(":domain")
include(":data")
