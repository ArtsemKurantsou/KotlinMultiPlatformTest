plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(Dependencies.Android.material)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraint)
    implementation(Dependencies.KotlinX.coroutinesAndroid)
}

android {
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        applicationId = "com.kurantsov.kmptest.android"
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}