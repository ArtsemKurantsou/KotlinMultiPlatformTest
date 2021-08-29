import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.KotlinX.coroutinesCore)
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.KotlinX.datetime)
                api(Dependencies.Kermit.kermit)
                implementation(project(":data"))
                implementation(project(":domain"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.KotlinX.coroutinesAndroid)
                implementation(Dependencies.Android.material)
                implementation(Dependencies.AndroidX.appcompat)
                implementation(Dependencies.AndroidX.constraint)
                implementation(Dependencies.AndroidX.coreKtx)
                implementation(Dependencies.AndroidX.activityKtx)
                implementation(Dependencies.AndroidX.fragmentKtx)
                implementation(Dependencies.AndroidX.cardView)
                implementation(Dependencies.AndroidX.recycler)
                implementation(Dependencies.SqlDelight.androidDriver)
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.Koin.android)
                implementation(Dependencies.Glide.runtime)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.SqlDelight.nativeDriver)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = AndroidConfig.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        buildConfigField("String", "API_KEY", "\"${Secrets.API_KEY}\"")
    }
    buildFeatures {
        viewBinding = true
    }
}