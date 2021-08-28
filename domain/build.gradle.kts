plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.KotlinX.coroutinesCore)
                implementation(Dependencies.KotlinX.datetime)
            }
        }
    }
}