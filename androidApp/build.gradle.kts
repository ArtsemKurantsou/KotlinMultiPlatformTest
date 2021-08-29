plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.Android.material)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraint)
    implementation(Dependencies.KotlinX.coroutinesAndroid)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.activityKtx)
    implementation(Dependencies.AndroidX.fragmentKtx)
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